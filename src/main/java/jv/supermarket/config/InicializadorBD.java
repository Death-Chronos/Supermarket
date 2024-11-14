package jv.supermarket.config;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;

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
        ps.saveProduto(p1, Arrays.asList("Eletrônicos","Smartphones"));

        Produto p2 = new Produto("Smartphone", "Xiaomi", new BigDecimal(3200), 32, "O mundo todo no seu bolso");
        ps.saveProduto(p2, Arrays.asList("Eletrônicos","Smartphones"));

        Produto p3 = new Produto("Geladeira", "Samsung", new BigDecimal(4000), 10, "Gela que é uma beleza!");
        ps.saveProduto(p3, Arrays.asList("Eletrônicos","Cozinha"));

        Produto p4 = new Produto("Cama de Casal", "Plumatex", new BigDecimal(1500), 5, "O que há de conforto para você");
        ps.saveProduto(p4, Arrays.asList("Mobília"));
        
        
    }

}
