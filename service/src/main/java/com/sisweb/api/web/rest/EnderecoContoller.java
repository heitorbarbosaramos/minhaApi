package com.sisweb.api.web.rest;

import com.sisweb.api.entity.Endereco;
import com.sisweb.api.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/endereco")
@RequiredArgsConstructor
public class EnderecoContoller {

    private final EnderecoService service;

    @GetMapping("/{cep}")
    @Operation(tags = {"Endereco "}, summary = "Retorna endereço da API VIA CEP",
            description = "Requisição GET para retornar endereço da API VIA CEP", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<Endereco> buscarPorCep(@PathVariable("cep") String cep){
        log.info("REQUISICAO GET PARA BUSCAR ENDERECO POR CEP: {}", cep);
        return ResponseEntity.ok(service.buscarPorCep(cep));
    }
}
