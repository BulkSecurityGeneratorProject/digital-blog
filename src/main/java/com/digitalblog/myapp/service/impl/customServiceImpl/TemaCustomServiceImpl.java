package com.digitalblog.myapp.service.impl.customServiceImpl;

import com.digitalblog.myapp.domain.Tema;
import com.digitalblog.myapp.repository.TemaRepository;
import com.digitalblog.myapp.repository.customRepository.TemaRepositoryCustom;
import com.digitalblog.myapp.service.customService.TemaCustomService;
import com.digitalblog.myapp.service.dto.TemaDTO;
import com.digitalblog.myapp.service.impl.TemaServiceImpl;
import com.digitalblog.myapp.service.mapper.TemaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jgm16 on 09/03/2017.
 */
@Service
@Transactional
public class TemaCustomServiceImpl implements TemaCustomService {
    private final Logger log = LoggerFactory.getLogger(TemaCustomServiceImpl.class);

    private final TemaRepositoryCustom temaCustomRepository;

    private final TemaMapper temaMapper;


    public TemaCustomServiceImpl(TemaMapper temaMapper,TemaRepositoryCustom temaCustomRepository) {
        this.temaMapper = temaMapper;
        this.temaCustomRepository = temaCustomRepository;
    }

    /**
     * @author Eduardo Guerrero
     * En cuentra el tema por el nombre
     * @param nombre recibe el nombre del tema
     * @return retorna el objeto DTO de tema
     * @version 1.0
     */
    @Override
    public TemaDTO findTemaByName(String nombre) {
        log.debug("Request to get tema : {}", nombre);
        Tema tema = temaCustomRepository.findTemaByName(nombre);
        TemaDTO temaDTO = temaMapper.temaToTemaDTO(tema);
        return temaDTO;
    }

    /**
     * @author Eduardo Guerrero
     * Guarda el tema en la BD
     * @param temaDTO recibe el objeto entero del tema a guardar
     * @return retorna el DTO del tema
     * @version 1.0
     */
    @Override
    public TemaDTO save(TemaDTO temaDTO) {
            String texto = temaDTO.getNombre();
            char primero = texto.charAt(0);
            texto = Character.toUpperCase(primero)+ texto.substring(1,texto.length()).toLowerCase();
            temaDTO.setNombre(texto);
            Tema tema = temaMapper.temaDTOToTema(temaDTO);
            tema = temaCustomRepository.save(tema);
            TemaDTO result = temaMapper.temaToTemaDTO(tema);
        return result;
    }
}
