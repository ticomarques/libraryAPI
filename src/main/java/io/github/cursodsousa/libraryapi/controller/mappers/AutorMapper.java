package io.github.cursodsousa.libraryapi.controller.mappers;

import io.github.cursodsousa.libraryapi.controller.dto.AutorDTO;
import io.github.cursodsousa.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    //Pode mapear source e target com nomes diferentes também
    @Mapping(source="nome", target="nome")
    @Mapping(source="dataNascimento", target="dataNascimento")
    @Mapping(source="nacionalidade", target="nacionalidade")
    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
