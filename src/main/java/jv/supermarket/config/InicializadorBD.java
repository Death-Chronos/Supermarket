package jv.supermarket.config;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import jv.supermarket.entities.Carrinho;
import jv.supermarket.entities.Categoria;
import jv.supermarket.entities.Produto;
import jv.supermarket.entities.Usuario;
import jv.supermarket.services.CarrinhoItemService;
import jv.supermarket.services.CarrinhoService;
import jv.supermarket.services.CategoriaService;
import jv.supermarket.services.PedidoService;
import jv.supermarket.services.ProdutoService;
import jv.supermarket.services.UsuarioService;

@Configuration
@Profile("test")
public class InicializadorBD implements CommandLineRunner {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CarrinhoItemService itemService;

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private UsuarioService userService;

    @Autowired
    private PedidoService pedidoService;

    @Override
    public void run(String... args) throws Exception {

        // Criação de Categorias
        Categoria c1 = new Categoria("Eletrônicos");
        Categoria c2 = new Categoria("Mobília");
        Categoria c3 = new Categoria("Smartphones");
        Categoria c4 = new Categoria("Cozinha");

        // Salva Categorias
        categoriaService.saveCategoria(c4);
        categoriaService.saveCategoria(c3);
        categoriaService.saveCategoria(c2);
        categoriaService.saveCategoria(c1);

        // Criação e associação de Produtos
        Produto p1 = new Produto("Smartphone", "Samsung", new BigDecimal(3000), 20, "O melhor da Samsung");
        produtoService.saveProduto(p1, Arrays.asList("Eletrônicos","Smartphones"));

        Produto p2 = new Produto("Smartphone", "Xiaomi", new BigDecimal(3200), 32, "O mundo todo no seu bolso");
        produtoService.saveProduto(p2, Arrays.asList("Eletrônicos","Smartphones"));

        Produto p3 = new Produto("Geladeira", "Samsung", new BigDecimal(4000), 10, "Gela que é uma beleza!");
        produtoService.saveProduto(p3, Arrays.asList("Eletrônicos","Cozinha"));

        Produto p4 = new Produto("Cama de Casal", "Plumatex", new BigDecimal(1500), 5, "O que há de conforto para você");
        produtoService.saveProduto(p4, Arrays.asList("Mobília"));

        Usuario user = new Usuario("Adm", "adm@gmail.com", "123456");

        user = userService.saveUsuario(user);

        Carrinho carrinho = carrinhoService.getById(user.getId());
        
        itemService.adicionarItemNoCarrinho(p1.getId(), 2, carrinho.getId());        
        itemService.adicionarItemNoCarrinho(p4.getId(), 1, carrinho.getId());
        itemService.removerItemDoCarrinho(carrinho.getId(), p4.getId());
        itemService.updateItemQuantidade(carrinho.getId(), p1.getId(), 3);
        
        pedidoService.createPedido(user.getId());
        
    }

}
