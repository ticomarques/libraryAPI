package io.github.cursodsousa.libraryapi.service;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import io.github.cursodsousa.libraryapi.repository.AutorRepository;
import io.github.cursodsousa.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void salvarLivroComFoto(){
        //salva o livro
    }

    @Transactional
    public void atualizacaoSemAtualizacao(){
        var livro = livroRepository.findById(UUID.fromString("e28efab2-7b7e-4864-befa-f4f5d26723c4")).orElse(null);

        livro.setDataPublicacao(LocalDate.of(2014,2,2));

        livroRepository.save(livro);
    }

    //transacoes tem que ser em metodos public
    @Transactional
    public void executar(){

        //salva o autor
        Autor autor = new Autor();
        autor.setNome("Teste Francisco");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1945, 1, 20));

        //salva o livro
        Livro livro = new Livro();
        livro.setIsbn("99909-99809");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Teste Livro do Francisco");
        livro.setDataPublicacao(LocalDate.of(1980,1,1));

        autorRepository.save(autor);

        livro.setAutor(autor);

        livroRepository.save(livro);

        if(autor.getNome().equals("Teste")){
            throw new RuntimeException("Rollback");
        }
    }
}
