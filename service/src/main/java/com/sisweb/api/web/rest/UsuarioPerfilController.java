package com.sisweb.api.web.rest;

import com.sisweb.api.entity.dto.UsuarioPerfilDTO;
import com.sisweb.api.service.UsuarioPerfilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/perfil")
@RequiredArgsConstructor
public class UsuarioPerfilController {

    private final UsuarioPerfilService service;

    @PostMapping
    public ResponseEntity<UsuarioPerfilDTO> save(@RequestBody UsuarioPerfilDTO dto){
        log.info("REQUISICAO POST PARA SALVAR UM PERFIL");
        dto = service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioPerfilDTO> findById(@PathVariable("id") Long id){
        log.info("REQUISICAO GET PARA RECUPERAR UM PERFIL: {}", id);
        return ResponseEntity.ok(service.findByIdDTO(id));
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioPerfilDTO>> findAll(
            @RequestParam(name = "page",         defaultValue = "0")    Integer page,
            @RequestParam(name = "linesPerPage", defaultValue = "10")   Integer linesPerPage,
            @RequestParam(name = "orderBy",      defaultValue = "nome") String orderBy,
            @RequestParam(name = "direction",    defaultValue = "ASC")  String direction){
        log.info("REQUISICAO GET PARA RECUPERAR LISTA DE PERFIL");
        return ResponseEntity.ok(service.findAll(page, linesPerPage, orderBy, direction));
    }
}
