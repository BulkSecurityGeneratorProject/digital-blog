package com.digitalblog.myapp.web.customResource;


import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.CapituloService;
import com.digitalblog.myapp.service.CoolaboradorService;
import com.digitalblog.myapp.service.PublicacionService;
import com.digitalblog.myapp.service.customService.CapituloCustomService;
import com.digitalblog.myapp.service.customService.CoolaboradorCustomService;
import com.digitalblog.myapp.service.dto.CapituloDTO;
import com.digitalblog.myapp.service.dto.CoolaboradorDTO;
import com.digitalblog.myapp.service.dto.PublicacionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing Coolaborador.
 */
@RestController
@RequestMapping("/apiCustom")
public class ColaboradorResourceCustom {

    private final Logger log = LoggerFactory.getLogger(com.digitalblog.myapp.web.rest.CoolaboradorResource.class);

    private static final String ENTITY_NAME = "coolaborador";

    private final CoolaboradorService coolaboradorService;

    private final CapituloCustomService capituloCustomService;

    private final CapituloService capituloService;

    private final CoolaboradorCustomService coolaboradorCustomService;

    private final PublicacionService publicacionService;


    public ColaboradorResourceCustom(CoolaboradorService coolaboradorService, CapituloCustomService capituloCustomService1, CapituloService capituloService, CoolaboradorCustomService coolaboradorCustomService, SimpMessageSendingOperations messagingTemplate, PublicacionService publicacionService) {
        this.coolaboradorService = coolaboradorService;
        this.capituloCustomService = capituloCustomService1;
        this.capituloService = capituloService;
        this.coolaboradorCustomService = coolaboradorCustomService;
        this.publicacionService = publicacionService;
    }

    /**
     *@author Maureen Leon
     * @return the ResponseEntity with status 201 (Created) and with body the new coolaboradorDTO, or with status 400 (Bad Request) if the coolaborador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @param colaboraciones
     * @return CapituloDTO
     * @version 1.0
     */
    @PostMapping("/coolaboradores")
    @Timed
    public CapituloDTO createCoolaborador(@RequestBody List<CoolaboradorDTO> colaboraciones) throws URISyntaxException {
       for(int i=0;i<colaboraciones.size();i++){
           CoolaboradorDTO colDTO =   coolaboradorService.save(colaboraciones.get(i));
       }
        CapituloDTO capitulo = capituloService.findOne(colaboraciones.get(0).getCapituloCId());
        return capitulo;
    }

    /**
     * @author Jose Nuñez
     * Este metodo recibe un capitulo y luego notifica al siguiente participante, le manda el capitulo para que el participante escriba
     * @param capitulo
     * @throws URISyntaxException
     * @version 1.0
     */
    @PostMapping("/terminarParticipacion")
    @Timed
    public void terminarParticipacionPubCompartida(@RequestBody CapituloDTO capitulo) throws URISyntaxException {
        ArrayList<CapituloDTO> listaCapitulos= new ArrayList<CapituloDTO>();
                listaCapitulos = capituloCustomService.buscarCapituloPorPublicacion(capitulo.getIdPublicacionCId());
                int numCapituloActual = capitulo.getNumeroCapitulo();
                CapituloDTO capSiguiente = new CapituloDTO();
                for (int i=0; i < listaCapitulos.size(); i++){
                    //Obtener el capitulo anterior y luego buscamos al siguiente colaborador para notificarle
                    if((numCapituloActual + 1) == listaCapitulos.get(i).getNumeroCapitulo()){
                        capSiguiente = listaCapitulos.get(i);
                        break;
                    }
                }
                if(capSiguiente.getId() != null) {
                    CoolaboradorDTO siguienteCoolaborador = coolaboradorCustomService.obtenerCoolaboradorPorIdCapitulo(capSiguiente.getId());
                    coolaboradorCustomService.notificarSiguienteColaborador(capSiguiente, siguienteCoolaborador);
                }else {
                    if (capitulo.getCapituloId() == null) {
                        capSiguiente = listaCapitulos.get(0);
                        CoolaboradorDTO siguienteCoolaborador = coolaboradorCustomService.obtenerCoolaboradorPorIdCapitulo(capSiguiente.getId());
                        coolaboradorCustomService.notificarColaboradorFinal(capSiguiente, siguienteCoolaborador, listaCapitulos.get(listaCapitulos.size() - 1).getNumeroCapitulo());
                    }else {
                        PublicacionDTO publicacionDTO = publicacionService.findOne(capitulo.getIdPublicacionCId());
                        publicacionDTO.setEstado(2);
                        publicacionService.save(publicacionDTO);
            }
        }
    }

}

