package com.nutricore.manager.api.handlers;

import com.nutricore.manager.domain.exceptions.BusinessException;
import com.nutricore.manager.domain.exceptions.DatabaseException;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
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
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleResourceNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = createStandardError(status, "Recurso não encontrado", ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> handleDatabase(DatabaseException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = createStandardError(status, "Erro de integridade de banco de dados", ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError err = createStandardError(status, "Recursos inválidos", "Erro de validação nos campos", request);

        List<FieldMessage> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new FieldMessage(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        err.setErrors(errors);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> handleBusiness(BusinessException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = createStandardError(status, "Violação de regra de negócio", ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "O corpo da requisição possui um erro de sintaxe.";

        if (ex.getCause() instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException invalidField) {
            String fieldName = invalidField.getPath().get(0).getFieldName();
            String valueSent = invalidField.getValue().toString();
            String targetType = invalidField.getTargetType().getSimpleName();

            if (invalidField.getTargetType().isEnum()) {
                message = String.format("O valor '%s' não é aceito para o campo '%s'. Verifique os valores permitidos.",
                        valueSent, fieldName);
            } else if (targetType.equals("LocalDate")) {
                message = String.format("A data '%s' no campo '%s' está em formato inválido. Use o padrão ISO (AAAA-MM-DD).",
                        valueSent, fieldName);
            } else {
                message = String.format("O valor '%s' é incompatível com o tipo de dado esperado para o campo '%s'.",
                        valueSent, fieldName);
            }
        }

        StandardError err = createStandardError(status, "Erro na leitura do JSON", message, request);
        return ResponseEntity.status(status).body(err);
    }

    // Método auxiliar para evitar repetição de código (Boas práticas!)
    private StandardError createStandardError(HttpStatus status, String error, String message, HttpServletRequest request) {
        StandardError err = new StandardError();
        err.setTimestamp(OffsetDateTime.now().toString());
        err.setStatus(status.value());
        err.setError(error);
        err.setMessage(message);
        err.setPath(request.getRequestURI());
        return err;
    }
}