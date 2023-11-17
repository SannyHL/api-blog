package com.br.blog.repository;

import com.br.blog.model.ImagensPostagem;
import com.br.blog.model.dto.ImagensPostagemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImagensPostagemRepository extends JpaRepository<ImagensPostagem, Integer> {


    @Query("SELECT new com.br.blog.model.dto.ImagensPostagemDTO(imagensPostagem.id, imagensPostagem.extensao, imagensPostagem.postagem.id)" +
            "FROM ImagensPostagem imagensPostagem WHERE imagensPostagem.postagem.id = :idPostagem")
    ImagensPostagemDTO buscarPorPostagemId(@Param("idPostagem") Integer idPostagem);
}
