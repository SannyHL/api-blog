package com.br.blog.controller;

import com.br.blog.model.Usuario;
import com.br.blog.model.dto.UsuarioLoginDTO;
import com.br.blog.security.DecodificadorToken;
import com.br.blog.security.JwtAuthenticationResponse;
import com.br.blog.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtToken;




    @PostMapping
    public ResponseEntity<?> authenticateUser(@RequestBody UsuarioLoginDTO usuarioLogin, HttpServletResponse response) {
        try {
            List<GrantedAuthority> authorities = new ArrayList<>();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuarioLogin.getEmail(), usuarioLogin.getSenha(), authorities));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtToken.generateToken(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        } catch (Exception e) {
            throw e;
            //return ResponseEntity.status(401).body("Falha na autenticação");
        }
    }
}
