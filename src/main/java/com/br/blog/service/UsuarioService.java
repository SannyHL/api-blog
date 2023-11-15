package com.br.blog.service;

import com.br.blog.model.Usuario;
import com.br.blog.model.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {

    Integer criarUsuario(Usuario usuario);
    Boolean existsUsuarioByEmail(String email);
    List<UsuarioDTO> buscarTodosUsuarios();
}
