package med.voll.api.controller;

import med.voll.api.model.consulta.ConsultaRequest;
import med.voll.api.model.consulta.ConsultaResponse;
import med.voll.api.model.especialidade.Especialidade;
import med.voll.api.service.consulta.ConsultaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<ConsultaRequest> consultaReqJson;

    @Autowired
    private JacksonTester<ConsultaResponse> consultaResJson;

    @MockBean
    private ConsultaService consultaService;

    @Test
    @DisplayName("Deveria devolver código HTTP 400 quando há informações inválidas")
    @WithMockUser
    void agendar_cenario1() throws Exception {
        var response = mvc.perform(post("/consultas/agendar")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 200 quando as infoprmações estão válidas")
    @WithMockUser
    void agendar_cenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;

        var consultaResponse = new ConsultaResponse(null, 2l, 5l, data);

        when(consultaService.agendar(any())).thenReturn(consultaResponse);

        var response = mvc
                .perform(
                        post("/consultas/agendar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(consultaReqJson.write(
                                        new ConsultaRequest(2l, 5l, data, especialidade)
                                ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = consultaResJson.write(consultaResponse).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

}