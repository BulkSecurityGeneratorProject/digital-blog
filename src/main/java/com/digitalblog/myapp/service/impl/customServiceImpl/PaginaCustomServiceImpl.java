package com.digitalblog.myapp.service.impl.customServiceImpl;

import com.digitalblog.myapp.domain.Pagina;
import com.digitalblog.myapp.repository.PaginaRepository;
import com.digitalblog.myapp.repository.customRepository.PaginaRepositoryCustom;
import com.digitalblog.myapp.service.customService.PaginaCustomService;
import com.digitalblog.myapp.service.dto.PaginaDTO;
import com.digitalblog.myapp.service.mapper.PaginaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Service Implementation for managing Pagina.
 */
@Service
@Transactional
public class PaginaCustomServiceImpl implements PaginaCustomService {

    private final Logger log = LoggerFactory.getLogger(PaginaCustomServiceImpl.class);

    private final PaginaRepositoryCustom paginaRepositoryCustom;

    private final PaginaRepository paginaRepository;

    private final PaginaMapper paginaMapper;

    public PaginaCustomServiceImpl(PaginaRepositoryCustom paginaRepositoryCustom, PaginaRepository paginaRepository, PaginaMapper paginaMapper) {
        this.paginaRepositoryCustom = paginaRepositoryCustom;
        this.paginaRepository = paginaRepository;
        this.paginaMapper = paginaMapper;
    }


    /**
     * @author Jose nuñez
     * Método que obtiene las paginas por medio de id de un capituli
     * @param id
     * @return ArrayList<PaginaDTO>
     * @version 1.0
     */
    @Transactional(readOnly = true)
    public ArrayList<PaginaDTO> buscarPaginasPorCapitulo(Long id) {

        ArrayList<Pagina> paginas = paginaRepositoryCustom.findByIdCapitulo(id);
        ArrayList<PaginaDTO> result = new ArrayList<>();
        PaginaDTO pagina;
        for (int i = 0; i < paginas.size(); i++) {
            pagina = paginaMapper.paginaToPaginaDTO(paginas.get(i));
            result.add(pagina);
        }
        return result;

    }


    /**
     * @author Jose nuñez
     * Método que obtiene la ultima pagina de un capitulo
     * @param paginas
     * @param ultimaPagina
     * @return PaginaDTO
     * @version 1.0
     */
    @Transactional(readOnly = true)
    public PaginaDTO obtenerUltimaPaginaCapitulo(ArrayList<PaginaDTO> paginas, PaginaDTO ultimaPagina) {

        PaginaDTO finalTemporal = paginas.get((paginas.size() - 1));
        ultimaPagina.setNumeroPagina((finalTemporal.getNumeroPagina() + 1));
        return ultimaPagina;
    }

    /**
     * @author Jose nuñez
     * Obtiene las paginas de un capitulo anterior
     * @param numPagina
     * @param idCapitulo
     * @return PaginaDTO
     * @version 1.0
     */
    @Override
    public PaginaDTO obtenerPaginaAnterior(Integer numPagina,Long idCapitulo) {

        Pagina ultimaPagina = paginaRepositoryCustom.buscarUltimaPaginaPorIdYCapituloId((numPagina-1),idCapitulo);
        return paginaMapper.paginaToPaginaDTO(ultimaPagina);
    }


}
