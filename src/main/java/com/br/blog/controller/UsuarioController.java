package com.br.blog.controller;

import com.br.blog.model.Usuario;
import com.br.blog.model.dto.UsuarioDTO;
import com.br.blog.service.UsuarioService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Integer> salvar(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.criarUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> buscarTodosUsuarios(){
        return ResponseEntity.ok(usuarioService.buscarTodosUsuarios());
    }
}
