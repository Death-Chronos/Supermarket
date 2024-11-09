package jv.supermarket.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jv.supermarket.entities.Categoria;
import jv.supermarket.entities.Produto;
import jv.supermarket.exceptions.AlreadyExistException;
import jv.supermarket.exceptions.ResourceNotFoundException;
import jv.supermarket.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository cr;

    public Categoria saveCategoria(Categoria categoria) {
        if (cr.existsByNome(categoria.getNome())) {
            throw new AlreadyExistException("A categoria com o Nome: " + categoria.getNome() + " já existe");
        }
        return cr.save(categoria);
    }

    public Categoria getCategoriaById(Long id) {
        return cr.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("A categoria com o id: " + id + " não foi encontrada"));
    }

    public Categoria getCategoriaByNome(String nome) {
        if (cr.existsByNome(nome)) {
            return cr.findByNome(nome);

        }
        throw new ResourceNotFoundException("A categoria com o nome: " + nome + " não foi encontrada");
    }

    public List<Categoria> getAllCategorias() {
        return cr.findAll();
    }

    
    public void deleteCategoriaById(Long id) {
        if (cr.existsById(id)) {
            Categoria categoria = cr.findById(id).get();

            Hibernate.initialize(categoria.getProdutos());

            for (Produto produto : categoria.getProdutos()) {
                produto.getCategorias().remove(categoria);
            }

            categoria.getProdutos().clear();
            cr.save(categoria);
            cr.deleteById(id);
        }else{
            throw new ResourceNotFoundException("A categoria com o id: " + id + " não foi encontrada");
        }
        
    }

    public Categoria updateCategoria(Long id, Categoria categoriaAtualizada) {
        Categoria categoria = getCategoriaById(id);
        categoria.setNome(categoriaAtualizada.getNome());

        if (!(categoriaAtualizada.getProdutos() == null)) {
            categoria.getProdutos().addAll(categoriaAtualizada.getProdutos());
        }
        
        return cr.save(categoria);
    }
}
