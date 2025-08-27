package io.github.cursodsousa.libraryapi.controller.dto;

import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
                    @NotBlank(message = "Campo obrigatorio.")
                    @ISBN
                    String isbn,
                    @NotBlank(message = "Campo obrigatorio.")
                    String titulo,
                    @NotNull(message = "Campo obrigatorio.")
                    @Past(message = "Data publicação deve ser uma data presente ou anterior.")
                    LocalDate dataPublicacao,
                    GeneroLivro genero,
                    BigDecimal preco,
                    @NotNull
                    UUID idAutor
                    ) {
}
