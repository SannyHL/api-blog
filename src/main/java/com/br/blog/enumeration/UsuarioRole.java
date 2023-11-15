package com.br.blog.enumeration;

import lombok.Getter;

@Getter
public enum UsuarioRole {

    ADIMIN("Adimin"),
    USER("User");

    private String usuarioRole;
    UsuarioRole(String usuarioRole){
        this.usuarioRole = usuarioRole;
    }

}
