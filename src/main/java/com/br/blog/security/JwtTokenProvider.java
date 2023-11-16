package com.br.blog.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.br.blog.model.Usuario;
import com.br.blog.utils.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenProvider {

    private static final long EXPIRATION_TIME = 23200000;

    public String generateToken(Usuario usuario) {

        Claims claims = Jwts.claims().setSubject(usuario.getEmail());
        return JWT.create()
                .withSubject(usuario.getEmail())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(Utils.TOKEN_SENHA.getBytes()));
    }
    public String getUsernameFromToken(String token) {
        try {
            return JWT.decode(token).getSubject();
        } catch (JWTDecodeException exception) {
            return null;
        }
    }

    public boolean verifyToken(String token) {
            JWT.require(Algorithm.HMAC256(Utils.TOKEN_SENHA.getBytes())).build().verify(token);
            return true;
    }


}