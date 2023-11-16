package com.br.blog.service.serviceImpl;

import com.br.blog.model.ImagensPostagem;
import com.br.blog.model.dto.ImagensPostagemDTO;
import com.br.blog.repository.ImagensPostagemRepository;
import com.br.blog.service.ImagensPostagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagensPostagemServiceImpl implements ImagensPostagemService {

    @Autowired
    ImagensPostagemRepository imagensPostagemRepository;

    @Override
    public Integer salvar(ImagensPostagemDTO imagensPostagemDTO) {
        ImagensPostagem imagensPostagem = new ImagensPostagem(imagensPostagemDTO);
        imagensPostagemRepository.saveAndFlush(imagensPostagem);
        return imagensPostagem.getId();
    }
}
