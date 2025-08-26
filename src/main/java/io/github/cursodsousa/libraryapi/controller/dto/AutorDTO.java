package io.github.cursodsousa.libraryapi.controller.dto;

import io.github.cursodsousa.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
                    UUID id, //vai ser ignorado, por nao estar no contrato
                    @NotBlank(message="Campo obrigatório")
                    @Size(min = 2, max = 100, message = "Limite de caracteres para campo atingido.")
                    String nome,
                    @NotNull(message="Campo obrigatório")
                    LocalDate dataNascimento,
                    @Past(message = "Data deve ser inferior a presente")
                    @NotBlank(message="Campo obrigatório")
                    @Size(max = 50, min = 2, message = "Quantidade de caracteres fora do padrão.")
                    String nacionalidade
    ) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);

        return autor;
    }

}
