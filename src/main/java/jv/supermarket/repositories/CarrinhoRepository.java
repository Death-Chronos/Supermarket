package jv.supermarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import jv.supermarket.entities.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    
}
