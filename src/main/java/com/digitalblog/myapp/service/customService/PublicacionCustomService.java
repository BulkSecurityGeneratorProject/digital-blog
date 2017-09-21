package com.digitalblog.myapp.service.customService;


import com.digitalblog.myapp.service.dto.PublicacionDTO;

import java.util.List;

/**
 * Created by Maureen on 3/14/2017.
 */
public interface PublicacionCustomService {

    //encuentra todas las publicaciones
    List<PublicacionDTO> findAllPublicaciones(Long id);
    //encuentra todas las publicaciones por seccion
    List<PublicacionDTO> findByTema(Long seccion );
    //encuentra todas las publicaciones que tienen estado 2
    List<PublicacionDTO> finByPublicacionesPublicadas();
    //encuentra todas las publicaciones por el nombre,categoria,autor etc..
    List<PublicacionDTO> finByPublicacionesPorBusqueda(String nombre);
    //encuentra todas las publicaciones por la categoria y tema eligida
    List<PublicacionDTO> findByCategoriaYTema(String categoriaNombre,String temaNombre);
}

