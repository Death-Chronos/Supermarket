package jv.supermarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import jv.supermarket.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    boolean existsByEmail(String email);

    Usuario findByEmail(String email);
}
