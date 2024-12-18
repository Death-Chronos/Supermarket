package jv.supermarket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jv.supermarket.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Boolean existsByNomeAndMarca(String nome, String marca);

    List<Produto> findByNome(String nome);

    List<Produto> findByMarca(String marca);

    Produto findByMarcaAndNome(String marca, String nome);

    @Query("SELECT p FROM Produto p JOIN FETCH p.categorias c WHERE c.nome = :nome")
    List<Produto> findByCategoriaNome(@Param("nome") String nome);

}
