package com.digitalblog.myapp.service.impl.customServiceImpl;

import com.digitalblog.myapp.domain.Seccion;
import com.digitalblog.myapp.repository.customRepository.SeccionRepositoryCustom;
import com.digitalblog.myapp.service.customService.SeccionCustomService;
import com.digitalblog.myapp.service.dto.SeccionDTO;
import com.digitalblog.myapp.service.mapper.SeccionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Service Implementation for managing Seccion.
 */
@Service
@Transactional
public class SeccionCustomServiceImpl implements SeccionCustomService{

    private final Logger log = LoggerFactory.getLogger(SeccionCustomServiceImpl.class);

    private final SeccionRepositoryCustom seccionCustomRepository;

    private final SeccionMapper seccionMapper;

    public SeccionCustomServiceImpl(SeccionRepositoryCustom seccionCustomRepository, SeccionMapper seccionMapper) {
        this.seccionCustomRepository = seccionCustomRepository;
        this.seccionMapper = seccionMapper;
    }

    /**
     * @author Eduardo Guerrero
     * Obtiene la lista de secciones por el id de la biblioteca del usuario logueado
     * @param id the entity to save
     * @return  ArrayList<SeccionDTO>
     * @version 1.0
     */
    @Transactional(readOnly = true)
    public ArrayList<SeccionDTO> buscarSeccionPorBiblioteca(Long id) {
        log.debug("Request to get all Seccions");
        ArrayList<Seccion> secciones = seccionCustomRepository.findAllByIdBiblioteca(id);
        ArrayList<SeccionDTO> result = new ArrayList<>();
        SeccionDTO seccionDTO;
        for (int i = 0; i < secciones.size(); i++) {
            seccionDTO = seccionMapper.seccionToSeccionDTO(secciones.get(i));
            result.add(seccionDTO);
        }
        return result;
    }

    /**
     * @author Eduardo Guerrero
     * @param nombre
     * @return SeccionDTO
     * @version 1.0
     */
    @Override
    public SeccionDTO buscarSeccionPorNombre(String nombre,Long id) {
        log.debug("Request to get seccion : {}", nombre);
        Seccion seccion = seccionCustomRepository.findOneByName(nombre,id);
        SeccionDTO seccionDTO = seccionMapper.seccionToSeccionDTO(seccion);
        return seccionDTO;
    }


    /**
     * @author Christopher Sullivan
     * @param idBiblioteca
     * @param nombreSeccion
     * @return SeccionDTO
     * @version 1.0
     */
    @Override
    public SeccionDTO findSeccionByNameById(Long idBiblioteca, String nombreSeccion) {
        Seccion seccion = seccionCustomRepository.findSeccionByNameById(idBiblioteca,nombreSeccion);
        SeccionDTO seccionDTO = seccionMapper.seccionToSeccionDTO(seccion);
        return seccionDTO;
    }
}
