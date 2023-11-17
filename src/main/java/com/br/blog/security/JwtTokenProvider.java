package com.br.blog.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.br.blog.model.Usuario;
import com.br.blog.utils.Utils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenProvider {

    private static final long EXPIRATION_TIME = 23200000;

    public String generateToken( Authentication authResult) {

        Usuario usuario = (Usuario) authResult.getPrincipal();



        JwtBuilder jwt = Jwts.builder()
                .setSubject(usuario.getUsername())
                .setSubject(usuario.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("id", usuario.getId())
                .claim("nome", usuario.getNome())
                .claim("email", usuario.getEmail())
                .signWith(SignatureAlgorithm.HS512, Utils.TOKEN_SENHA.getBytes());

        String token = jwt.compact();

        return token;
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