package com.nutricore.manager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AdminPublicConfigurationIntegrationTest {

    private static final String API_CONTEXT = "/api";
    private static final String ADMIN_EMAIL = "albertovilar1@gmail.com";
    private static final String ADMIN_PASSWORD = "132747";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReadAndUpdatePublicProfileIncludingImageReference() throws Exception {
        String accessToken = loginAndExtractToken(ADMIN_EMAIL, ADMIN_PASSWORD);

        String profileJson = mockMvc.perform(get("/api/v1/admin/public-profile")
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(accessToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "profile-hero.png",
                MediaType.IMAGE_PNG_VALUE,
                new byte[]{9, 8, 7, 6}
        );

        String uploadJson = mockMvc.perform(multipart("/api/v1/admin/media/images")
                        .file(file)
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(accessToken)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String uploadedImageUrl = objectMapper.readTree(uploadJson).get("url").asText();
        JsonNode requestNode = objectMapper.readTree(profileJson).deepCopy();

        ((com.fasterxml.jackson.databind.node.ObjectNode) requestNode).put("professionalSubtitle", "Nutrição clínica, treino e estratégia de rotina");
        ((com.fasterxml.jackson.databind.node.ObjectNode) requestNode).put("heroTitle", "Nutrição com estratégia para rotina, treino e performance");
        ((com.fasterxml.jackson.databind.node.ObjectNode) requestNode).put("heroImageUrl", uploadedImageUrl);
        ((com.fasterxml.jackson.databind.node.ObjectNode) requestNode).put("aboutImageUrl", uploadedImageUrl);
        ((com.fasterxml.jackson.databind.node.ObjectNode) requestNode).put("contactPhone", "+55 11 98888-7766");
        ((com.fasterxml.jackson.databind.node.ObjectNode) requestNode).put("whatsappNumber", "5511988887766");
        ((com.fasterxml.jackson.databind.node.ObjectNode) requestNode).put("officeAddress", "Rua de teste, 10 - Centro - Cuite, PB");
        ((com.fasterxml.jackson.databind.node.ObjectNode) requestNode).remove("createdAt");
        ((com.fasterxml.jackson.databind.node.ObjectNode) requestNode).remove("updatedAt");

        mockMvc.perform(put("/api/v1/admin/public-profile")
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(accessToken))
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestNode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.heroImageUrl").value(uploadedImageUrl))
                .andExpect(jsonPath("$.contactPhone").value("+55 11 98888-7766"))
                .andExpect(jsonPath("$.officeAddress").value("Rua de teste, 10 - Centro - Cuite, PB"))
                .andExpect(jsonPath("$.professionalSubtitle").value("Nutrição clínica, treino e estratégia de rotina"));

        mockMvc.perform(get("/api/v1/public/profile")
                        .contextPath(API_CONTEXT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.heroImageUrl").value(uploadedImageUrl))
                .andExpect(jsonPath("$.contactPhone").value("+55 11 98888-7766"))
                .andExpect(jsonPath("$.officeAddress").value("Rua de teste, 10 - Centro - Cuite, PB"))
                .andExpect(jsonPath("$.professionalSubtitle").value("Nutrição clínica, treino e estratégia de rotina"));
    }

    @Test
    void shouldCreateUpdateAndHideInactivePublicPlanFromPublicApi() throws Exception {
        String accessToken = loginAndExtractToken(ADMIN_EMAIL, ADMIN_PASSWORD);

        String createResponse = mockMvc.perform(post("/api/v1/admin/public-plans")
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(accessToken))
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Plano Performance",
                                  "subtitle": "Para quem busca estrutura com acompanhamento próximo",
                                  "priceLabel": "R$ 420 / mês",
                                  "summary": "Acompanhamento com revisão estratégica e ajustes para rotina de treino.",
                                  "features": ["Consulta inicial", "Revisão quinzenal", "Canal direto"],
                                  "featured": true,
                                  "active": true,
                                  "ctaLabel": "Quero este plano",
                                  "ctaUrl": "https://wa.me/5511999999999?text=Quero%20o%20Plano%20Performance",
                                  "displayOrder": 9
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Plano Performance"))
                .andExpect(jsonPath("$.active").value(true))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long planId = objectMapper.readTree(createResponse).get("id").asLong();

        mockMvc.perform(get("/api/v1/public/plans")
                        .contextPath(API_CONTEXT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id == %s)].title".formatted(planId)).value(org.hamcrest.Matchers.hasItem("Plano Performance")));

        mockMvc.perform(put("/api/v1/admin/public-plans/{id}", planId)
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(accessToken))
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Plano Performance Pro",
                                  "subtitle": "Acompanhamento premium para rotina e performance",
                                  "priceLabel": "R$ 490 / mês",
                                  "summary": "Plano reformulado com revisão semanal e mais proximidade.",
                                  "features": ["Consulta inicial", "Revisão semanal", "Canal direto", "Análise de rotina"],
                                  "featured": true,
                                  "active": true,
                                  "ctaLabel": "Falar sobre o plano",
                                  "ctaUrl": "https://wa.me/5511999999999?text=Quero%20o%20Plano%20Performance%20Pro",
                                  "displayOrder": 4
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Plano Performance Pro"))
                .andExpect(jsonPath("$.displayOrder").value(4));

        mockMvc.perform(patch("/api/v1/admin/public-plans/{id}/deactivate", planId)
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(accessToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(false));

        mockMvc.perform(get("/api/v1/public/plans")
                        .contextPath(API_CONTEXT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id == %s)]".formatted(planId)).isEmpty());

        mockMvc.perform(patch("/api/v1/admin/public-plans/{id}/activate", planId)
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(accessToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(true));

        mockMvc.perform(get("/api/v1/public/plans")
                        .contextPath(API_CONTEXT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id == %s)].title".formatted(planId)).value(org.hamcrest.Matchers.hasItem("Plano Performance Pro")));
    }

    @Test
    void shouldCreateResetPasswordDeactivateAndBlockEditorialUser() throws Exception {
        String adminToken = loginAndExtractToken(ADMIN_EMAIL, ADMIN_PASSWORD);

        String createResponse = mockMvc.perform(post("/api/v1/admin/users")
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(adminToken))
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "fullName": "Editor de Teste",
                                  "email": "editor.admin@nutricore.dev",
                                  "password": "editor123",
                                  "role": "EDITOR",
                                  "active": true
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("editor.admin@nutricore.dev"))
                .andExpect(jsonPath("$.role").value("EDITOR"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long userId = objectMapper.readTree(createResponse).get("id").asLong();
        String editorToken = loginAndExtractToken("editor.admin@nutricore.dev", "editor123");

        mockMvc.perform(get("/api/v1/admin/users")
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(editorToken)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Você não possui permissão para acessar este recurso."));

        mockMvc.perform(put("/api/v1/admin/users/{id}", userId)
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(adminToken))
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "fullName": "Editor de Rotina",
                                  "email": "editor.admin@nutricore.dev",
                                  "role": "EDITOR",
                                  "active": true
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Editor de Rotina"));

        mockMvc.perform(patch("/api/v1/admin/users/{id}/password", userId)
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(adminToken))
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "newPassword": "novaSenha123"
                                }
                                """))
                .andExpect(status().isNoContent());

        mockMvc.perform(post("/api/v1/auth/login")
                        .contextPath(API_CONTEXT)
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "editor.admin@nutricore.dev",
                                  "password": "editor123"
                                }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("E-mail ou senha inválidos."));

        loginAndExtractToken("editor.admin@nutricore.dev", "novaSenha123");

        mockMvc.perform(patch("/api/v1/admin/users/{id}/deactivate", userId)
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(false));

        mockMvc.perform(post("/api/v1/auth/login")
                        .contextPath(API_CONTEXT)
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "editor.admin@nutricore.dev",
                                  "password": "novaSenha123"
                                }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Sua conta editorial está desativada."));
    }

    private String loginAndExtractToken(String email, String password) throws Exception {
        String responseBody = mockMvc.perform(post("/api/v1/auth/login")
                        .contextPath(API_CONTEXT)
                        .contentType(APPLICATION_JSON)
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
