package jv.supermarket.services.Exceptions;

import java.time.Instant;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jv.supermarket.exceptions.AlreadyExistException;
import jv.supermarket.exceptions.ImageSavingException;
import jv.supermarket.exceptions.OutOfStockException;
import jv.supermarket.exceptions.ResourceNotFoundException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Mensagem> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String erro = "Recurso não encontrado";
        ArrayList<String> detalhes = new ArrayList<String>();
        detalhes.add(e.getMessage());
        Mensagem mensagem = new Mensagem(Instant.now(), status.value(), erro, request.getRequestURI(), detalhes);
        return ResponseEntity.status(status).body(mensagem);

    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Mensagem> alredyExist(AlreadyExistException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        String erro = "Recurso já existe";
        ArrayList<String> detalhes = new ArrayList<String>();
        detalhes.add(e.getMessage());
        Mensagem mensagem = new Mensagem(Instant.now(), status.value(), erro, request.getRequestURI(), detalhes);
        return ResponseEntity.status(status).body(mensagem);

    }

    @ExceptionHandler(ImageSavingException.class)
    public ResponseEntity<Mensagem> salvamentoDeImagem(ImageSavingException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String erro = "Problemas com imagens";
        ArrayList<String> detalhes = new ArrayList<String>();
        detalhes.add(e.getMessage());
        Mensagem mensagem = new Mensagem(Instant.now(), status.value(), erro,request.getRequestURI(), detalhes);
        return ResponseEntity.status(status).body(mensagem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Mensagem> argumentoInvalido(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String erro = "Problema ao validar valores: ";

        ArrayList<String> detalhes = new ArrayList<String>();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            detalhes.add(fieldError.getDefaultMessage());
        }

        Mensagem mensagem = new Mensagem(Instant.now(), status.value(), erro,request.getRequestURI(), detalhes);
        return ResponseEntity.status(status).body(mensagem);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<Mensagem> estoqueInsuficiente(OutOfStockException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String erro = "Estoque insuficiente";
        ArrayList<String> detalhes = new ArrayList<String>();
        detalhes.add(e.getMessage());
        Mensagem mensagem = new Mensagem(Instant.now(), status.value(), erro, request.getRequestURI(), detalhes);
        return ResponseEntity.status(status).body(mensagem);
    }
}
