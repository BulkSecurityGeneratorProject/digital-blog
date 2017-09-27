package com.digitalblog.myapp.web.customResource;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.customService.SeguidorCustomService;
import com.digitalblog.myapp.service.dto.SeguidorDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiCustom")
public class SeguidorResourceCustom {

    private static final String ENTITY_NAME = "seguidor";
    private final SeguidorCustomService seguidorCustomService;

    public SeguidorResourceCustom(SeguidorCustomService pseguidorCustomService){
            this.seguidorCustomService= pseguidorCustomService;
    }
    /**
     * @author Maureen Leon
     * GET /seguidoresPorUserId/{id} :obtiene todos los seguidores de un usuario
     * @param id the id  del usuario
     * @return  List<SeguidorDTO>
     * @version 1.0
     */
    @GetMapping("/seguidoresPorUserId/{id}")
    @Timed
    public List<SeguidorDTO> getAllSeguidors(@PathVariable Long id) {
        List<SeguidorDTO>  result = seguidorCustomService.findAllByUserId(id);
        return result;
    }
    /**
     * @author Maureen Leon
     * GET /seguidosPorUserId/{id} :obtiene todos los seguidos de un usuario
     * @param id the id  del usuario
     * @return  List<SeguidorDTO>
     * @version 1.0
     */
    @GetMapping("/seguidosPorUserId/{id}")
    @Timed
    public List<SeguidorDTO> seguidosPorUserId(@PathVariable Long id) {
        List<SeguidorDTO>  result = seguidorCustomService.findAllSeguidosByUserId(id);
        return result;
    }


    /**
     * @author Maureen Leon
     * post /seguidosPorUserId/{id} : recibe un seguimiento existente
     * @param seguidorDTO
     * @return SeguidorDTO
     * @version 1.0
     * */
    @PostMapping("/seguidors")
    @Timed
    public SeguidorDTO obtenerSeguimiento(@RequestBody SeguidorDTO seguidorDTO) {
        SeguidorDTO result = seguidorCustomService.findSeguimientoByUsersId(seguidorDTO.getIdSeguidoId(),seguidorDTO.getIdSeguidorId());
        return result;
    }

}
