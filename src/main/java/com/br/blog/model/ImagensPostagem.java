package com.br.blog.model;

import com.br.blog.model.dto.ImagensPostagemDTO;
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
@Table(name = "IMAGENS_POSTAGEM")
public class ImagensPostagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "EXTENSAO")
    private String extensao;
    @ManyToOne
    @JoinColumn(name = "POSTAGEM_ID")
    private Postagem postagem;

    public ImagensPostagem(ImagensPostagemDTO imagensPostagemDTO) {
        this.id = imagensPostagemDTO.getId();
        this.extensao = imagensPostagemDTO.getExtensao();
        this.postagem = new Postagem(imagensPostagemDTO.getPostagemId());
    }
}
