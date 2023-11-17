package com.br.blog.controller;


import com.br.blog.model.dto.PostagemDTO;
import com.br.blog.service.PostagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.br.blog.service.serviceImpl.PostagemServiceImpl.CAMINHO_DA_PASTA_SAIDA;

@RestController
@RequestMapping("api/postagens")
public class PostagemController {

    @Autowired
    PostagemService postagemService;

    @PostMapping
    public ResponseEntity<Integer> salvar(@RequestBody PostagemDTO postagemDTO){
        return ResponseEntity.ok(postagemService.salvar(postagemDTO));
    }

    @GetMapping
    public  ResponseEntity<List<PostagemDTO>> buscarTodasAtivas(){
        return ResponseEntity.ok(postagemService.buscarTodasAtivas());
    }
    @GetMapping("/{id}")
    public  ResponseEntity<PostagemDTO> buscarPorId(@PathVariable("id") Integer id){
        return ResponseEntity.ok(postagemService.buscarPorId(id));
    }

    @GetMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") Integer id){
        postagemService.deletar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public  ResponseEntity<Integer> atualizar(@RequestBody PostagemDTO postagemDTO){
        return ResponseEntity.ok(postagemService.atualizar(postagemDTO));
    }

    @GetMapping("/imagens/{nomeDaImagem}")
    public ResponseEntity<Resource> obterImagem(@PathVariable String nomeDaImagem) throws IOException {
        Path imagePath = Paths.get(CAMINHO_DA_PASTA_SAIDA, nomeDaImagem);

        if (!imagePath.toFile().exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(imagePath.toFile()));

        MediaType mediaType = MediaType.IMAGE_JPEG;
        if (nomeDaImagem.toLowerCase().endsWith(".png")) {
            mediaType = MediaType.IMAGE_PNG;
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + nomeDaImagem)
                .body(resource);

    }

}
