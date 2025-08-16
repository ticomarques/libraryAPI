package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
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
}
