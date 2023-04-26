package com.sisweb.api.service;

import com.sisweb.api.entity.UsuarioPerfil;
import com.sisweb.api.entity.dto.UsuarioPerfilDTO;
import com.sisweb.api.mapper.UsuarioPerfilMapper;
import com.sisweb.api.repository.UsuarioPerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UsuarioPerfilService {

    private final UsuarioPerfilRepository repository;

    private final UsuarioPerfilMapper mapper;

    public UsuarioPerfilDTO save (UsuarioPerfilDTO dto){

        UsuarioPerfil perfil = mapper.toEntity(dto);

        if(dto.getId() != null){
            perfil = findById(dto.getId());
            perfil.setPerfil(dto.getPerfil());
            perfil.setNome(dto.getNome());
        }

        perfil = repository.save(perfil);
        return mapper.toDto(perfil);
    }

    public UsuarioPerfil findById(Long id){
        return repository.findById(id).orElseThrow(()-> new NoSuchElementException("Perfil não exite"));
    }

    public UsuarioPerfilDTO findByIdDTO(Long id){
        return mapper.toDto(findById(id));
    }

    public Page<UsuarioPerfilDTO> findAll(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<UsuarioPerfil> perfils = repository.findAll(pageRequest);
        Page<UsuarioPerfilDTO> dtos = perfils.map(mapper::toDto);
        return dtos;
    }
}
