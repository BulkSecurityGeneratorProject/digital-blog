package com.digitalblog.myapp.service.impl.customServiceImpl;

import com.digitalblog.myapp.repository.PublicacionRepository;
import com.digitalblog.myapp.repository.customRepository.PublicacionRepositoryCustom;
import com.digitalblog.myapp.service.customService.PublicacionCustomService;
import com.digitalblog.myapp.service.dto.PublicacionDTO;
import com.digitalblog.myapp.service.mapper.PublicacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PublicacionCustomServiceImpl implements PublicacionCustomService {

    private final Logger log = LoggerFactory.getLogger(PublicacionCustomServiceImpl.class);

    private final PublicacionRepositoryCustom publicacionRepositoryCustom;

    private final PublicacionRepository publicacionRepository;

    private final PublicacionMapper publicacionMapper;

    public PublicacionCustomServiceImpl(PublicacionRepositoryCustom publicacionRepositoryCustom, PublicacionRepository publicacionRepository, PublicacionMapper publicacionMapper) {
        this.publicacionRepositoryCustom = publicacionRepositoryCustom;
        this.publicacionRepository = publicacionRepository;
        this.publicacionMapper = publicacionMapper;
    }

    /**
     * @author Christopher Sullivan
     * Encuentra todas las publicaciones existentes
     * @param id se le envia el de la publicacion y tambien el estado que es 2 que significa que esta publicada
     * @return una lista de publicaciones
     * @version 1.0
     */
    @Override
    public List<PublicacionDTO> findAllPublicaciones(Long id) {
        List<PublicacionDTO> result = publicacionRepositoryCustom.findAll(id,2).stream()
            .map(publicacionMapper::publicacionToPublicacionDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * @author Christopher Sullivan
     * Encuentras las publicaciones por seccion de biblioteca persona
     * @param seccion recibe el id de la seccion
     * @return una lista de la publicacion de la seccion especifica
     * @version 1.0
     */
    @Transactional(readOnly = true)
    public List<PublicacionDTO> findByTema(Long seccion) {
        log.debug("Request to get all Publicacions");
        List<PublicacionDTO> result = publicacionRepositoryCustom.findByusuarioIdandtemaId(seccion).stream()
            .map(publicacionMapper::publicacionToPublicacionDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * @author Eduardo Guerrero
     * Encuentras todas las publicaciones que tengan estado 2 (publicada)
     * @return lista de publicaciones publicadas.
     * @version 1.0
     */
    @Override
    public List<PublicacionDTO> finByPublicacionesPublicadas() {
        List<PublicacionDTO> result = publicacionRepositoryCustom.findByPublicacionPublicada().stream()
            .map(publicacionMapper::publicacionToPublicacionDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     * @author Eduardo Guerrero
     * Encuentra una publicacion por nombre.
     * @param nombre de la publicacion
     * @return lista de publicaciones por nombre
     * @version 1.0
     */
    @Override
    public List<PublicacionDTO> finByPublicacionesPorBusqueda(String nombre) {
        log.debug("Request to get all Publicacions");
        List<PublicacionDTO> result = publicacionRepositoryCustom.findByPublicacionPorAutorTemaCategoria(nombre).stream()
            .map(publicacionMapper::publicacionToPublicacionDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     * @author Eduardo Guerrero
     * Metodo que busca publicaciones por categoria y tema
     * @param categoriaNombre nombre de la categoria que el usuario escoge
     * @param temaNombre nombre del tema que el usuario escoge
     * @return lista de publicaciones por la categoria y el tema
     * @version 1.0
     */
    @Override
    public List<PublicacionDTO> findByCategoriaYTema(String categoriaNombre, String temaNombre) {
        log.debug("Request to get all Publicacions");
        List<PublicacionDTO> result = publicacionRepositoryCustom.findByPublicacionPorCategoriaYtema(categoriaNombre,temaNombre).stream()
            .map(publicacionMapper::publicacionToPublicacionDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

}
