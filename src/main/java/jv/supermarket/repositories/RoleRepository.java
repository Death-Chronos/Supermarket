package jv.supermarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import jv.supermarket.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
