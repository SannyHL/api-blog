package com.br.blog.controller;


import com.br.blog.model.dto.PostagemDTO;
import com.br.blog.service.PostagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") Integer id){
        postagemService.deletar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public  ResponseEntity<Integer> atualizar(@RequestBody PostagemDTO postagemDTO){
        return ResponseEntity.ok(postagemService.atualizar(postagemDTO));
    }
}
