package com.digitalblog.myapp.service.customService;

import com.digitalblog.myapp.service.dto.TemaDTO;

/**
 * Created by jgm16 on 09/03/2017.
 */
public interface TemaCustomService {
    //En cuentra el tema por el nombre
    TemaDTO findTemaByName(String nombre);

    //Guarda el tema en la bd
    TemaDTO save(TemaDTO temaDTO);
}
