package com.br.blog.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostagemDTO {

    private Integer id;
    private String texto;
    private String imagem;
    private Integer usuarioId;
    private Date dataCriacao;
    private Date dataAlteracao;
    private Boolean ativo;

    public PostagemDTO(Integer id, String texto, Integer usuarioId, Date dataCriacao, Date dataAlteracao, Boolean ativo) {
        this.id = id;
        this.texto = texto;
        this.usuarioId = usuarioId;
        this.dataCriacao = dataCriacao;
        this.dataAlteracao = dataAlteracao;
        this.ativo = ativo;
    }
}
