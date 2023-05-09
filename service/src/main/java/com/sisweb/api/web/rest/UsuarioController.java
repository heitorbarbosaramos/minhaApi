package com.sisweb.api.web.rest;

import com.sisweb.api.entity.dto.UsuarioDTO;
import com.sisweb.api.entity.dto.UsuarioFormDTO;
import com.sisweb.api.entity.dto.UsuarioResetaSenhaDTO;
import com.sisweb.api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UsuarioDTO> save(@RequestBody @Valid UsuarioFormDTO dto){
        log.info("REQUISICAO POST PARA CRIAR OU ATUALIZAR USUARIO");
        UsuarioDTO createUpdate = service.createUpdate(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(createUpdate.getId()).toUri();
        return ResponseEntity.created(uri).body(createUpdate);
    }

    @GetMapping("/findlogin/{login}")
    public ResponseEntity<UsuarioDTO> findByLogin(@PathVariable String login){
        log.info("REQUISICAO GET PARA BUSCAR USUARIO POR LOGIN");
        return ResponseEntity.ok(service.findByLogin(login));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable("id") Long id){
        log.info("REQUISICAO GET PARA RESGATAR USUARIO POR ID");
        return ResponseEntity.ok(service.findUsuario(id));
    }

    @PostMapping("/ListaFiltro")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Page<UsuarioDTO>> listarFiltra(
            @RequestBody UsuarioDTO dto,
            @RequestParam(name = "page",         defaultValue = "0")    Integer page,
            @RequestParam(name = "linesPerPage", defaultValue = "10")   Integer linesPerPage,
            @RequestParam(name = "orderBy",      defaultValue = "nome") String orderBy,
            @RequestParam(name = "direction",    defaultValue = "ASC")  String direction){
        log.info("REQUISICAO POST PARA FILTRAR E LISTA USUARIOS {}", dto.getLogin());
        return ResponseEntity.ok(service.usuarioFiltro(dto, page, linesPerPage, orderBy, direction));
    }

    @GetMapping("/geralinkrecuperasenha/{email}")
    public ResponseEntity<Void> geraLinkRecuperaSenha(@PathVariable("email") String email){
        log.info("GERA LINK RECUPERACAO SENHA");
        service.geraLinkEsqueciSenha(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recuperasenhaetapa1")
    public ResponseEntity<UsuarioResetaSenhaDTO>recuperaSenhaEtapa1(@RequestBody UsuarioResetaSenhaDTO dto){
        log.info("RECUPERA SENHA ETAPA 1");
        return ResponseEntity.ok(service.recuperaSenhaEtapa1(dto));
    }

    @PostMapping("/recuperasenhaetapa2")
    public ResponseEntity<Void>recuperaSenhaEtapa2(@RequestBody UsuarioResetaSenhaDTO dto){
        log.info("RECUPERA SENHA ETAPA 2");
        service.recuperaSenhaEtapa2(dto);
        return ResponseEntity.ok().build();
    }
}
