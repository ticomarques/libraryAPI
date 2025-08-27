package io.github.cursodsousa.libraryapi.controller;

import io.github.cursodsousa.libraryapi.controller.dto.AutorDTO;
import io.github.cursodsousa.libraryapi.controller.dto.ErroResposta;
import io.github.cursodsousa.libraryapi.controller.mappers.AutorMapper;
import io.github.cursodsousa.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.cursodsousa.libraryapi.exceptions.RegistroDuplicadoExceptions;
import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService service;
    private final AutorMapper mapper;

    //POST autor
    @PostMapping
    //@ResponseStatus(HttpStatus.OK) - Se voce quiser que sempre retorne ok na chamda deste endpoint
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto){
        try{
            //Autor autor = autor.mapearParaAutor(); -- forma sem usar o mapStruct
            Autor autor = mapper.toEntity(dto);
            service.salvar(autor);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autor.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoExceptions e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    //GET autor (detalhes)
    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        return service
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto);

                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    //DELETE autor
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            service.deletar(autorOptional.get());
            return ResponseEntity.noContent().build();
        } catch (OperacaoNaoPermitidaException e){
            var erroDTO = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    //GET autor (nome, nacionalidade, todos)
    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value="nome", required=false) String nome,
            @RequestParam(value="nacionalidade", required=false) String nacionalidade
    ){
        List<Autor> resultado = service.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                                .stream()
                                .map(mapper::toDTO)
                                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    //PUT autor
    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto){
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var autor = autorOptional.get();
            autor.setNome(dto.nome());
            autor.setNacionalidade(dto.nacionalidade());
            autor.setDataNascimento(dto.dataNascimento());

            service.atualizar(autor);
            return ResponseEntity.noContent().build();
        } catch (RegistroDuplicadoExceptions e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
