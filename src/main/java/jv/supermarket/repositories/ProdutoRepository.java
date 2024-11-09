package jv.supermarket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jv.supermarket.entities.Produto;

public interface ProdutoRepository  extends JpaRepository<Produto, Long>{
    Boolean existsByNomeAndMarca(String nome, String marca);

    List<Produto> findProdutoByNome(String nome);

    List<Produto> findProdutoByMarca(String marca);

    List<Produto> findProdutoByMarcaAndNome(String marca, String nome);
}
