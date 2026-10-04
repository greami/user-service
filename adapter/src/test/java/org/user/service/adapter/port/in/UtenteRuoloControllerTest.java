package org.user.service.adapter.port.in;

import org.junit.jupiter.api.Test;
import org.openapitools.model.UtenteRuoloResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.user.service.adapter.mapper.UtenteRuoloMapper;
import org.user.service.domain.model.UtenteRuoloRecord;
import org.user.service.domain.port.in.UtenteRuoloUseCasePortIn;

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

@WebMvcTest(controllers = UtenteRuoloController.class)
class UtenteRuoloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtenteRuoloUseCasePortIn utenteRuoloUseCasePortIn;

    @MockBean
    private UtenteRuoloMapper utenteRuoloMapper;

    @Test
    void createUtenteRuoloReturnsCreatedAssociation() throws Exception {
        UtenteRuoloRecord saved = associationRecord();
        when(utenteRuoloUseCasePortIn.save(any(UtenteRuoloRecord.class))).thenReturn(saved);
        when(utenteRuoloMapper.toUtenteRuoloResponse(saved)).thenReturn(associationResponse());

        mockMvc.perform(post("/utente-ruoli")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"idUtente":7,"idRuolo":2}
                                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idUtenteRuolo").value(11))
                .andExpect(jsonPath("$.idUtente").value(7))
                .andExpect(jsonPath("$.idRuolo").value(2));
    }

    @Test
    void createUtenteRuoloReturnsBadRequestWhenBodyIsMissing() throws Exception {
        mockMvc.perform(post("/utente-ruoli").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUtenteRuoloReturnsBadRequestWhenSaveFails() throws Exception {
        when(utenteRuoloUseCasePortIn.save(any(UtenteRuoloRecord.class))).thenReturn(null);

        mockMvc.perform(post("/utente-ruoli")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"idUtente":7,"idRuolo":2}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listUtenteRuoliReturnsAssociations() throws Exception {
        when(utenteRuoloUseCasePortIn.findAll()).thenReturn(List.of(associationRecord()));
        when(utenteRuoloMapper.toUtenteRuoloResponse(any(UtenteRuoloRecord.class)))
                .thenReturn(associationResponse());

        mockMvc.perform(get("/utente-ruoli"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idUtenteRuolo").value(11))
                .andExpect(jsonPath("$[0].idUtente").value(7));
    }

    @Test
    void getUtenteRuoloByIdReturnsAssociation() throws Exception {
        when(utenteRuoloUseCasePortIn.findById(11)).thenReturn(Optional.of(associationRecord()));
        when(utenteRuoloMapper.toUtenteRuoloResponse(any(UtenteRuoloRecord.class)))
                .thenReturn(associationResponse());

        mockMvc.perform(get("/utente-ruoli/11"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idUtenteRuolo").value(11))
                .andExpect(jsonPath("$.idRuolo").value(2));
    }

    @Test
    void getUtenteRuoloByIdReturnsNotFoundWhenAssociationDoesNotExist() throws Exception {
        when(utenteRuoloUseCasePortIn.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/utente-ruoli/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUtenteRuoloByUtenteAndRuoloReturnsAssociation() throws Exception {
        when(utenteRuoloUseCasePortIn.findByIdUtenteAndIdRuolo(7, 2))
                .thenReturn(Optional.of(associationRecord()));
        when(utenteRuoloMapper.toUtenteRuoloResponse(any(UtenteRuoloRecord.class)))
                .thenReturn(associationResponse());

        mockMvc.perform(get("/utente-ruoli/cerca").queryParam("idUtente", "7").queryParam("idRuolo", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idUtente").value(7))
                .andExpect(jsonPath("$.idRuolo").value(2));
    }

    @Test
    void getUtenteRuoloByUtenteAndRuoloReturnsNotFoundWhenAssociationDoesNotExist() throws Exception {
        when(utenteRuoloUseCasePortIn.findByIdUtenteAndIdRuolo(7, 9)).thenReturn(Optional.empty());

        mockMvc.perform(get("/utente-ruoli/cerca").queryParam("idUtente", "7").queryParam("idRuolo", "9"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getRuoliByUtenteReturnsRoleIds() throws Exception {
        when(utenteRuoloUseCasePortIn.findIdRuoloByIdUtente(7)).thenReturn(List.of(2, 3));

        mockMvc.perform(get("/utente-ruoli/utente/7"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value(2))
                .andExpect(jsonPath("$[1]").value(3));
    }

    @Test
    void getUtenteRuoloByUtenteAndRuoloReturnsBadRequestWhenQueryIsMissing() throws Exception {
        mockMvc.perform(get("/utente-ruoli/cerca").queryParam("idUtente", "7"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUtenteRuoloReturnsUpdatedAssociation() throws Exception {
        when(utenteRuoloUseCasePortIn.updateById(11, 7L, 2L)).thenReturn(true);
        when(utenteRuoloUseCasePortIn.findById(11)).thenReturn(Optional.of(associationRecord()));
        when(utenteRuoloMapper.toUtenteRuoloResponse(any(UtenteRuoloRecord.class)))
                .thenReturn(associationResponse());

        mockMvc.perform(put("/utente-ruoli/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"idUtente":7,"idRuolo":2}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idUtenteRuolo").value(11))
                .andExpect(jsonPath("$.idRuolo").value(2));
    }

    @Test
    void updateUtenteRuoloReturnsNotFoundWhenAssociationDoesNotExist() throws Exception {
        when(utenteRuoloUseCasePortIn.updateById(99, 7L, 2L)).thenReturn(false);

        mockMvc.perform(put("/utente-ruoli/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"idUtente":7,"idRuolo":2}
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUtenteRuoloReturnsNotFoundWhenUpdatedAssociationCannotBeReloaded() throws Exception {
        when(utenteRuoloUseCasePortIn.updateById(11, 7L, 2L)).thenReturn(true);
        when(utenteRuoloUseCasePortIn.findById(11)).thenReturn(Optional.empty());

        mockMvc.perform(put("/utente-ruoli/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"idUtente":7,"idRuolo":2}
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUtenteRuoloReturnsNoContentWhenDeleted() throws Exception {
        when(utenteRuoloUseCasePortIn.deleteById(11)).thenReturn(true);

        mockMvc.perform(delete("/utente-ruoli/11"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUtenteRuoloReturnsNotFoundWhenAssociationDoesNotExist() throws Exception {
        when(utenteRuoloUseCasePortIn.deleteById(99)).thenReturn(false);

        mockMvc.perform(delete("/utente-ruoli/99"))
                .andExpect(status().isNotFound());
    }

    private UtenteRuoloRecord associationRecord() {
        return new UtenteRuoloRecord(11L, 7L, 2L);
    }

    private UtenteRuoloResponse associationResponse() {
        return new UtenteRuoloResponse(7, 2, 11);
    }
}
