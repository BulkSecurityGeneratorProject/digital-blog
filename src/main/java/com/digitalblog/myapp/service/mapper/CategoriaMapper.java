package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.CategoriaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Categoria and its DTO CategoriaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoriaMapper {

    CategoriaDTO categoriaToCategoriaDTO(Categoria categoria);

    List<CategoriaDTO> categoriasToCategoriaDTOs(List<Categoria> categorias);

    Categoria categoriaDTOToCategoria(CategoriaDTO categoriaDTO);

    List<Categoria> categoriaDTOsToCategorias(List<CategoriaDTO> categoriaDTOs);
}
