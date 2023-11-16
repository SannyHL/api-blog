package com.br.blog.service.serviceImpl;

import com.br.blog.model.ImagensPostagem;
import com.br.blog.model.dto.ComentarioPostagemDTO;
import com.br.blog.repository.ComentarioPostagemRepository;
import com.br.blog.service.ComentarioPostagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioPostagemServiceImpl implements ImagensPostagemService {

    @Autowired
    ComentarioPostagemRepository comentarioPostagemRepository;

    @Override
    public Integer salvar(ComentarioPostagemDTO comentarioPostagemDTO) {
        ComentarioPostagem comentarioPostagem = new ComentarioPostagem(comentarioPostagemDTO);
        comentarioPostagemRepository.saveAndFlush(comentarioPostagem);
        return comentarioPostagem.getId();
    }
}
