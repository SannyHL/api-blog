package com.br.blog.security;

import com.br.blog.model.Usuario;
import com.br.blog.model.dto.UsuarioLoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import io.jsonwebtoken.Jwts;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            InputStream requestBody = request.getInputStream();
            UsuarioLoginDTO usuario = new ObjectMapper().readValue(requestBody, UsuarioLoginDTO.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha());

            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();

        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("id", ((Usuario) userDetails).getId())
                .claim("nome", ((Usuario) userDetails).getNome())
                .claim("email", ((Usuario) userDetails).getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
                .signWith(SignatureAlgorithm.HS512, "ADDD51544662DSAA")
                .compact();
        response.addHeader("Authorization", "Bearer " + token);
    }
}
