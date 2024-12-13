package jv.supermarket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jv.supermarket.DTOs.auth.AuthResponseDTO;
import jv.supermarket.entities.Usuario;
import jv.supermarket.security.JWT.TokenService;
import jv.supermarket.services.UsuarioService;

@RestController
@RequestMapping("/supermarket/admin")
public class AdminController {

    @Autowired
    UsuarioService userService;

    @Autowired
    TokenService tokenService;
    
    @PostMapping("/criarFuncionario")
    public ResponseEntity<AuthResponseDTO> saveUsuario(@RequestBody @Valid Usuario user){
        Usuario usuario = userService.saveFuncionario(user);
        String token = tokenService.gerarToken(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponseDTO(usuario.getNome(),token));
    }
}
