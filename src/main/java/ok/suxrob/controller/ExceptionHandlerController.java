package ok.suxrob.controller;

import ok.suxrob.exceptions.BadRequestException;
import ok.suxrob.exceptions.ForbiddenException;
import ok.suxrob.exceptions.ItemNotFoundException;
import ok.suxrob.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({ItemNotFoundException.class, BadRequestException.class})
    public ResponseEntity<?> handlerException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<?> handlerException(ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<?> handlerException(UnauthorizedException e) {
        System.out.println(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}
