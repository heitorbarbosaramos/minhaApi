package com.sisweb.api.service;

import com.sisweb.api.entity.Endereco;
import com.sisweb.api.feign.EnderecoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoCliente enderecoCliente;

    public Endereco buscarPorCep(String cep){
        return enderecoCliente.getEndereco(cep);
    }
}
