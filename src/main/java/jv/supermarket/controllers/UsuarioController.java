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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jv.supermarket.config.RespostaAPI;
import jv.supermarket.entities.Usuario;
import jv.supermarket.services.UsuarioService;

@RestController
@RequestMapping("/supermarket/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Usuario> saveUsuario(@RequestBody @Valid Usuario user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUsuario(user));
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<Usuario> updateUsuario(Usuario user, @PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUsuario(user, userId));
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<RespostaAPI> deleteUsuario(@PathVariable Long userId){
        userService.deleteUsuario(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new RespostaAPI(Instant.now(),"Usuário deletado com sucesso."));
    }
}
