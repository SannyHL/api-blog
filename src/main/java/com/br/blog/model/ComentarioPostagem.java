package com.br.blog.model;

import com.br.blog.model.dto.ComentarioPostagemDTO;
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
@Table(name = "COMENTARIO_POSTAGEM")
public class ComentarioPostagem {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        private Integer id;
        @Column(name = "TEXTO")
        private String texto;
        @ManyToOne
        @JoinColumn(name = "POSTAGEM_ID")
        private Postagem postagem;
        @ManyToOne
        @JoinColumn(name = "USUARIO_ID")
        private Usuario usuario;
        @Column(name = "DATA_CRIACAO")
        private Date dataCriacao;
        @Column(name = "DATA_ALTERACAO")
        private Date dataAlteracao;
        @Column(name = "ATIVO")
        private Boolean ativo;


        public ComentarioPostagem(ComentarioPostagemDTO comentario) {
            this.id = comentario.getId();
            this.texto = comentario.getTexto();
            this.dataCriacao = comentario.getDataCriacao();
            this.dataAlteracao = comentario.getDataAlteracao();
            this.postagem = new Postagem(comentario.getPostagemId());
            this.usuario = new Usuario(comentario.getUsuarioId());
            this.ativo = comentario.getAtivo();
        }

}
