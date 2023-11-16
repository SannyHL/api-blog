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
    private Date dataAlteracao;
    private Integer postagemId;
    private Integer usuarioId;
    private Boolean ativo;

    public ComentarioPostagemDTO(Integer id, String texto,Integer usuarioId,Integer postagemId, Date dataCriacao, Date dataAlteracao, Boolean ativo) {
        this.id = id;
        this.texto = texto;
        this.usuarioId = usuarioId;
        this.postagemId = postagemId
        this.dataCriacao = dataCriacao;
        this.dataAlteracao = dataAlteracao;
        this.ativo = ativo;
    }
}