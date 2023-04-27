package com.sisweb.api.service;

import com.sisweb.api.entity.Endereco;
import com.sisweb.api.feign.EnderecoCliente;
import com.sisweb.api.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository repository;
    private final EnderecoCliente enderecoCliente;

    public Endereco buscarPorCep(String cep){
        return enderecoCliente.getEndereco(cep);
    }

    public Endereco save(Endereco endereco){
        return repository.save(endereco);
    }
}
