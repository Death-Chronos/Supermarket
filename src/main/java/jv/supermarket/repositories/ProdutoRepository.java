package jv.supermarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import jv.supermarket.entities.Produto;

public interface ProdutoRepository  extends JpaRepository<Produto, Long>{
    Boolean existsByNomeAndMarca(String nome, String marca);
}
