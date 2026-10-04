package org.user.service.adapter.port.in;

import org.junit.jupiter.api.Test;
import org.openapitools.model.RuoloResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.user.service.adapter.mapper.RuoloMapper;
import org.user.service.domain.model.RuoloRecord;
import org.user.service.domain.port.in.RuoloUseCasePortIn;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RuoloController.class)
class RuoloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuoloUseCasePortIn ruoloUseCasePortIn;

    @MockBean
    private RuoloMapper ruoloMapper;

    @Test
    void createRuoloReturnsCreatedRole() throws Exception {
        RuoloRecord saved = roleRecord();
        when(ruoloUseCasePortIn.save(any(RuoloRecord.class))).thenReturn(saved);
        when(ruoloMapper.toRuoloResponse(saved)).thenReturn(roleResponse());

        mockMvc.perform(post("/ruoli")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"idRuolo":2,"nome":"admin","descrizione":"Amministratore"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idRuolo").value(2))
                .andExpect(jsonPath("$.nome").value("admin"));
    }

    @Test
    void createRuoloReturnsBadRequestWhenBodyIsMissing() throws Exception {
        mockMvc.perform(post("/ruoli").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRuoloReturnsBadRequestWhenSaveFails() throws Exception {
        when(ruoloUseCasePortIn.save(any(RuoloRecord.class))).thenReturn(null);

        mockMvc.perform(post("/ruoli")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"idRuolo":2,"nome":"admin","descrizione":"Amministratore"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listRuoliReturnsRoles() throws Exception {
        when(ruoloUseCasePortIn.findAll()).thenReturn(List.of(roleRecord()));
        when(ruoloMapper.toRuoloResponse(any(RuoloRecord.class))).thenReturn(roleResponse());

        mockMvc.perform(get("/ruoli"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idRuolo").value(2))
                .andExpect(jsonPath("$[0].nome").value("admin"));
    }

    @Test
    void getRuoloByIdReturnsRole() throws Exception {
        when(ruoloUseCasePortIn.findById(2)).thenReturn(Optional.of(roleRecord()));
        when(ruoloMapper.toRuoloResponse(any(RuoloRecord.class))).thenReturn(roleResponse());

        mockMvc.perform(get("/ruoli/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idRuolo").value(2))
                .andExpect(jsonPath("$.descrizione").value("Amministratore"));
    }

    @Test
    void getRuoloByIdReturnsNotFoundWhenRoleDoesNotExist() throws Exception {
        when(ruoloUseCasePortIn.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/ruoli/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateRuoloReturnsUpdatedRole() throws Exception {
        when(ruoloUseCasePortIn.updateById(2, "admin", "Amministratore")).thenReturn(true);
        when(ruoloUseCasePortIn.findById(2)).thenReturn(Optional.of(roleRecord()));
        when(ruoloMapper.toRuoloResponse(any(RuoloRecord.class))).thenReturn(roleResponse());

        mockMvc.perform(put("/ruoli/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"idRuolo":2,"nome":"admin","descrizione":"Amministratore"}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idRuolo").value(2))
                .andExpect(jsonPath("$.nome").value("admin"));
    }

    @Test
    void updateRuoloReturnsNotFoundWhenRoleDoesNotExist() throws Exception {
        when(ruoloUseCasePortIn.updateById(99, "admin", "Amministratore")).thenReturn(false);

        mockMvc.perform(put("/ruoli/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"idRuolo":2,"nome":"admin","descrizione":"Amministratore"}
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateRuoloReturnsNotFoundWhenUpdatedRoleCannotBeReloaded() throws Exception {
        when(ruoloUseCasePortIn.updateById(2, "admin", "Amministratore")).thenReturn(true);
        when(ruoloUseCasePortIn.findById(2)).thenReturn(Optional.empty());

        mockMvc.perform(put("/ruoli/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"idRuolo":2,"nome":"admin","descrizione":"Amministratore"}
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRuoloReturnsNoContentWhenDeleted() throws Exception {
        when(ruoloUseCasePortIn.deleteById(2)).thenReturn(true);

        mockMvc.perform(delete("/ruoli/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteRuoloReturnsNotFoundWhenRoleDoesNotExist() throws Exception {
        when(ruoloUseCasePortIn.deleteById(99)).thenReturn(false);

        mockMvc.perform(delete("/ruoli/99"))
                .andExpect(status().isNotFound());
    }

    private RuoloRecord roleRecord() {
        return new RuoloRecord(2L, "admin", "Amministratore");
    }

    private RuoloResponse roleResponse() {
        return new RuoloResponse("admin", "Amministratore", 2);
    }
}
