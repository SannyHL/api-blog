package com.br.blog.repository;

import com.br.blog.model.ComentarioPostagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioPostagemRepository extends JpaRepository<ComentarioPostagem, Integer> {
}
