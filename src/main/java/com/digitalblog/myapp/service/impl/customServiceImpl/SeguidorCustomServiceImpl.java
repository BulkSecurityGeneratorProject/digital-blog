package com.digitalblog.myapp.service.impl.customServiceImpl;

import com.digitalblog.myapp.domain.Seguidor;
import com.digitalblog.myapp.repository.customRepository.SeguidorRepositoryCustom;
import com.digitalblog.myapp.service.customService.SeguidorCustomService;
import com.digitalblog.myapp.service.dto.SeguidorDTO;
import com.digitalblog.myapp.service.mapper.SeguidorMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SeguidorCustomServiceImpl implements SeguidorCustomService {


    private final SeguidorRepositoryCustom seguidorRepository;

    private final SeguidorMapper seguidorMapper;

    public SeguidorCustomServiceImpl(SeguidorRepositoryCustom seguidorRepository, SeguidorMapper seguidorMapper) {
        this.seguidorRepository = seguidorRepository;
        this.seguidorMapper = seguidorMapper;
    }

    /**
     * @author Maureen Leon
     * findAllByUserId obtiene los seguidores de un usuario especifico
     * @param id del usuario
     * @return List<SeguidorDTO>
     * @version 1.0
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<SeguidorDTO> findAllByUserId(Long id) {
        List<SeguidorDTO> result = seguidorRepository.findAllByUserId(id).stream()
            .map(seguidorMapper::seguidorToSeguidorDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     * @author Maureen Leon
     * findAllSeguidosByUserId obtiene los usuarios seguidos por un usuario especifico
     * @param id del usuario
     * @return List<SeguidorDTO>
     * @version 1.0
     */
    @Override
    public List<SeguidorDTO> findAllSeguidosByUserId(Long id) {
        List<SeguidorDTO> result = seguidorRepository.findAllSeguidosByUserId(id).stream()
            .map(seguidorMapper::seguidorToSeguidorDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     * @author Maureen Leon
     * findSeguimientoByUsersId obtiene el objeto seguimiento
     * @param seguido
     * @param seguidor
     * @return SeguidorDTO
     * @version 1.0
     */
    @Override
    public SeguidorDTO findSeguimientoByUsersId(Long seguido, Long seguidor) {
        Seguidor seguimiento = seguidorRepository.findOne(seguido,seguidor);
        SeguidorDTO seguidorDTO = seguidorMapper.seguidorToSeguidorDTO(seguimiento);
        return seguidorDTO;
    }
}
