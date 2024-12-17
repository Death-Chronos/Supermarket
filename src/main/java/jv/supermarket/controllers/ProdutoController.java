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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jv.supermarket.DTOs.ProdutoDTO;
import jv.supermarket.DTOs.request.ProdutoRequestDTO;
import jv.supermarket.config.RespostaAPI;
import jv.supermarket.entities.Produto;
import jv.supermarket.exceptions.ResourceNotFoundException;
import jv.supermarket.services.ProdutoService;
import jv.supermarket.services.Exceptions.Mensagem;

@RestController
@RequestMapping("/supermarket/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Salva um novo produto", 
        description = "Recebe uma DTO de produto, que possui em conjunto a(s) categoria(s) em que ele fará parte ( a categoria já deve existir no sistema anteriormente), lembrando que, um produto com o mesmo nome e marca não pode existir")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", 
                description = "Produto com mesmo com o mesmo nome e marca já existe", 
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Mensagem.class))),

            @ApiResponse(responseCode = "404", 
                description = "Não existe uma categoria com os nomes informados", 
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Mensagem.class))),

            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Produto.class)))
    })
    @PostMapping("/save")
    public ResponseEntity<Produto> saveProduto(@RequestBody @Valid ProdutoRequestDTO produto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.saveProduto(produto));

    }

    @Operation(summary = "Busca um produto pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "404", description = "Nenhum produto foi encontrado com o id requisitado", 
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Mensagem.class))),
        
        @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso", 
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Produto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@Parameter(description = "Id do produto a ser buscado. Exemplo: 1") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.getProdutoById(id));
    }

    @Operation(summary = "Busca todos os produtos existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", 
                description = "Produtos encontrados com sucesso",
                content = @Content(mediaType = "application/json", 
                    array = @ArraySchema(schema = @Schema(implementation = Produto.class))))
    })
    @GetMapping("/all")
    public ResponseEntity<List<Produto>> getAllProdutos() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.getAllProdutos());
    }

    @GetMapping("/by-nome")
    public ResponseEntity<List<Produto>> getProdutosByNome(@RequestParam String nome) {
        List<Produto> produtos = produtoService.getProdutosByNome(nome);
        if (produtos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum produto foi encontrado com o Nome: " + nome);
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
    public ResponseEntity<List<Produto>> getProdutosByMarcaAndNome(@RequestParam String marca,
            @RequestParam String nome) {
        List<Produto> produtos = produtoService.getProdutosByMarcaAndNome(marca, nome);
        if (produtos.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Nenhum produto foi encontrado com a Marca: " + marca + " e o Nome: " + nome);
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @GetMapping("/by-categoria-nome")
    public ResponseEntity<List<ProdutoDTO>> getProdutosByCategoriaNome(@RequestParam String nome) {
        List<ProdutoDTO> produtos = produtoService.getProdutosByCategoriaNome(nome);
        if (produtos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum produto foi encontrado com a Categoria: " + nome);
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody @Valid Produto produto) {

        return ResponseEntity.status(HttpStatus.OK).body(produtoService.updateProduto(produto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespostaAPI> deleteProduto(@PathVariable Long id) {
        produtoService.deleteProdutoById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new RespostaAPI(Instant.now(), "Produto deletado com sucesso"));
    }

    @PutMapping("/{id}/addEstoque")
    public ResponseEntity<Produto> aumentarEstoque(@PathVariable Long id, @RequestParam int quantidade) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.addProdutoEstoque(quantidade, id));
    }

}
