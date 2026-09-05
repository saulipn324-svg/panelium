package com.saul.panelium.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest @AutoConfigureMockMvc
class AuthFlowTests {
  @Autowired MockMvc mvc; @Autowired ObjectMapper json;

  @Test void readerCanRegisterButCannotUseAdminApi() throws Exception {
    String response=mvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content("""
      {"name":"Lector Demo","email":"reader@panelium.test","password":"Reader123!"}
      """)).andExpect(status().isCreated()).andExpect(jsonPath("$.user.role").value("READER")).andReturn().getResponse().getContentAsString();
    String token=json.readTree(response).get("token").asText();
    mvc.perform(post("/api/admin/works").header("Authorization","Bearer "+token).contentType(MediaType.APPLICATION_JSON).content("{}"))
      .andExpect(status().isForbidden());
  }

  @Test void invalidCredentialsAreRejected() throws Exception {
    mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content("""
      {"email":"nobody@panelium.test","password":"Wrong123!"}
      """)).andExpect(status().isUnauthorized());
  }
}
