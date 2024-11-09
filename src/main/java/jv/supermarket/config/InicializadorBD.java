package jv.supermarket.config;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import jv.supermarket.entities.Categoria;
import jv.supermarket.entities.Produto;
import jv.supermarket.services.CategoriaService;
import jv.supermarket.services.ProdutoService;

@Configuration
@Profile("test")
public class InicializadorBD implements CommandLineRunner {

    @Autowired
    ProdutoService ps;

    @Autowired
    CategoriaService cs;

    @Override
    public void run(String... args) throws Exception {

        // Criação de Categorias
        Categoria c1 = new Categoria("Eletrônicos");
        Categoria c2 = new Categoria("Mobília");
        Categoria c3 = new Categoria("Smartphones");
        Categoria c4 = new Categoria("Cozinha");

        // Salva Categorias
        cs.saveCategoria(c4);
        cs.saveCategoria(c3);
        cs.saveCategoria(c2);
        cs.saveCategoria(c1);

        // Criação e associação de Produtos
        Produto p1 = new Produto("Smartphone", "Samsung", new BigDecimal(3000), 20, "O melhor da Samsung");
        ps.saveProduto(p1);

        p1.addCategoria(c3);
        p1.addCategoria(c1);
        ps.updateProduto(p1, p1.getId()); // Atualiza produto com categorias

        Produto p2 = new Produto("Smartphone", "Xiaomi", new BigDecimal(3200), 32, "O mundo todo no seu bolso");
        ps.saveProduto(p2);

        p2.addCategoria(c3);
        p2.addCategoria(c1);
        ps.updateProduto(p2, p2.getId()); // Atualiza produto com categorias

        Produto p3 = new Produto("Geladeira", "Samsung", new BigDecimal(4000), 10, "Gela que é uma beleza!");
        ps.saveProduto(p3);
        
        p3.addCategoria(c4);
        p3.addCategoria(c1);
        ps.updateProduto(p3, p3.getId()); // Atualiza produto com categorias

        Produto p4 = new Produto("Cama de Casal", "Plumatex", new BigDecimal(1500), 5, "O que há de conforto para você");
        ps.saveProduto(p4);
        
        p4.addCategoria(c2);
        ps.updateProduto(p4, p4.getId()); // Atualiza produto com categorias
        
        
    }

}
