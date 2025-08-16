package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
//classe nao precisa ter marcacao publica, porque testes sao publicos
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();

        livro.setIsbn("99909-99809");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO da Lua");
        livro.setData_publicacao(LocalDate.of(1980,1,1));

        Autor autor = autorRepository.findById(UUID.fromString("86c29685-b72a-4e49-8bb6-628a123886bf")).orElse(null);
        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarAutorELivroTest(){
        Livro livro = new Livro();

        livro.setIsbn("99909-99809");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO da Sol");
        livro.setData_publicacao(LocalDate.of(1980,1,1));

        Autor autor = new Autor();
        autor.setNome("Pelé");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1945, 1, 20));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    //Não recomendado (use com cuidado).
    void salvarCascadeTest(){
        Livro livro = new Livro();

        livro.setIsbn("99909-99809");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO da Lua");
        livro.setData_publicacao(LocalDate.of(1980,1,1));

        Autor autor = new Autor();
        autor.setNome("Garrincha");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1942, 1, 20));

        livro.setAutor(autor);

        repository.save(livro);
    }

}