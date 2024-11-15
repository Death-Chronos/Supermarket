package jv.supermarket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jv.supermarket.entities.Carrinho;
import jv.supermarket.entities.CarrinhoItem;


public interface CarrinhoItemRepository extends JpaRepository<CarrinhoItem, Long> {
    List<CarrinhoItem> findByCarrinho(Carrinho carrinho);

    void deleteAllByCarrinhoId(Long carrinhoId);
}
