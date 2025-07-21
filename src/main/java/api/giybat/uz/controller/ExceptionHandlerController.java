package api.giybat.uz.controller;

import api.giybat.uz.exps.AppBadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {


    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<String> handle(AppBadException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
