package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@SpringBootTest
public class AutorRepositoryTest {


    @Autowired
    AutorRepository repository;

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
}
