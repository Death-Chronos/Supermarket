package jv.supermarket.controllers;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jv.supermarket.config.RespostaAPI;
import jv.supermarket.entities.Categoria;
import jv.supermarket.services.CategoriaService;

@RestController
@RequestMapping("/supermarket/categoria")
public class CategoriaController {

    @Autowired
    CategoriaService cs;

    @PostMapping("/save")
    public ResponseEntity<Categoria> saveCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cs.saveCategoria(categoria));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(cs.getCategoriaById(id));
    }

    @GetMapping("/by-nome")
    public ResponseEntity<Categoria> getCategoriaByNome(@RequestParam String nome) {
        return ResponseEntity.status(HttpStatus.OK).body(cs.getCategoriaByNome(nome));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Categoria>> getAllCategorias() {
        return ResponseEntity.status(HttpStatus.OK).body(cs.getAllCategorias());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespostaAPI> deleteCategoria(@PathVariable Long id) {
        cs.deleteCategoriaById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new RespostaAPI(Instant.now(),"Categoria deletada com sucess."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoriaAtualizada) {
        return ResponseEntity.status(HttpStatus.OK).body(cs.updateCategoria(id, categoriaAtualizada));
    }

}
