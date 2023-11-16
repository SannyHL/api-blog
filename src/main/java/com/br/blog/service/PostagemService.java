package com.br.blog.service;

import com.br.blog.model.dto.PostagemDTO;

import java.util.List;

public interface PostagemService {

    Integer salvar(PostagemDTO postagemDTO);
    Integer atualizar(PostagemDTO postagemDTO);
    List<PostagemDTO> buscarTodasAtivas();
    PostagemDTO buscarPorId(Integer postagemId);
    void deletar(Integer postagemId);
}
