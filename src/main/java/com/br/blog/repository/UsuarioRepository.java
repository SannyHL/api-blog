package com.br.blog.repository;

import com.br.blog.model.Usuario;
import com.br.blog.model.dto.UsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Boolean existsUsuarioByEmail(String email);

    UserDetails findByEmail(String email);

    @Query("SELECT new com.br.blog.model.dto.UsuarioDTO(usuario.id, usuario.nome, usuario.email, usuario.dataCriacao, usuario.ativo)" +
            "FROM Usuario usuario")
    List<UsuarioDTO> buscarTodosUsuarios();
}
