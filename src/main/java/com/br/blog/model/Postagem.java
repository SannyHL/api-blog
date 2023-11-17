package com.br.blog.model;


import com.br.blog.model.dto.PostagemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "POSTAGEM")
public class Postagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "TEXTO")
    private String texto;
    @ManyToOne
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;
    @Column(name = "DATA_CRIACAO")
    private Date dataCriacao;
    @Column(name = "DATA_ALTERACAO")
    private Date dataAlteracao;
    @Column(name = "ATIVO")
    private Boolean ativo;

    public Postagem(PostagemDTO postagemDTO) {
        this.id = postagemDTO.getId();
        this.ativo = postagemDTO.getAtivo();
        this.texto = postagemDTO.getTexto();
        this.usuario = new Usuario(postagemDTO.getUsuarioId());
        this.dataCriacao = postagemDTO.getDataCriacao();
        this.dataAlteracao = postagemDTO.getDataAlteracao();
    }

    public Postagem(Integer postagemId) {
        this.id = postagemId;
    }
}
