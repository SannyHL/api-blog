package com.br.blog.controller;

import com.br.blog.model.Usuario;
import com.br.blog.model.dto.UsuarioLoginDTO;
import com.br.blog.security.JwtAuthenticationResponse;
import com.br.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtToken;


    @PostMapping
    public ResponseEntity<?> authenticateUser(@RequestBody UsuarioLoginDTO usuarioLogin) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuarioLogin.getEmail(), usuarioLogin.getSenha()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtToken.generateToken((Usuario) authentication.getPrincipal());

            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        } catch (Exception e) {
            throw e;
            //return ResponseEntity.status(401).body("Falha na autenticação");
        }
    }
}
