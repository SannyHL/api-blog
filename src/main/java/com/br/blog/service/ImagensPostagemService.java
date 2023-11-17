package com.br.blog.service;

import com.br.blog.model.dto.ImagensPostagemDTO;

public interface ImagensPostagemService {

    Integer salvar(ImagensPostagemDTO imagensPostagemDTO);
    ImagensPostagemDTO buscarPorPostagemId(Integer postagemId);
}
