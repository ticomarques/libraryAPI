package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @see @LivroRepositoryTest
 */

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    //Query method

    //select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    //select * from livro where titulo = titulo
    List<Livro> findByTitulo(String titulo);

    //select * from livro where isbn = ?
    Optional<Livro> findByIsbn(String isbn);

    //select * from livro where titulo = ? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    //select * from livro where titulo = ? or isbn = ? order by titulo desc
    List<Livro> findByTituloOrIsbnOrderByTitulo(String titulo, String isbn);

    //select * from livro where data_publicacao between ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    //JPQL - Referencia a Entidade, e as propriedades da entidade (Livro.java), e não as tabelas do banco.
    //SQL criado em background: SELECT l.* from livro as l order by l.titulo, l.preco
    @Query(" SELECT l FROM Livro as l order by l.titulo, l.preco ")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    @Query("SELECT a FROM Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    //select distinct l.* from livro
    @Query("SELECT DISTINCT l.titulo FROM Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
            SELECT l.genero
            FROM Livro l
            JOIN l.autor a
            WHERE a.nacionalidade = 'Brasileiro'
            Order by l.genero
    """)
    List<String> listarGenerosAutoresBrasileiros();


    //Named parameters
    @Query(" SELECT l FROM Livro l WHERE l.genero = :genero ORDER BY :paramOrdenacao")
    List<Livro> findByGenero(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomePropriedade
            );

    //Positional parameters
    @Query(" SELECT l FROM Livro l WHERE l.genero = ?1 ORDER BY ?2 ")
    List<Livro> findByGeneroPositionalParameters(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomePropriedade
    );

    @Modifying
    @Transactional //update e delete são transacoes (precisam ser comitadas no final)
    @Query(" DELETE FROM Livro WHERE genero = ?1 ")
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional //update e delete são transacoes (precisam ser comitadas no final)
    @Query(" UPDATE Livro SET dataPublicacao = ?1 ")
    void updateDataPublicacao(LocalDate novaData);

    boolean existsByAutor(Autor autor);

}
