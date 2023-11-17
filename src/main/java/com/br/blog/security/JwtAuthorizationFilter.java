package com.br.blog.security;

import com.br.blog.utils.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authentication;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authentication) {
        this.authentication = authentication;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.replace("Bearer ", "");
                try {
                    Claims claims = Jwts.parser()
                            .setSigningKey(Utils.TOKEN_SENHA.getBytes())
                            .parseClaimsJws(token)
                            .getBody();

                    String username = claims.getSubject();
                    Integer userId = claims.get("id", Integer.class);
                    String nome = claims.get("nome", String.class);
                    String email = claims.get("email", String.class);

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, null);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } catch (Exception e) {
                    logger.error("Erro ao validar Token", e);
                }

        }
        chain.doFilter(request, response);
    }

}