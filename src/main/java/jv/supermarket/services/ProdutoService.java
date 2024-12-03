package jv.supermarket.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jv.supermarket.DTOs.ProdutoDTO;
import jv.supermarket.entities.Categoria;
import jv.supermarket.entities.Produto;
import jv.supermarket.exceptions.AlreadyExistException;
import jv.supermarket.exceptions.ResourceNotFoundException;
import jv.supermarket.repositories.CategoriaRepository;
import jv.supermarket.repositories.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository pr;

    @Autowired
    CategoriaRepository cr;

    private Boolean produtoExist(String nome, String marca) {
        return pr.existsByNomeAndMarca(nome, marca);
    }

    public List<Produto> getAllProdutos() {
        return pr.findAll();
    }

    @Transactional
    public Produto saveProduto(Produto produto, List<String> categoriaNomes) {
        if (produtoExist(produto.getNome(), produto.getMarca())) {
            throw new AlreadyExistException("Já existe um produto com este nome e marca.");
        }
        produto = addCategoriasEmProduto(categoriaNomes, produto);
        return pr.save(produto);
    }

    public Produto addCategoriasEmProduto(List<String> categoriasNomes, Produto produto) {
        for (String categoriaNome : categoriasNomes) {
            if (cr.existsByNome(categoriaNome)) {
                Categoria categoria = cr.findByNome(categoriaNome);
                produto.getCategorias().add(categoria);
            } else {
                throw new ResourceNotFoundException("A categoria com o nome: " + categoriaNome + " não foi encontrada");
            }
        }
        return produto;
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
        return pr.findByNome(nome);
    }

    public List<Produto> getProdutosByMarca(String marca) {
        return pr.findByMarca(marca);
    }

    public List<Produto> getProdutosByMarcaAndNome(String marca, String nome) {
        return pr.findByMarcaAndNome(marca, nome);
    }

    public List<ProdutoDTO> getProdutosByCategoriaNome(String nome) {
        if (cr.existsByNome(nome)) {
            List<Produto> produtos = pr.findByCategoriaNome(nome);
            if (produtos.isEmpty()) {
                throw new ResourceNotFoundException("Não existem produtos pertencentes a categoria com o nome:" + nome);
            }
            return produtos.stream().map(produto -> converterParaDTO(produto)).toList();
        }
        throw new ResourceNotFoundException("A categoria com o nome: " + nome + " não foi encontrada");

    }

    public ProdutoDTO converterParaDTO(Produto produto) {
        ProdutoDTO produtoDTO = new ProdutoDTO();

        BeanUtils.copyProperties(produto, produtoDTO);

        return produtoDTO;
    }

    public boolean existById(Long produtoId) {
        return pr.existsById(produtoId);
    }

}
