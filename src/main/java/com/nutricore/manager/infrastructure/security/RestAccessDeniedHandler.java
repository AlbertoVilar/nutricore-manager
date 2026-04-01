package com.nutricore.manager.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutricore.manager.domain.exceptions.error.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws java.io.IOException {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(OffsetDateTime.now().toString());
        standardError.setStatus(HttpStatus.FORBIDDEN.value());
        standardError.setError("Acesso negado");
        standardError.setMessage("Você não possui permissão para acessar este recurso.");
        standardError.setPath(request.getRequestURI());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(standardError));
    }
}
