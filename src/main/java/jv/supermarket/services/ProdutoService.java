package jv.supermarket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jv.supermarket.entities.Produto;
import jv.supermarket.exceptions.AlreadyExistException;
import jv.supermarket.exceptions.ResourceNotFoundException;
import jv.supermarket.repositories.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository pr;

    private Boolean produtoExist(String nome, String marca) {
        return pr.existsByNomeAndMarca(nome, marca);
    }
    
    public List<Produto> getAllProdutos() {
        return pr.findAll();
    }

    public Produto saveProduto(Produto produto) {
        if (produtoExist(produto.getNome(), produto.getMarca())) {
            throw new AlreadyExistException("Já existe um produto com este nome e marca.");
        }
        return pr.save(produto);
    }

    public Produto getProdutoById(Long id) {
        return pr.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com o id: " + id + " não encontrado"));
    }

    public Produto updateProduto(Produto produto, Long id) {
        if (pr.existsById(id)) {
            Produto produtoSalvo = getProdutoById(id);

            produtoSalvo.setNome(produto.getNome());
            produtoSalvo.setMarca(produto.getMarca());
            produtoSalvo.setPreco(produto.getPreco());
            produtoSalvo.setEstoque(produto.getEstoque());
            produtoSalvo.setDescricao(produto.getDescricao());

            return pr.save(produtoSalvo);
        }
        throw new ResourceNotFoundException("Produto com o id: " + id + " não encontrado");

    }

    public void deleteProdutoById(Long id) {
        if (pr.existsById(id)) {
            pr.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Produto com o id: " + id + " não encontrado");
        }

    }

    public Produto addProdutoEstoque(int quantidade, Long id) {
        if (pr.existsById(id)) {
            Produto produto = getProdutoById(id);
            produto.setEstoque(produto.getEstoque() + quantidade);
            return pr.save(produto);
        }
        throw new ResourceNotFoundException("Produto com o id: " + id + " não encontrado");

    }

    public List<Produto> getProdutosByNome(String nome) {
       return pr.findProdutoByNome(nome);
    }

    public List<Produto> getProdutosByMarca(String marca) {
        return pr.findProdutoByMarca(marca);
    }

    public List<Produto> getProdutosByMarcaAndNome(String marca, String nome){
        return pr.findProdutoByMarcaAndNome(marca, nome);
    }

}
