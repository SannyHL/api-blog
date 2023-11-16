package com.br.blog.repository;

import com.br.blog.model.ComentarioPostagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioPostagemRepository extends JpaRepository<ComentarioPostagem, Integer> {


 @Query("SELECT new com.br.blog.model.dto.ComentarioPostagemDTO(comentarioPostagem.id, comentarioPostagem.texto, comentarioPostagem.usuario.id,
comentarioPostagem.postagem.id, comentarioPostagem.dataCriacao, comentarioPostagem.dataAlteracao, comentarioPostagem.ativo)" +
            "FROM ComentarioPostagem comentarioPostagem WHERE comentarioPostagem.id = :idComentario")
    ComentarioPostagemDTO buscarPorId(@Param("idComentario") Integer idComentario);

    @Query("SELECT new com.br.blog.model.dto.ComentarioPostagemDTO(comentarioPostagem.id, comentarioPostagem.texto, comentarioPostagem.usuario.id,
comentarioPostagem.postagem.id, comentarioPostagem.dataCriacao, comentarioPostagem.dataAlteracao, comentarioPostagem.ativo)" +
            "FROM ComentarioPostagem comentarioPostagem WHERE comentarioPostagem.ativo =true")
    List<ComentarioPostagemDTO> buscarTodosComentariosAtivos();

    @Query("SELECT new com.br.blog.model.dto.ComentarioPostagemDTO(comentarioPostagem.id, comentarioPostagem.texto, comentarioPostagem.usuario.id,
comentarioPostagem.postagem.id, comentarioPostagem.dataCriacao, comentarioPostagem.dataAlteracao, comentarioPostagem.ativo)" +
            "FROM ComentarioPostagem comentarioPostagem WHERE comentarioPostagem.ativo =true AND comentarioPostagem.postagem.id = :postagemId")
    List<ComentarioPostagemDTO> buscarTodosComentariosAtivosPorPostagem(@Param("postagemId") Integer postagemId);

}
