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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@SpringBootTest
public class AutorRepositoryTest {


    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        var autorSalvor = repository.save(autor);
        System.out.println("Autor salvo: " + autorSalvor);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("88871a54-b17b-4cd6-b14e-54d046e2ee20");
        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor: ");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1960, 1, 30));

            //nao existe metodo update, para atualizar usamos o save.
            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Numero de autores cadastrados: " + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        //Aqui deleta o objeto por id, nao estamos testando a existencia no caso.
        var id = UUID.fromString("88871a54-b17b-4cd6-b14e-54d046e2ee20");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest(){
        //Aqui deleta o objeto direto, nao estamos testando a existencia no caso.
        var id = UUID.fromString("1db2517c-fde0-4727-8d65-fde2eae5845b");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }

    @Test
    public void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Antonia");
        autor.setNacionalidade("Japones");
        autor.setDataNascimento(LocalDate.of(1970, 1, 31));

        Livro livro = new Livro();
        livro.setIsbn("97709-77777");
        livro.setPreco(BigDecimal.valueOf(200));
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setTitulo("UFO da Romantuca");
        livro.setDataPublicacao(LocalDate.of(1984,1,1));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("1111-77555");
        livro2.setPreco(BigDecimal.valueOf(300));
        livro2.setGenero(GeneroLivro.ROMANCE);
        livro2.setTitulo("UFO da Rolantico");
        livro2.setDataPublicacao(LocalDate.of(1990,1,1));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    @Transactional
    void listarLivrosAutor(){
        var id = UUID.fromString("86c29685-b72a-4e49-8bb6-628a123886bf");
        var autor = repository.findById(id).get();

        // buscar livros pelo autor

        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }
}
