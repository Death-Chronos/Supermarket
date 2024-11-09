package jv.supermarket.controllers;

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

import jv.supermarket.entities.Produto;
import jv.supermarket.exceptions.ResourceNotFoundException;
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

    @GetMapping("/all")
    public ResponseEntity<List<Produto>> getAllProdutos() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.getAllProdutos());
    }

    @GetMapping("/by-nome")
    public ResponseEntity<List<Produto>> getProdutosByNome(@RequestParam String nome) {
        List<Produto> produtos = produtoService.getProdutosByNome(nome);
        if (produtos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum produto foi encontrado com o Nome: "+ nome);
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @GetMapping("/by-marca")
    public ResponseEntity<List<Produto>> getProdutosByMarca(@RequestParam String marca) {
        List<Produto> produtos = produtoService.getProdutosByMarca(marca);
        if (produtos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum produto foi encontrado com a Marca: " + marca);
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @GetMapping("/by-marca-and-nome")
    public ResponseEntity<List<Produto>> getProdutosByMarcaAndNome(@RequestParam String marca, @RequestParam String nome) {
        List<Produto> produtos = produtoService.getProdutosByMarcaAndNome(marca, nome);
        if (produtos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum produto foi encontrado com a Marca: " + marca + " e o Nome: "+ nome);
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }
    @GetMapping("/by-categoria-nome")
    public ResponseEntity<List<Produto>> getProdutosByCategoriaNome(@RequestParam String nome) {
        List<Produto> produtos = produtoService.getProdutosByCategoriaNome(nome);
        if (produtos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum produto foi encontrado com a Categoria: " + nome);
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
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
