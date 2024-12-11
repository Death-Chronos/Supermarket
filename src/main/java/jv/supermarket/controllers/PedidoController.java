package jv.supermarket.controllers;

import java.time.Instant;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jv.supermarket.DTOs.PedidoDTO;
import jv.supermarket.config.RespostaAPI;
import jv.supermarket.entities.Pedido;
import jv.supermarket.services.PedidoService;

@RestController
@RequestMapping("/supermarket/pedido")
public class PedidoController {
    
    @Autowired
    PedidoService pedidoService;

    @PostMapping("/criar")
    public ResponseEntity<Pedido> criarPedido() {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.createPedido());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.getPedido(id));
    }

    @GetMapping("by-user")
    public ResponseEntity<Set<PedidoDTO>> getPedidoByUser() {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.getPedidosByUsuario());
    }

    @DeleteMapping("/cancelar")
    public ResponseEntity<RespostaAPI> cancelarPedido(@RequestParam Long pedidoId) {
        pedidoService.cancelarPedido(pedidoId);
        return ResponseEntity.status(HttpStatus.OK).body(new RespostaAPI(Instant.now(), "Pedido cancelado com sucesso."));
    }
}
