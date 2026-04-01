package com.nutricore.manager.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutricore.manager.domain.exceptions.error.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public static final String AUTH_ERROR_MESSAGE_ATTRIBUTE = "security.auth.error.message";

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws java.io.IOException {
        writeError(
                response,
                request,
                HttpStatus.UNAUTHORIZED,
                "Autenticação obrigatória",
                resolveMessage(request)
        );
    }

    private String resolveMessage(HttpServletRequest request) {
        Object customMessage = request.getAttribute(AUTH_ERROR_MESSAGE_ATTRIBUTE);
        if (customMessage instanceof String message && !message.isBlank()) {
            return message;
        }

        return "Autenticação obrigatória para acessar este recurso.";
    }

    private void writeError(
            HttpServletResponse response,
            HttpServletRequest request,
            HttpStatus status,
            String error,
            String message
    ) throws java.io.IOException {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(OffsetDateTime.now().toString());
        standardError.setStatus(status.value());
        standardError.setError(error);
        standardError.setMessage(message);
        standardError.setPath(request.getRequestURI());

        response.setStatus(status.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(standardError));
    }
}
