package com.br.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioPostagemDTO {

    private Integer id;
    private String texto;
    private Date dataCriacao;
    private Integer postagemId;

    public ComentarioPostagemDTO(Integer id, String texto, Date dataCriacao, Integer postagemId) {
        this.id = id;
        this.texto = texto;
        this.dataCriacao = dataCriacao;
        this.postagemId = postagemId;
    }
}
