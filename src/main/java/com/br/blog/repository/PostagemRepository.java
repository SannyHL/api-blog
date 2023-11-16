package com.br.blog.repository;

import com.br.blog.model.Postagem;
import com.br.blog.model.dto.PostagemDTO;
import com.br.blog.model.dto.UsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Integer> {

    @Query("SELECT new com.br.blog.model.dto.PostagemDTO(postagem.id, postagem.texto, postagem.usuario.id, postagem.dataCriacao, postagem.dataAlteracao, postagem.ativo)" +
            "FROM Postagem postagem WHERE postagem.id = :idPostagem")
    PostagemDTO buscarPorId(@Param("idPostagem") Integer idPostagem);

    @Query("SELECT new com.br.blog.model.dto.PostagemDTO(postagem.id, postagem.texto, postagem.usuario.id, postagem.dataCriacao, postagem.dataAlteracao, postagem.ativo)" +
            "FROM Postagem postagem WHERE postagem.ativo =true")
    List<PostagemDTO> buscarTodasPostagensAtivas();

    @Query("SELECT new com.br.blog.model.dto.PostagemDTO(postagem.id, postagem.texto, postagem.usuario.id, postagem.dataCriacao, postagem.dataAlteracao, postagem.ativo)" +
            "FROM Postagem postagem WHERE postagem.ativo =true AND postagem.usuario.id = :idUsuario")
    List<PostagemDTO> buscarTodasPostagensAtivasPorUsuario(@Param("idUsuario") Integer idUsuario);

}
