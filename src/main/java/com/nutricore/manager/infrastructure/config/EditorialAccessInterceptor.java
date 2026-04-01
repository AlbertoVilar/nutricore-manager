package com.nutricore.manager.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutricore.manager.domain.exceptions.error.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.OffsetDateTime;

@Component
public class EditorialAccessInterceptor implements HandlerInterceptor {

    public static final String HEADER_NAME = "X-Editorial-Token";

    private final ObjectMapper objectMapper;
    private final String adminToken;

    public EditorialAccessInterceptor(
            ObjectMapper objectMapper,
            @Value("${app.editorial.admin-token:}") String adminToken
    ) {
        this.objectMapper = objectMapper;
        this.adminToken = adminToken;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        if (adminToken == null || adminToken.isBlank()) {
            writeError(response, request, HttpStatus.SERVICE_UNAVAILABLE,
                    "Protecao editorial provisoria nao configurada para este ambiente.");
            return false;
        }

        String requestToken = request.getHeader(HEADER_NAME);
        if (requestToken == null || !adminToken.equals(requestToken)) {
            writeError(response, request, HttpStatus.UNAUTHORIZED,
                    "Informe um token editorial valido. Esta protecao e temporaria e sera substituida por autenticacao real.");
            return false;
        }

        return true;
    }

    private void writeError(
            HttpServletResponse response,
            HttpServletRequest request,
            HttpStatus status,
            String message
    ) throws Exception {
        StandardError error = new StandardError();
        error.setTimestamp(OffsetDateTime.now().toString());
        error.setStatus(status.value());
        error.setError(status.getReasonPhrase());
        error.setMessage(message);
        error.setPath(request.getRequestURI());

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
