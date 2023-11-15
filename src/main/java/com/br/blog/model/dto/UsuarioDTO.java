package com.br.blog.model.dto;

import com.br.blog.enumeration.UsuarioRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private UsuarioRole role;
    private Date dataCriacao;
    private Boolean ativo;

    public UsuarioDTO(Integer id, String nome, String email, Date dataCriacao, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataCriacao = dataCriacao;
        this.ativo = ativo;
    }
}
