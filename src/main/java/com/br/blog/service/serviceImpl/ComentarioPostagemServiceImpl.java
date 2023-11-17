package com.br.blog.service.serviceImpl;

import com.br.blog.model.ComentarioPostagem;
import com.br.blog.model.ImagensPostagem;
import com.br.blog.model.dto.ComentarioPostagemDTO;
import com.br.blog.repository.ComentarioPostagemRepository;
import com.br.blog.security.DecodificadorToken;
import com.br.blog.service.ComentarioPostagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class ComentarioPostagemServiceImpl implements ComentarioPostagemService {

    @Autowired
    ComentarioPostagemRepository comentarioPostagemRepository;
    @Autowired
    private DecodificadorToken decodificadorToken;

    @Override
    public Integer salvar(ComentarioPostagemDTO comentarioPostagemDTO) {
        comentarioPostagemDTO.setDataCriacao(new Date());
        comentarioPostagemDTO.setUsuarioId(new Integer(decodificadorToken.obterIdDoUsuarioAtual()));
        comentarioPostagemDTO.setAtivo(true);
        ComentarioPostagem comentarioPostagem = new ComentarioPostagem(comentarioPostagemDTO);
        comentarioPostagemRepository.saveAndFlush(comentarioPostagem);
        return comentarioPostagem.getId();
    }

    @Override
    public Integer atualizar(ComentarioPostagemDTO comentarioPostagemDTO) {
        try {
            ComentarioPostagemDTO comentarioPostagemNoBanco = buscarPorId(comentarioPostagemDTO.getId());
            comentarioPostagemDTO.setAtivo(comentarioPostagemNoBanco.getAtivo());
            comentarioPostagemDTO.setUsuarioId(comentarioPostagemNoBanco.getUsuarioId());
            comentarioPostagemDTO.setDataCriacao(comentarioPostagemNoBanco.getDataCriacao());
            comentarioPostagemDTO.setDataAlteracao(new Date());
            ComentarioPostagem comentarioPostagem = new ComentarioPostagem(comentarioPostagemDTO);
            comentarioPostagemRepository.saveAndFlush(comentarioPostagem);
            return comentarioPostagem.getId();
        } catch (ResponseStatusException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao editar comentario.");
        }
    }

    @Override
    public List<ComentarioPostagemDTO> buscarTodosComentariosAtivosPorPostagem(Integer idPostagem) {
        try{
            return comentarioPostagemRepository.buscarTodosComentariosAtivosPorPostagem(idPostagem);
        } catch (ResponseStatusException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar comentários.");
        }
    }

    @Override
    public ComentarioPostagemDTO buscarPorId(Integer comentarioId) {
        try{
            return comentarioPostagemRepository.buscarPorId(comentarioId);
        } catch (ResponseStatusException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar comentário.");
        }
    }

    @Override
    public void deletar(Integer comentarioId) {
        try{
            ComentarioPostagemDTO comentarioDTOBanco = buscarPorId(comentarioId);
            comentarioDTOBanco.setAtivo(false);
            ComentarioPostagem comentarioPostagem = new ComentarioPostagem(comentarioDTOBanco);
            comentarioPostagemRepository.saveAndFlush(comentarioPostagem);
        } catch (ResponseStatusException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar comentário.");
        }
    }
}
