package jv.supermarket.controllers;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jv.supermarket.DTOs.CarrinhoDTO;
import jv.supermarket.config.RespostaAPI;
import jv.supermarket.entities.Usuario;
import jv.supermarket.services.CarrinhoItemService;
import jv.supermarket.services.CarrinhoService;
import jv.supermarket.services.UsuarioService;

@RestController
@RequestMapping("/supermarket/carrinho")
public class CarrinhoController {

    @Autowired
    CarrinhoService carrinhoService;

    @Autowired
    CarrinhoItemService ItemService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/show")
    public ResponseEntity<CarrinhoDTO> mostrarCarrinho() {
        Usuario user = usuarioService.getUsuarioLogado();

        return ResponseEntity.status(HttpStatus.OK).body(carrinhoService.getCarrinho(user.getId()));
    }

    @PostMapping("/addItem/{itemId}")
    public ResponseEntity<RespostaAPI> adicionarItem(@PathVariable Long itemId, @RequestParam int quantidade) {
        Usuario user = usuarioService.getUsuarioLogado();
        ItemService.adicionarItemNoCarrinho(itemId, quantidade, user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new RespostaAPI(Instant.now(),
                "Item de id: " + itemId + " adicionado no carrinho com id: " + user.getId() + " com sucesso."));
    }

    @DeleteMapping("/removeItem/{itemId}")
    public ResponseEntity<RespostaAPI> removerItem(@PathVariable Long itemId) {
        Usuario user = usuarioService.getUsuarioLogado();
        ItemService.removerItemDoCarrinho(user.getId(), itemId);
        return ResponseEntity.status(HttpStatus.OK).body(new RespostaAPI(Instant.now(),
                "Item de id: " + itemId + " removido do carrinho com id: " + user.getId() + " com sucesso."));
    }

    @PutMapping("/item/{itemId}/update")
    public ResponseEntity<RespostaAPI> updateItemQuantidade(@PathVariable Long itemId, @RequestParam int quantidade) {
        Usuario user = usuarioService.getUsuarioLogado();
        System.out.println("ID do usuario: " + user.getId());
        ItemService.updateItemQuantidade(user.getId(), itemId, quantidade);
        return ResponseEntity.status(HttpStatus.OK).body(new RespostaAPI(Instant.now(),
                "Item de id: " + itemId + " modificado no carrinho com id: " + user.getId() + " com sucesso."));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<RespostaAPI> limparCarrinho() {
        Usuario user = usuarioService.getUsuarioLogado();
        carrinhoService.limparCarrinho(user.getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new RespostaAPI(Instant.now(), "Carrinho com id: " + user.getId() + " limpo com sucesso."));
    }

}
