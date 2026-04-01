package com.nutricore.manager.api.handlers;

import com.nutricore.manager.domain.exceptions.BusinessException;
import com.nutricore.manager.domain.exceptions.DatabaseException;
import com.nutricore.manager.domain.exceptions.InvalidCredentialsException;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.domain.exceptions.UnauthorizedException;
import com.nutricore.manager.domain.exceptions.error.FieldMessage;
import com.nutricore.manager.domain.exceptions.error.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = createStandardError(status, "Recurso nao encontrado", ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> handleDatabase(
            DatabaseException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = createStandardError(status, "Erro de integridade de banco de dados", ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError err = createStandardError(status, "Recurso invalido", "Erro de validacao nos campos", request);

        List<FieldMessage> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new FieldMessage(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        err.setErrors(errors);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> handleBusiness(
            BusinessException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = createStandardError(status, "Violacao de regra de negocio", ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<StandardError> handleInvalidCredentials(
            InvalidCredentialsException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = createStandardError(status, "Credenciais invalidas", ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<StandardError> handleUnauthorized(
            UnauthorizedException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = createStandardError(status, "Nao autenticado", ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "O corpo da requisicao possui um erro de sintaxe.";

        if (ex.getCause() instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException invalidField
                && !invalidField.getPath().isEmpty()) {
            String fieldName = invalidField.getPath().get(0).getFieldName();
            String valueSent = String.valueOf(invalidField.getValue());
            String targetType = invalidField.getTargetType().getSimpleName();

            if (invalidField.getTargetType().isEnum()) {
                message = String.format(
                        "O valor '%s' nao e aceito para o campo '%s'. Verifique os valores permitidos.",
                        valueSent,
                        fieldName
                );
            } else if (targetType.equals("LocalDate")) {
                message = String.format(
                        "A data '%s' no campo '%s' esta em formato invalido. Use o padrao ISO (AAAA-MM-DD).",
                        valueSent,
                        fieldName
                );
            } else {
                message = String.format(
                        "O valor '%s' e incompativel com o tipo esperado para o campo '%s'.",
                        valueSent,
                        fieldName
                );
            }
        }

        StandardError err = createStandardError(status, "Erro na leitura do JSON", message, request);
        return ResponseEntity.status(status).body(err);
    }

    private StandardError createStandardError(
            HttpStatus status,
            String error,
            String message,
            HttpServletRequest request
    ) {
        StandardError err = new StandardError();
        err.setTimestamp(OffsetDateTime.now().toString());
        err.setStatus(status.value());
        err.setError(error);
        err.setMessage(message);
        err.setPath(request.getRequestURI());
        return err;
    }
}
