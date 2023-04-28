package com.sisweb.api.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ResourcesExceptionsHandler {

    @ExceptionHandler(UncategorizedSQLException.class)
    public ResponseEntity<MensagemPadrao> uncategorizedSQLException(UncategorizedSQLException e, HttpServletRequest request){

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        MensagemPadrao obj = new MensagemPadrao();
        obj.setIdStatus(httpStatus.value());
        obj.setCausa(httpStatus.toString());
        obj.setMensagem(e.getMessage());
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        e.printStackTrace();

        return  ResponseEntity.status(httpStatus).body(obj);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MensagemPadrao> dataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request){

        HttpStatus httpStatus = HttpStatus.CONFLICT;

        MensagemPadrao obj = new MensagemPadrao();
        obj.setIdStatus(httpStatus.value());
        obj.setCausa(httpStatus.toString());
        obj.setMensagem("Não foi possível executar a instrução");
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        e.printStackTrace();

        return  ResponseEntity.status(httpStatus).body(obj);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MensagemPadrao> illegalArgumentException(IllegalArgumentException e, HttpServletRequest request){

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        MensagemPadrao obj = new MensagemPadrao();
        obj.setIdStatus(httpStatus.value());
        obj.setCausa(httpStatus.toString());
        obj.setMensagem(e.getMessage());
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        e.printStackTrace();

        return  ResponseEntity.status(httpStatus).body(obj);
    }

    @ExceptionHandler(SQLGrammarException.class)
    public ResponseEntity<MensagemPadrao> SQLGrammarException(SQLGrammarException e, HttpServletRequest request){

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        MensagemPadrao obj = new MensagemPadrao();
        obj.setIdStatus(httpStatus.value());
        obj.setCausa(httpStatus.toString());
        obj.setMensagem(e.getMessage());
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        e.printStackTrace();

        return  ResponseEntity.status(httpStatus).body(obj);
    }

    @ExceptionHandler(BeanCreationException.class)
    public ResponseEntity<MensagemPadrao> beanCreationException(BeanCreationException e, HttpServletRequest request){

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        MensagemPadrao obj = new MensagemPadrao();
        obj.setIdStatus(httpStatus.value());
        obj.setCausa(httpStatus.toString());
        obj.setMensagem(e.getMessage());
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        e.printStackTrace();

        return  ResponseEntity.status(httpStatus).body(obj);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrosLista> validation(MethodArgumentNotValidException e, HttpServletRequest request){

        ErrosLista obj = new ErrosLista();

        obj.setIdStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        obj.setCausa(HttpStatus.UNPROCESSABLE_ENTITY.toString());
        obj.setMensagem("Erro de validação");
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        for(FieldError x : e.getBindingResult().getFieldErrors()){
            obj.getErros().add(new ErrosCampos(x.getField(), x.getDefaultMessage()));
        }
        e.printStackTrace();
        return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(obj);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<MensagemPadrao> constrainViolation(ConstraintViolationException e, HttpServletRequest request){

        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;


        ErrosLista obj = new ErrosLista();
        obj.setIdStatus(httpStatus.value());
        obj.setCausa(httpStatus.toString());
        obj.setMensagem("Erro de validação.");
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        for(ConstraintViolation error : e.getConstraintViolations()){
            obj.getErros().add(new ErrosCampos(error.getPropertyPath().toString(), error.getMessage() ));
        }

        e.printStackTrace();

        return  ResponseEntity.status(httpStatus).body(obj);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MensagemPadrao> httpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request){

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        MensagemPadrao obj = new MensagemPadrao();
        obj.setIdStatus(httpStatus.value());
        obj.setCausa(httpStatus.toString());
        obj.setMensagem(e.getMessage());
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        e.printStackTrace();

        return  ResponseEntity.status(httpStatus).body(obj);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<MensagemPadrao> noSuchElementException(NoSuchElementException e, HttpServletRequest request){

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        MensagemPadrao obj = new MensagemPadrao();
        obj.setIdStatus(httpStatus.value());
        obj.setCausa(httpStatus.toString());
        obj.setMensagem(e.getMessage());
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        e.printStackTrace();

        return  ResponseEntity.status(httpStatus).body(obj);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<MensagemPadrao> nullPointerException(NullPointerException e, HttpServletRequest request){

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        MensagemPadrao obj = new MensagemPadrao();
        obj.setIdStatus(httpStatus.value());
        obj.setCausa(httpStatus.toString());
        obj.setMensagem(e.getMessage());
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        e.printStackTrace();

        return  ResponseEntity.status(httpStatus).body(obj);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MensagemPadrao> runtimeException(RuntimeException e, HttpServletRequest request){

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        MensagemPadrao obj = new MensagemPadrao();
        obj.setIdStatus(httpStatus.value());
        obj.setCausa(httpStatus.toString());
        obj.setMensagem(e.getMessage());
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        e.printStackTrace();

        return  ResponseEntity.status(httpStatus).body(obj);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<MensagemPadrao> jwtVerificationException(JWTVerificationException e, HttpServletRequest request){

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        MensagemPadrao obj = new MensagemPadrao();
        obj.setIdStatus(httpStatus.value());
        obj.setCausa(httpStatus.toString());
        obj.setMensagem(e.getMessage());
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        e.printStackTrace();

        return  ResponseEntity.status(httpStatus).body(obj);
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<MensagemPadrao> connectException(ConnectException e, HttpServletRequest request){

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        MensagemPadrao obj = new MensagemPadrao();
        obj.setIdStatus(httpStatus.value());
        obj.setCausa(httpStatus.toString());
        obj.setMensagem(e.getMessage());
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        e.printStackTrace();

        return  ResponseEntity.status(httpStatus).body(obj);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<MensagemPadrao> illegalStateException(IllegalStateException e, HttpServletRequest request){

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        MensagemPadrao obj = new MensagemPadrao();
        obj.setIdStatus(httpStatus.value());
        obj.setCausa(httpStatus.toString());
        obj.setMensagem(e.getMessage());
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        e.printStackTrace();

        return  ResponseEntity.status(httpStatus).body(obj);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MensagemPadrao> httpMessageNotReadableException(BadCredentialsException e, HttpServletRequest request){

        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        MensagemPadrao obj = new MensagemPadrao();
        obj.setIdStatus(httpStatus.value());
        obj.setCausa(httpStatus.toString());
        obj.setMensagem(e.getMessage());
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        e.printStackTrace();

        return  ResponseEntity.status(httpStatus).body(obj);
    }
}
