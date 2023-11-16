package com.br.blog.service.serviceImpl;

import com.br.blog.model.Postagem;
import com.br.blog.model.dto.ImagensPostagemDTO;
import com.br.blog.model.dto.PostagemDTO;
import com.br.blog.repository.PostagemRepository;
import com.br.blog.service.PostagemService;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.List;

@Service
public class PostagemServiceImpl implements PostagemService {

    public static final String CAMINHO_DA_PASTA;

    static {
        CAMINHO_DA_PASTA = System.getProperty("user.home") + "/blog/life/imagens";
    }
    @Autowired
    PostagemRepository postagemRepository;
    @Autowired
    ImagensPostagemServiceImpl imagensPostagemService;

    @Override
    public Integer salvar(PostagemDTO postagemDTO) {
        try {
            postagemDTO.setAtivo(true);
            postagemDTO.setDataCriacao(new Date());
            Postagem postagem = new Postagem(postagemDTO);
            String imagemBase64 = "";
            String fileName = "";
            postagemRepository.saveAndFlush(postagem);
            if(postagem.getId() != null && postagemDTO.getImagem() != null){
                salvarImagemNoBanco(new ImagensPostagemDTO("jpg", postagem.getId()), postagem.getId(), postagemDTO.getImagem());
            }
            return postagem.getId();
        } catch (ResponseStatusException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar postagem.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer salvarImagemNoBanco(ImagensPostagemDTO imagensPostagemDTO, Integer idPostagem, String base64) throws IOException {
        Integer idImagem = imagensPostagemService.salvar(imagensPostagemDTO);
        if(idImagem != null){
            salvarImagen(base64, idPostagem.toString().concat(".").concat(imagensPostagemDTO.getExtensao()));
        }
        return idImagem;
    }
    @Override
    public Integer atualizar(PostagemDTO postagemDTO) {
        try {
            PostagemDTO postagemDTOBanco = buscarPorId(postagemDTO.getId());
            postagemDTO.setAtivo(postagemDTOBanco.getAtivo());
            postagemDTO.setUsuarioId(postagemDTOBanco.getUsuarioId());
            postagemDTO.setDataCriacao(postagemDTOBanco.getDataCriacao());
            postagemDTO.setDataAlteracao(new Date());
            Postagem postagem = new Postagem(postagemDTO);
            postagemRepository.saveAndFlush(postagem);

            return postagem.getId();
        } catch (ResponseStatusException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao editar postagem.");
        }
    }

    @Override
    public List<PostagemDTO> buscarTodasAtivas() {
        try{
            return postagemRepository.buscarTodasPostagensAtivas();
        } catch (ResponseStatusException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar postagens.");
        }
    }

    @Override
    public PostagemDTO buscarPorId(Integer postagemId) {
        try{
            return postagemRepository.buscarPorId(postagemId);
        } catch (ResponseStatusException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar postagem.");
        }
    }

    @Override
    public void deletar(Integer postagemId) {
        try{
            PostagemDTO postagemDTOBanco = buscarPorId(postagemId);
            postagemDTOBanco.setAtivo(false);
            Postagem postagem = new Postagem(postagemDTOBanco);
            postagemRepository.saveAndFlush(postagem);
        } catch (ResponseStatusException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar postagem.");
        }
    }

    public String salvarImagen(String base64Image, String fileName) throws IOException {
        criarPastaSeNaoExistir();
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
        Path filePath = Path.of(CAMINHO_DA_PASTA, fileName);
        Files.write(filePath, imageBytes, StandardOpenOption.CREATE);
        return fileName;
    }

    private void criarPastaSeNaoExistir() throws IOException {
        Path folderPath = Path.of(CAMINHO_DA_PASTA);

        if (Files.notExists(folderPath)) {
            Files.createDirectories(folderPath);
        }
    }

}
