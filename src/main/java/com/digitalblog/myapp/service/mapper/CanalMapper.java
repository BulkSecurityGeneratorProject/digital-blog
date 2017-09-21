package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.CanalDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Canal and its DTO CanalDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CanalMapper {

    CanalDTO canalToCanalDTO(Canal canal);

    List<CanalDTO> canalsToCanalDTOs(List<Canal> canals);

    Canal canalDTOToCanal(CanalDTO canalDTO);

    List<Canal> canalDTOsToCanals(List<CanalDTO> canalDTOs);
}
