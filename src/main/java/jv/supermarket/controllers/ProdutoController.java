package jv.supermarket.controllers;

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

import jv.supermarket.entities.Produto;
import jv.supermarket.services.ProdutoService;


@RestController
@RequestMapping("/supermarket/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/save")
    public ResponseEntity<Produto> saveProduto(@RequestBody Produto produto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.saveProduto(produto));

    }

    @GetMapping("/{id}")
    public Produto getProdutoById(@PathVariable Long id) {
        return produtoService.getProdutoById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produto) {

        return ResponseEntity.status(HttpStatus.OK).body(produtoService.updateProduto(produto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduto(@PathVariable Long id) {
        produtoService.deleteProdutoById(id);

        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso");
    }
    @GetMapping("/{id}/addEstoque")
    public ResponseEntity<Produto> aumentarEstoque(@PathVariable Long id, @RequestParam int quantidade) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.addProdutoEstoque(quantidade, id));
    }
    
    

}
