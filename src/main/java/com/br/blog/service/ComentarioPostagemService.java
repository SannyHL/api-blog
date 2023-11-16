package com.br.blog.service;

import com.br.blog.model.dto.ComentarioPostagemDTO;

public interface ComentarioPostagemService {

    Integer salvar(ComentarioPostagemDTO comentarioPostagemDTO);
    Integer atualizar(ComentarioPostagemDTO comentarioPostagemDTO);
    List<ComentarioPostagemDTO> buscarTodosComentariosAtivos();
    ComentarioPostagemDTO buscarPorId(Integer comentarioId);
    void deletar(Integer comentarioId);
    
}
