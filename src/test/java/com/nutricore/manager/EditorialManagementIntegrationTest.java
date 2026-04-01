package com.nutricore.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class EditorialManagementIntegrationTest {

    private static final String API_CONTEXT = "/api";
    private static final String ADMIN_EMAIL = "albertovilar1@gmail.com";
    private static final String ADMIN_PASSWORD = "132747";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRequireAuthenticationForAdminEndpoints() throws Exception {
        mockMvc.perform(get("/api/v1/admin/posts").contextPath(API_CONTEXT))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(containsString("Autenticacao obrigatoria")));
    }

    @Test
    void shouldCreateDraftPostAndKeepItPrivate() throws Exception {
        String accessToken = loginAndExtractToken(ADMIN_EMAIL, ADMIN_PASSWORD);

        String requestBody = """
                {
                  "title": "Post em rascunho de teste",
                  "summary": "Resumo interno",
                  "body": "Conteudo ainda nao publicado para o site publico.",
                  "category": "Teste",
                  "featured": false,
                  "status": "DRAFT"
                }
                """;

        mockMvc.perform(post("/api/v1/admin/posts")
                        .contextPath(API_CONTEXT)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", bearerToken(accessToken))
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("DRAFT"))
                .andExpect(jsonPath("$.slug").value("post-em-rascunho-de-teste"));

        mockMvc.perform(get("/api/v1/admin/posts")
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(accessToken))
                        .param("status", "DRAFT"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Post em rascunho de teste")));

        mockMvc.perform(get("/api/v1/public/posts/post-em-rascunho-de-teste")
                        .contextPath(API_CONTEXT))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldPublishAndUnpublishPost() throws Exception {
        String accessToken = loginAndExtractToken(ADMIN_EMAIL, ADMIN_PASSWORD);

        String requestBody = """
                {
                  "title": "Publicacao controlada por status",
                  "summary": "Resumo para publicar",
                  "body": "Corpo completo para validar publicacao e despublicacao.",
                  "category": "Teste",
                  "featured": true,
                  "status": "DRAFT"
                }
                """;

        String createResponse = mockMvc.perform(post("/api/v1/admin/posts")
                        .contextPath(API_CONTEXT)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", bearerToken(accessToken))
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(createResponse).get("id").asLong();

        mockMvc.perform(patch("/api/v1/admin/posts/{id}/publish", id)
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(accessToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PUBLISHED"))
                .andExpect(jsonPath("$.publishedAt").exists());

        mockMvc.perform(get("/api/v1/public/posts/publicacao-controlada-por-status")
                        .contextPath(API_CONTEXT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Publicacao controlada por status"));

        mockMvc.perform(patch("/api/v1/admin/posts/{id}/draft", id)
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(accessToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DRAFT"));

        mockMvc.perform(get("/api/v1/public/posts/publicacao-controlada-por-status")
                        .contextPath(API_CONTEXT))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateArticleAndExposeItOnlyWhenPublished() throws Exception {
        String accessToken = loginAndExtractToken(ADMIN_EMAIL, ADMIN_PASSWORD);

        String requestBody = """
                {
                  "title": "Artigo sobre adesao no dia a dia",
                  "summary": "Resumo do artigo",
                  "body": "Texto completo para explicar adesao e previsibilidade alimentar ao longo da semana.",
                  "tags": ["adesao", "rotina"],
                  "category": "Rotina",
                  "featured": true,
                  "status": "PUBLISHED"
                }
                """;

        mockMvc.perform(post("/api/v1/admin/articles")
                        .contextPath(API_CONTEXT)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", bearerToken(accessToken))
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("PUBLISHED"))
                .andExpect(jsonPath("$.readTimeMinutes").value(greaterThanOrEqualTo(1)));

        mockMvc.perform(get("/api/v1/public/articles/artigo-sobre-adesao-no-dia-a-dia")
                        .contextPath(API_CONTEXT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.featured").value(true))
                .andExpect(jsonPath("$.tags.length()").value(2));
    }

    @Test
    void shouldArchiveRecipeAndHideItFromPublicApi() throws Exception {
        String accessToken = loginAndExtractToken(ADMIN_EMAIL, ADMIN_PASSWORD);

        String requestBody = """
                {
                  "title": "Receita de teste para arquivamento",
                  "description": "Descricao da receita de teste",
                  "imageUrl": "/images/recipe-1.jpg",
                  "ingredients": ["Ingrediente 1", "Ingrediente 2"],
                  "preparationSteps": ["Passo 1", "Passo 2"],
                  "prepTimeMinutes": 18,
                  "yieldInfo": "2 porcoes",
                  "category": "Teste",
                  "featured": false,
                  "status": "PUBLISHED"
                }
                """;

        String createResponse = mockMvc.perform(post("/api/v1/admin/recipes")
                        .contextPath(API_CONTEXT)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", bearerToken(accessToken))
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(createResponse).get("id").asLong();

        mockMvc.perform(get("/api/v1/public/recipes/receita-de-teste-para-arquivamento")
                        .contextPath(API_CONTEXT))
                .andExpect(status().isOk());

        mockMvc.perform(patch("/api/v1/admin/recipes/{id}/archive", id)
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(accessToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ARCHIVED"));

        mockMvc.perform(get("/api/v1/public/recipes/receita-de-teste-para-arquivamento")
                        .contextPath(API_CONTEXT))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUploadEditorialImage() throws Exception {
        String accessToken = loginAndExtractToken(ADMIN_EMAIL, ADMIN_PASSWORD);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "cover.png",
                "image/png",
                new byte[]{1, 2, 3, 4}
        );

        mockMvc.perform(multipart("/api/v1/admin/media/images")
                        .file(file)
                        .contextPath(API_CONTEXT)
                        .header("Authorization", bearerToken(accessToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value(containsString("/api/media/images/")))
                .andExpect(jsonPath("$.contentType").value("image/png"));
    }

    private String loginAndExtractToken(String email, String password) throws Exception {
        String requestBody = """
                {
                  "email": "%s",
                  "password": "%s"
                }
                """.formatted(email, password);

        String responseBody = mockMvc.perform(post("/api/v1/auth/login")
                        .contextPath(API_CONTEXT)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
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
