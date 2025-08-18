package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
        livro.setDataPublicacao(LocalDate.of(1980,1,1));

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
        livro.setDataPublicacao(LocalDate.of(1980,1,1));

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
        livro.setDataPublicacao(LocalDate.of(1980,1,1));

        Autor autor = new Autor();
        autor.setNome("Garrincha");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1942, 1, 20));

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarDoAutorTest(){
        UUID id = UUID.fromString("e9af2bd9-c31c-4606-9762-5bf592e6b3c3");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID id_autor = UUID.fromString("86c29685-b72a-4e49-8bb6-628a123886bf");
        Autor autor = autorRepository.findById(id_autor).orElse(null);

        livroParaAtualizar.setAutor(autor);
        repository.save(livroParaAtualizar);
    }

    @Test
    void deletar(){
        UUID id = UUID.fromString("e9af2bd9-c31c-4606-9762-5bf592e6b3c3");
        repository.deleteById(id);
        System.out.println("Livro deletado com sucesso!");
    }

    @Test
    @Transactional
    void buscarLivroTest(){
        UUID id = UUID.fromString("3ed34dd6-08c3-4faa-a4d5-f8beedd238e1");
        Livro livro = repository.findById(id).orElse(null);

        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());

        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest(){
        List<Livro> lista = repository.findByTitulo("UFO da Lua");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbnTest(){
        List<Livro> lista = repository.findByIsbn("99909-99809");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPrecoTest(){
        var titulo = "UFO da Lua";
        var preco = BigDecimal.valueOf(100.00);

        List<Livro> lista = repository.findByTituloAndPreco(titulo, preco);
        lista.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL(){
        var resultado = repository.listarTodosOrdenadoPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivros(){
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidos(){
        var resultado = repository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeLivrosAutoresBrasileiros(){
        var resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParamTest(){
        var resultado = repository.findByGenero(GeneroLivro.ROMANCE, "dataPublicacao");
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroPositionalParamTest(){
        var resultado = repository.findByGeneroPositionalParameters(GeneroLivro.ROMANCE, "preco");
        resultado.forEach(System.out::println);
    }
}