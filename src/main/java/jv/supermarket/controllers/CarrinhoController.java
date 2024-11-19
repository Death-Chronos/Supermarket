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
import jv.supermarket.services.CarrinhoItemService;
import jv.supermarket.services.CarrinhoService;

@RestController
@RequestMapping("supermarket/carrinho")
public class CarrinhoController {

    @Autowired
    CarrinhoService carrinhoService;

    @Autowired
    CarrinhoItemService ItemService;
    
    @GetMapping("/{id}")
    public ResponseEntity<CarrinhoDTO> mostrarCarrinho(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(carrinhoService.getCarrinho(id));
    }

    @PostMapping("/{carrinhoId}/addItem/{itemId}")
    public ResponseEntity<RespostaAPI> adicionarItem(@PathVariable Long carrinhoId, @PathVariable Long itemId, @RequestParam int quantidade) {
        ItemService.adicionarItemNoCarrinho(itemId, quantidade, carrinhoId);
        return ResponseEntity.status(HttpStatus.OK).body(new RespostaAPI(Instant.now(),"Item de id: "+itemId +" adicionado no carrinho com id: "+carrinhoId+" com sucesso."));
    }
    
    @DeleteMapping("/{carrinhoId}/removeItem/{itemId}")
    public ResponseEntity<RespostaAPI> removerItem(@PathVariable Long carrinhoId, @PathVariable Long itemId) {
        ItemService.removerItemDoCarrinho(carrinhoId, itemId);
        return ResponseEntity.status(HttpStatus.OK).body(new RespostaAPI(Instant.now(),"Item de id: "+itemId +" removido do carrinho com id: "+carrinhoId+" com sucesso."));
    }

    @PutMapping("/{carrinhoId}/item/{itemId}/update")
    public ResponseEntity<RespostaAPI> updateItemQuantidade(@PathVariable Long carrinhoId, @PathVariable Long itemId, @RequestParam int quantidade) {
        ItemService.updateItemQuantidade(itemId, carrinhoId, quantidade);
        return ResponseEntity.status(HttpStatus.OK).body(new RespostaAPI(Instant.now(),"Item de id: "+itemId +" modificado no carrinho com id: "+carrinhoId+" com sucesso."));
    }

    @DeleteMapping("/{carrinhoId}/clear")
    public ResponseEntity<RespostaAPI> limparCarrinho(@PathVariable Long carrinhoId) {
        carrinhoService.limparCarrinho(carrinhoId);
        return ResponseEntity.status(HttpStatus.OK).body(new RespostaAPI(Instant.now(),"Carrinho com id: "+carrinhoId +" limpo com sucesso."));
    }


}
