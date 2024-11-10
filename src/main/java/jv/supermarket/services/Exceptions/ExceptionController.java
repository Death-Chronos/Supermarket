package jv.supermarket.services.Exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jv.supermarket.exceptions.AlreadyExistException;
import jv.supermarket.exceptions.ImageSavingException;
import jv.supermarket.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionController {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Mensagem> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        Mensagem mensagem = new Mensagem(Instant.now(), status.value(), e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(mensagem);

    }
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Mensagem> alredyExist(AlreadyExistException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        Mensagem mensagem = new Mensagem(Instant.now(), status.value(), e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(mensagem);
    }@ExceptionHandler(ImageSavingException.class)
    public ResponseEntity<Mensagem> salvamentoDeImagem(ImageSavingException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Mensagem mensagem = new Mensagem(Instant.now(), status.value(), e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(mensagem);
    }
}
