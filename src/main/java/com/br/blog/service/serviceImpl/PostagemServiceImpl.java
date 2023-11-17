package com.br.blog.service.serviceImpl;

import com.br.blog.model.Postagem;
import com.br.blog.model.dto.ImagensPostagemDTO;
import com.br.blog.model.dto.PostagemDTO;
import com.br.blog.repository.ImagensPostagemRepository;
import com.br.blog.repository.PostagemRepository;
import com.br.blog.security.DecodificadorToken;
import com.br.blog.service.ImagensPostagemService;
import com.br.blog.service.PostagemService;
import io.jsonwebtoken.Claims;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class PostagemServiceImpl implements PostagemService {

    public static final String CAMINHO_DA_PASTA;

    static {
        CAMINHO_DA_PASTA = System.getProperty("user.home") + "/blog/life/imagens";
    }
    public static final String CAMINHO_DA_PASTA_SAIDA;

    static {
        CAMINHO_DA_PASTA_SAIDA = System.getProperty("user.home") + "/blog/life/imagens/";
    }
    @Autowired
    PostagemRepository postagemRepository;
    @Autowired
    ImagensPostagemServiceImpl imagensPostagemService;

    @Autowired
    private DecodificadorToken decodificadorToken;

    @Override
    public Integer salvar(PostagemDTO postagemDTO) {
        try {
            Integer idUsuario = new Integer(decodificadorToken.obterIdDoUsuarioAtual());
            postagemDTO.setAtivo(true);
            postagemDTO.setDataCriacao(new Date());
            postagemDTO.setUsuarioId(idUsuario);
            Postagem postagem = new Postagem(postagemDTO);
            postagemRepository.saveAndFlush(postagem);
            String extensao = verificarExtensaoImagem(postagemDTO.getImagem());
            if(postagem.getId() != null && postagemDTO.getImagem() != null){
                salvarImagemNoBanco(new ImagensPostagemDTO(extensao, postagem.getId()), postagem.getId(), postagemDTO.getImagem());
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
            if(postagemDTO.getImagem() != null){
               salvarImagemNoBanco(new ImagensPostagemDTO("jpg", postagemDTO.getId()), postagemDTO.getId(), postagemDTO.getImagem());
            }
            return postagem.getId();
        } catch (ResponseStatusException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao editar postagem.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PostagemDTO> buscarTodasAtivas() {
        try{
            List<PostagemDTO> postagemDTOS = postagemRepository.buscarTodasPostagensAtivas();

            for(PostagemDTO postagemDTO : postagemDTOS){
                ImagensPostagemDTO imagensPostagemDTO = new ImagensPostagemDTO();
                imagensPostagemDTO = imagensPostagemService.buscarPorPostagemId(postagemDTO.getId());
                if(imagensPostagemDTO != null){
                    postagemDTO.setImagem(imagensPostagemDTO.getId().toString().concat(".").concat(imagensPostagemDTO.getExtensao()));
                }
            }

            return postagemDTOS;
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

    public String decodificarImagem(String imagem){
        byte[] decodedBytes = Base64.getDecoder().decode(imagem);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }

    public String verificarExtensaoImagem(String imagem) {
        String stringFormatada = imagem.replace("data:image/", "");
        int indexVirgula = stringFormatada.indexOf(',');

        if (indexVirgula != -1) {
            stringFormatada = stringFormatada.substring(0, indexVirgula);
        } else {
            int indexPontoVirgula = stringFormatada.indexOf(';');

            if (indexPontoVirgula != -1) {
                stringFormatada = stringFormatada.substring(0, indexPontoVirgula);
            }
        }
        stringFormatada = stringFormatada.replace(";base64", "");

        return stringFormatada;
    }

    public String salvarImagen(String base64Image, String fileName) throws IOException {
        criarPastaSeNaoExistir();
        String base64ImageWithoutPrefix = base64Image.replaceFirst("data:image/.*?;base64,", "");
        byte[] imageBytes = Base64.getDecoder().decode(base64ImageWithoutPrefix);
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
