package jv.supermarket.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import jv.supermarket.entities.Pedido;

public interface PedidoRepository  extends JpaRepository<Pedido, Long>{

    Set<Pedido> findByUserId(Long userId);
    
}
