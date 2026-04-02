package com.nutricore.manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PublicContentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnPublicProfile() throws Exception {
        mockMvc.perform(get("/api/v1/public/profile").contextPath("/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Ana Paula Vilar Alves"))
                .andExpect(jsonPath("$.officeAddress").value("Rua Samaritana Maria Amália de Castilho, Cuité, PB, 58175-000, Brasil"))
                .andExpect(jsonPath("$.heroTitle").exists());
    }

    @Test
    void shouldReturnPublicPlans() throws Exception {
        mockMvc.perform(get("/api/v1/public/plans").contextPath("/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[1].featured").value(true));
    }

    @Test
    void shouldReturnPublicPosts() throws Exception {
        mockMvc.perform(get("/api/v1/public/posts").contextPath("/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].slug").value("rotina-da-nutri-dando-exemplo"))
                .andExpect(jsonPath("$[0].galleryImageUrls.length()").value(3));
    }

    @Test
    void shouldReturnPublicArticles() throws Exception {
        mockMvc.perform(get("/api/v1/public/articles").contextPath("/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].readTimeMinutes").exists());
    }

    @Test
    void shouldReturnPublicRecipes() throws Exception {
        mockMvc.perform(get("/api/v1/public/recipes").contextPath("/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].ingredients.length()").isNotEmpty());
    }

    @Test
    void shouldExposeCorsHeadersForFrontendOrigin() throws Exception {
        mockMvc.perform(get("/api/v1/public/profile")
                        .contextPath("/api")
                        .header("Origin", "http://localhost:5173"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:5173"));
    }
}
