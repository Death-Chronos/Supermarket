package jv.supermarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import jv.supermarket.entities.Imagem;
import java.util.List;


public interface ImagemRepository extends JpaRepository<Imagem, Long>{
    List<Imagem> findByProdutoId(Long produtoId);
}
