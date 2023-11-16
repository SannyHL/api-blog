package com.br.blog.service.serviceImpl;

import com.br.blog.model.ImagensPostagem;
import com.br.blog.model.dto.ComentarioPostagemDTO;
import com.br.blog.repository.ComentarioPostagemRepository;
import com.br.blog.service.ComentarioPostagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioPostagemServiceImpl implements ComentarioPostagemService {

    @Autowired
    ComentarioPostagemRepository comentarioPostagemRepository;

    @Override
    public Integer salvar(ComentarioPostagemDTO comentarioPostagemDTO) {
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
            return postagem.getId();
        } catch (ResponseStatusException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao editar comentario.");
        }
    }

    @Override
    public List<ComentarioPostagemDTO> buscarTodosComentariosAtivos() {
        try{
            return comentarioPostagemRepository.buscarTodosComentariosAtivos();
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
            ComentarioPostagem comentarioPostagem = new Postagem(comentarioDTOBanco);
            comentarioPostagemRepository.saveAndFlush(comentarioPostagem);
        } catch (ResponseStatusException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar comentário.");
        }
    }
}
