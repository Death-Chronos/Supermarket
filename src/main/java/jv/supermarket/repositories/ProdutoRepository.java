package jv.supermarket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jv.supermarket.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Boolean existsByNomeAndMarca(String nome, String marca);

    boolean existsByIdAndDisponivel(Long id, boolean disponivel);

    Produto findByIdAndDisponivel(Long id, boolean disponivel);

    List<Produto> findAllByDisponivel(boolean disponivel);

    List<Produto> findByNome(String nome);

    List<Produto> findByNomeAndDisponivel(String nome, boolean disponivel);

    List<Produto> findByMarca(String marca);

<<<<<<< Updated upstream
    List<Produto> findByMarcaAndNome(String marca, String nome);
=======
    List<Produto> findByMarcaAndDisponivel(String marca, boolean disponivel);

    Produto findByMarcaAndNome(String marca, String nome);
>>>>>>> Stashed changes

    Produto findByMarcaAndNomeAndDisponivel(String marca, String nome, boolean disponivel);

    @Query("SELECT p FROM Produto p JOIN FETCH p.categorias c WHERE c.nome = :nome")
    List<Produto> findByCategoriaNome(@Param("nome") String nome);

    @Query("SELECT p FROM Produto p JOIN FETCH p.categorias c WHERE c.nome = :nome AND p.disponivel = :disponivel")
    List<Produto> findByCategoriaNomeAndDisponivel(@Param("nome") String nome, @Param("disponivel") boolean disponivel);

}
