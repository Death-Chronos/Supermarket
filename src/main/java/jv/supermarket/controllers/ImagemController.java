package jv.supermarket.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jv.supermarket.DTOs.ImagemDTO;
import jv.supermarket.entities.Imagem;
import jv.supermarket.services.ImagemService;

@RestController
@RequestMapping("/supermarket/imagem")
public class ImagemController {

    @Autowired
    ImagemService is;

    @PostMapping("/upload")
    public ResponseEntity<List<ImagemDTO>> uploadImagem(@RequestParam List<MultipartFile> arquivos, @RequestParam Long produtoId){
       List<ImagemDTO> imagensSalvas = is.saveImagens(produtoId, arquivos);
        return ResponseEntity.status(HttpStatus.CREATED).body(imagensSalvas);
    }

    @GetMapping("/by-produto-id/{produtoId}")
    public ResponseEntity<List<ImagemDTO>> getImagemByProduto(@PathVariable Long produtoId){
        return ResponseEntity.status(HttpStatus.OK).body(is.getImagensByProdutoId(produtoId));
    }
    
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadImagem(@PathVariable Long id) throws SQLException{
        Imagem imagem = is.getImagemById(id);
        ByteArrayResource imagemBytes = new ByteArrayResource(imagem.getImagem().getBytes(1, (int) imagem.getImagem().length())); // Forma de pegar os bytes da imagem

        //@formatter:off
        return ResponseEntity.status(HttpStatus.OK)
                            .contentType(MediaType.parseMediaType(imagem.getTipoArquivo()))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attechment; filename:\""+imagem.getNomeArquivo()+"\"")
                            .body(imagemBytes);
        //@formatter:on
    }

    @PutMapping("/{id}")
    public ResponseEntity<Imagem> updateImage(@PathVariable Long id, @RequestParam MultipartFile arquivos){
        is.updateImage(arquivos, id);
        return ResponseEntity.status(HttpStatus.OK).body(is.getImagemById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id){
        is.deleteImage(id);
        return ResponseEntity.status(HttpStatus.OK).body("Imagem deletada com sucesso");
    }

}
