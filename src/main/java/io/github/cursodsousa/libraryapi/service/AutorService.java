package io.github.cursodsousa.libraryapi.service;

import io.github.cursodsousa.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.repository.AutorRepository;
import io.github.cursodsousa.libraryapi.repository.LivroRepository;
import io.github.cursodsousa.libraryapi.validator.AutorValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;

    public AutorService(
            AutorRepository repository,
            AutorValidator validator,
            LivroRepository livroRepository
    ){
        this.repository = repository;
        this.validator = validator;
        this.livroRepository = livroRepository;
    }

    //service para salvar autor
    public Autor salvar(Autor autor){
        validator.validar(autor);
        return repository.save(autor);
    }

    public void atualizar(Autor autor){
        if(autor.getId() == null) {
            throw new IllegalArgumentException("Para atualizar é necessário que o autor já esteja salvo na base.");
        }
        validator.validar(autor);
        repository.save(autor);
    }

    //service para buscar autor por id
    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Autor autor){
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException(
                    "Autor possui livros cadastrados! Não é possui excluir autor com livros cadastrados.");
        }
        repository.delete(autor);
    }
    //aqui fica a logica
    public List<Autor> pesquisa(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if(nome !=  null){
            return repository.findByNome(nome);
        }

        if (nacionalidade != null){
            return repository.findByNacionalidade(nacionalidade);
        }

        return repository.findAll();
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
