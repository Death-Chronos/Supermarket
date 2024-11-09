package jv.supermarket.config;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import jv.supermarket.entities.Produto;
import jv.supermarket.services.ProdutoService;

@Configuration
@Profile("test")
public class InicializadorBD implements CommandLineRunner {

    @Autowired
    ProdutoService ps;

    @Override
    public void run(String... args) throws Exception {
        Produto p1 = new Produto("Smartphone", "Samsung", new BigDecimal(3000), 20, "O melhor da Samsumg");
        Produto p2 = new Produto("Smartphone", "Xiaomi", new BigDecimal(3200), 32, "O mundo todo no seu bolso");
        Produto p3 = new Produto("Geladeira", "Samsung", new BigDecimal(4000), 10, "Gela que é uma beleza!");
        Produto p4 = new Produto("Cama de Casal", "Plumatex", new BigDecimal(1500), 5, "O que há de conforto para você");

        ps.saveProduto(p4);
        ps.saveProduto(p3);
        ps.saveProduto(p2);
        ps.saveProduto(p1);
    }
    
}
