package com.br.blog.service;

import com.br.blog.model.dto.ComentarioPostagemDTO;

import java.util.List;

public interface ComentarioPostagemService {

    Integer salvar(ComentarioPostagemDTO comentarioPostagemDTO);
    Integer atualizar(ComentarioPostagemDTO comentarioPostagemDTO);
    List<ComentarioPostagemDTO> buscarTodosComentariosAtivosPorPostagem(Integer idPostagem);
    ComentarioPostagemDTO buscarPorId(Integer comentarioId);
    void deletar(Integer comentarioId);
    
}
