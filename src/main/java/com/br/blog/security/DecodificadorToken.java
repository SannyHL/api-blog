package com.br.blog.security;

import com.br.blog.utils.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DecodificadorToken {

    public String obterIdDoUsuarioAtual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String idUsuario = authentication.getName();
            return idUsuario;
        } else {
            return null;
        }
    }


}
