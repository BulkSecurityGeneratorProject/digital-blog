package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.CanalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Canal and its DTO CanalDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CanalMapper extends EntityMapper <CanalDTO, Canal> {
    
    
    default Canal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Canal canal = new Canal();
        canal.setId(id);
        return canal;
    }
}
