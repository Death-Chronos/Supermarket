package jv.supermarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import jv.supermarket.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    Categoria findByNome(String nome);
    Boolean existsByNome(String nome);
}
