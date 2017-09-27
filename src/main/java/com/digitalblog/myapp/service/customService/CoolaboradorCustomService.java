package com.digitalblog.myapp.service.customService;

import com.digitalblog.myapp.service.dto.CapituloDTO;
import com.digitalblog.myapp.service.dto.CoolaboradorDTO;

/**
 * Created by jose_ on 13/4/2017.
 */
public interface CoolaboradorCustomService {

    CoolaboradorDTO obtenerCoolaboradorPorIdCapitulo(Long idCapituloSiguiente);

    void notificarSiguienteColaborador(CapituloDTO capSiguiente, CoolaboradorDTO coolaborador);

    void notificarColaboradorFinal(CapituloDTO capSiguiente, CoolaboradorDTO coolaborador, Integer numCapFinal);
}
