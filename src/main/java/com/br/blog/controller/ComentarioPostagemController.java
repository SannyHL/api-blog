package com.br.blog.controller;


import com.br.blog.model.dto.ComentarioPostagemDTO;
import com.br.blog.service.ComentarioPostagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/postagens/comentarios")
public class ComentarioPostagemController {

    @Autowired
    ComentarioPostagemService comentarioPostagemService;

    @PostMapping
    public ResponseEntity<Integer> salvar(@RequestBody ComentarioPostagemDTO comentarioPostagemDTO){
        return ResponseEntity.ok(comentarioPostagemService.salvar(comentarioPostagemDTO));
    }

    @GetMapping("ativos/{idPostagem}")
    public  ResponseEntity<List<ComentarioPostagemDTO>> buscarTodosComentariosAtivos(@PathVariable("idPostagem") Integer idPostagem){
        return ResponseEntity.ok(comentarioPostagemService.buscarTodosComentariosAtivosPorPostagem(idPostagem));

    }
    @GetMapping("/{id}")
    public  ResponseEntity<ComentarioPostagemDTO> buscarPorId(@PathVariable("id") Integer id){
        return ResponseEntity.ok(comentarioPostagemService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") Integer id){
        comentarioPostagemService.deletar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public  ResponseEntity<Integer> atualizar(@RequestBody ComentarioPostagemDTO comentarioPostagemDTO){
        return ResponseEntity.ok(comentarioPostagemService.atualizar(comentarioPostagemDTO));
    }
}
