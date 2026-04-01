package com.nutricore.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutricore.manager.domain.entities.UserAccount;
import com.nutricore.manager.domain.enums.security.UserRole;
import com.nutricore.manager.infrastructure.db.repositories.UserAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AuthIntegrationTest {

    private static final String API_CONTEXT = "/api";
    private static final String ADMIN_EMAIL = "albertovilar1@gmail.com";
    private static final String ADMIN_PASSWORD = "132747";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldLoginWithValidCredentials() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contextPath(API_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "albertovilar1@gmail.com",
                                  "password": "132747"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.user.email").value(ADMIN_EMAIL))
                .andExpect(jsonPath("$.user.role").value("ADMIN"));
    }

    @Test
    void shouldRejectInvalidCredentials() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contextPath(API_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "albertovilar1@gmail.com",
                                  "password": "senha-errada"
                                }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Email ou senha invalidos."));
    }

    @Test
    void shouldAllowPublicRouteWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/v1/public/posts")
                        .contextPath(API_CONTEXT))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBlockPrivateRouteWithoutToken() throws Exception {
        mockMvc.perform(get("/api/v1/admin/posts")
                        .contextPath(API_CONTEXT))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(containsString("Autenticacao obrigatoria")));
    }

    @Test
    void shouldAllowPrivateEditorialRouteWithValidToken() throws Exception {
        String accessToken = loginAndExtractToken(ADMIN_EMAIL, ADMIN_PASSWORD);

        mockMvc.perform(get("/api/v1/admin/posts")
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(accessToken)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDenyEditorRoleForAdminOnlyClinicalRoutes() throws Exception {
        createEditorUser();
        String accessToken = loginAndExtractToken("editor.local@nutricore.dev", "editor-123");

        mockMvc.perform(get("/api/v1/patients")
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(accessToken)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Voce nao possui permissao para acessar este recurso."));
    }

    @Test
    void shouldReturnAuthenticatedUserForValidSession() throws Exception {
        String accessToken = loginAndExtractToken(ADMIN_EMAIL, ADMIN_PASSWORD);

        mockMvc.perform(get("/api/v1/auth/me")
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(accessToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(ADMIN_EMAIL))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    private void createEditorUser() {
        UserAccount userAccount = UserAccount.builder()
                .fullName("Editor Local")
                .email("editor.local@nutricore.dev")
                .passwordHash(passwordEncoder.encode("editor-123"))
                .role(UserRole.EDITOR)
                .active(true)
                .build();

        userAccountRepository.save(userAccount);
    }

    private String loginAndExtractToken(String email, String password) throws Exception {
        String responseBody = mockMvc.perform(post("/api/v1/auth/login")
                        .contextPath(API_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "%s",
                                  "password": "%s"
                                }
                                """.formatted(email, password)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(responseBody).get("accessToken").asText();
    }

    private String bearerToken(String accessToken) {
        return "Bearer " + accessToken;
    }
}
