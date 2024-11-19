package jv.supermarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import jv.supermarket.entities.PedidoItem;

public interface PedidoItemRepository extends JpaRepository<PedidoItem,Long> {
    
}
