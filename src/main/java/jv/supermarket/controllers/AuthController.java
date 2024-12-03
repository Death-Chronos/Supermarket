package jv.supermarket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jv.supermarket.DTOs.auth.AuthLoginRequestDTO;
import jv.supermarket.DTOs.auth.AuthResponseDTO;
import jv.supermarket.entities.Usuario;
import jv.supermarket.exceptions.BadAuthRequestException;
import jv.supermarket.security.JWT.TokenService;
import jv.supermarket.services.UsuarioService;

@RestController
@RequestMapping("/supermarket/auth")
public class AuthController {
    
    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthLoginRequestDTO dto) {
        Usuario user = userService.getByEmail(dto.email());

        if (passwordEncoder.matches(dto.senha(), user.getPassword())) {
            String token = tokenService.gerarToken(user);

            return ResponseEntity.status(HttpStatus.OK).body(new AuthResponseDTO(user.getNome(), token));
        }
        throw new BadAuthRequestException("Erro ao realizar login: email ou senha incorreta");

    }

    @PostMapping("/registrar")
    public ResponseEntity<AuthResponseDTO> saveUsuario(@RequestBody @Valid Usuario user){
        Usuario usuario = userService.saveUsuario(user);
        String token = tokenService.gerarToken(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponseDTO(usuario.getNome(),token));
    }
}
