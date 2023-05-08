package com.sisweb.api.service;

import com.sisweb.api.entity.Endereco;
import com.sisweb.api.entity.Usuario;
import com.sisweb.api.entity.UsuarioPerfil;
import com.sisweb.api.entity.dto.UsuarioDTO;
import com.sisweb.api.entity.dto.UsuarioFormDTO;
import com.sisweb.api.enumeration.Perfil;
import com.sisweb.api.mapper.UsuarioMapper;
import com.sisweb.api.repository.UsuarioRepository;
import com.sisweb.api.security.UsuarioLogado;
import com.sisweb.api.security.UsuarioSpringSecurity;
import com.sisweb.api.service.email.EmailSenderService;
import com.sisweb.api.service.email.corpo.CorpoEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioMapper mapper;
    private final UsuarioRepository repository;
    private final EnderecoService enderecoService;
    private final UsuarioPerfilService perfilService;
    private final EmailSenderService emailService;

    private Usuario save(Usuario usuario){
        return repository.save(usuario);
    }

    public Usuario findById(Long id){

        UsuarioSpringSecurity uss = UsuarioLogado.usuarioLogado();

        if(uss == null || !uss.temPerfil(Perfil.ROLE_ADMIN) && !uss.getId().equals(id)){
            throw new AuthorizationServiceException("Acesso negado");
        }
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Usuario não encontrado"));
    }

    public UsuarioDTO findByLogin(String login) {

        UsuarioSpringSecurity uss = UsuarioLogado.usuarioLogado();

        if(uss == null || !uss.temPerfil(Perfil.ROLE_ADMIN) && !uss.getLogin().equals(login)){
            throw new AuthorizationServiceException("Acesso negado");
        }

        Usuario usuario = repository.findByLogin(login);
        if(usuario == null){
            throw new NoSuchElementException("Login não existe");
        }
        UsuarioDTO dto = mapper.toDTO(usuario);

        return dto;
    }

    @Transactional
    public UsuarioDTO createUpdate(UsuarioFormDTO dto){
        Endereco endereco = enderecoService.save(dto.getEndereco());

        Set<UsuarioPerfil> perfis = new HashSet<>();
        perfis = dto.getIdsPerfis().stream().map(item -> perfilService.findById(item)).collect(Collectors.toSet());

        dto.setSenha(new BCryptPasswordEncoder().encode(dto.getSenha()));

        Usuario usuario = mapper.fromFormDTO(dto);
        usuario.setPerfis(perfis);
        usuario.setEndereco(endereco);

        usuario = save(usuario);
        UsuarioDTO usuarioDTO = mapper.toDTO(usuario);

        return usuarioDTO;
    }

    public UsuarioDTO findUsuario(Long id){
        return mapper.toDTO(findById(id));
    }

    public Page<UsuarioDTO> usuarioFiltro(UsuarioDTO dto, Integer page, Integer linesPerPage, String orderBy, String direction){

        Usuario usuario = mapper.toEntity(dto);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("id",                   ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("login",                ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("nome",                 ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("endereco.id",          ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("endereco.cep",         ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("endereco.logradouro",  ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("endereco.complemento", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("endereco.bairro",      ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("endereco.localidade",  ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("endereco.uf",          ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("endereco.ibge",        ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("endereco.gia",         ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Usuario> example = Example.of(usuario, matcher);

        PageRequest request = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Page<Usuario> pageUsuario = repository.findAll(example, request);
        Page<UsuarioDTO> usuarioDTOPage = pageUsuario.map(mapper::toDTO);

        return usuarioDTOPage;
    }

    public void geraLinkEsqueciSenha(String emailLogin){

        Usuario usuario = repository.findByLogin(emailLogin);

        LocalDateTime data = LocalDateTime.now().plusMinutes(30);

        Long instante = data.toInstant(ZoneOffset.of("-03:00")).getEpochSecond();
        usuario.setTimestampRecuperaSenha(instante);
        save(usuario);

        String corpoEmail = CorpoEmail.esqueciSenha(usuario.getNome(), usuario.getLogin(), data, usuario.getId(), instante );

        emailService.enviaEmail(emailLogin, "Esqueci a senha", corpoEmail);
    }
}
