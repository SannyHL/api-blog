package com.br.blog.service.serviceImpl;

import com.br.blog.model.Usuario;
import com.br.blog.model.dto.UsuarioDTO;
import com.br.blog.repository.UsuarioRepository;
import com.br.blog.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Integer criarUsuario(UsuarioDTO usuarioDto) {
        if(existsUsuarioByEmail(usuarioDto.getEmail())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado no sistema.");
        }
        BCryptPasswordEncoder senhaCodificada = new BCryptPasswordEncoder();
        usuarioDto.setSenha(senhaCodificada.encode(usuarioDto.getSenha()));
        Usuario usuario = new Usuario(usuarioDto.getNome(), usuarioDto.getEmail(), usuarioDto.getSenha());
        usuario.setDataCriacao(new Date());
        usuario.setAtivo(true);
        usuarioRepository.saveAndFlush(usuario);
        return usuario.getId();
    }

    @Override
    public Boolean existsUsuarioByEmail(String email) {
        return usuarioRepository.existsUsuarioByEmail(email);
    }

    @Override
    public List<UsuarioDTO> buscarTodosUsuarios() {
        return usuarioRepository.buscarTodosUsuarios();
    }

}
