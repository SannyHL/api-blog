package com.br.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImagensPostagemDTO {

    private Integer id;
    private String extensao;
    private Integer postagemId;

    public ImagensPostagemDTO(String extensao, Integer postagemId) {
        this.extensao = extensao;
        this.postagemId = postagemId;
    }

    public ImagensPostagemDTO(Integer id, String extensao, Integer postagemId) {
        this.id = id;
        this.extensao = extensao;
        this.postagemId = postagemId;
    }
}
