package com.sisweb.api.security;

import com.sisweb.api.entity.Usuario;
import com.sisweb.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository repository;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Usuario usuario = repository.findByLogin(login);

        if(usuario == null){
            throw new UsernameNotFoundException("Login não existe");
        }

        UsuarioSpringSecurity user = new UsuarioSpringSecurity(usuario.getId(), usuario.getLogin(), usuario.getSenha(), usuario.getNome(), usuario.getAtivo(), usuario.getPerfis());

        return user;
    }
}
