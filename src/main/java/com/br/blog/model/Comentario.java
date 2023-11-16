package com.br.blog.model;

import com.br.blog.model.ComentarioPostagemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COMENTARIO_POSTAGEM")
public class ComentarioPostagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "TEXTO")
    private String texto;
    @Column(name = "DATA_CRIACAO")
    private Date dataCriacao;
    @ManyToOne
    @JoinColumn(name = "POSTAGEM_ID")
    private Postagem postagem;

    public ComentarioPostagem(ComentarioPostagemDTO comentario) {
        this.id = comentario.getId();
        this.texto = comentario.getTexto();
        this.dataCriacao = comentario.getDataCriacao();
        this.postagem = new Postagem(comentario.getPostagemId());
    }
}
