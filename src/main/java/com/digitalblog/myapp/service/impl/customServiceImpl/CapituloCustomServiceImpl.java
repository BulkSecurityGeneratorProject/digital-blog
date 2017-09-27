package com.digitalblog.myapp.service.impl.customServiceImpl;

import com.digitalblog.myapp.domain.Capitulo;
import com.digitalblog.myapp.repository.customRepository.CapituloRepositoryCustom;
import com.digitalblog.myapp.service.customService.CapituloCustomService;
import com.digitalblog.myapp.service.dto.CapituloDTO;
import com.digitalblog.myapp.service.mapper.CapituloMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Service Implementation for managing Publicacion.
 */
@Service
@Transactional
public class CapituloCustomServiceImpl implements CapituloCustomService {

    private final Logger log = LoggerFactory.getLogger(CapituloCustomServiceImpl.class);

    private final CapituloRepositoryCustom capituloRepositoryCustom;

    private final CapituloMapper capituloMapper;

    public CapituloCustomServiceImpl(CapituloRepositoryCustom capituloRepositoryCustom, CapituloMapper capituloMapper) {
        this.capituloRepositoryCustom = capituloRepositoryCustom;
        this.capituloMapper = capituloMapper;
    }


    /**
     * @author Jose Nuñez
     * Método que obtiene capitulos por id publicacion
     * @param id
     * @return  ArrayList<CapituloDTO>
     * @version 1.0
     */
    @Transactional(readOnly = true)
    public ArrayList<CapituloDTO> buscarCapituloPorPublicacion(Long id) {

        ArrayList<Capitulo> capitulos = capituloRepositoryCustom.findByIdPublicacion(id);
        ArrayList<CapituloDTO> result = new ArrayList<>();
        CapituloDTO capitulo;
        for (int i = 0; i < capitulos.size(); i++) {
            capitulo = capituloMapper.capituloToCapituloDTO(capitulos.get(i));
            result.add(capitulo);
        }
        return result;

    }

    /**
     * @author Jose Nuñez
     * Método que obtiene el ultimo capitulo de una publicacion
     * @param capitulos
     * @param ultimoCapitulo
     * @return CapituloDTO
     * @version 1.0
     */
    @Transactional(readOnly = true)
    public CapituloDTO obtenerUltimoCapituloPublicacion(ArrayList<CapituloDTO> capitulos, CapituloDTO ultimoCapitulo) {

        CapituloDTO finalTemporal = capitulos.get((capitulos.size()-1));
        ultimoCapitulo.setNumeroCapitulo((finalTemporal.getNumeroCapitulo()+1));
        return ultimoCapitulo;
    }


}
