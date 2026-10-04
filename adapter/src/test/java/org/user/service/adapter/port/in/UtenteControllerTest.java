package org.user.service.adapter.port.in;

import org.junit.jupiter.api.Test;
import org.openapitools.model.UtenteResponse;
import org.openapitools.model.UtenteConRuoliResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.user.service.adapter.mapper.RuoloMapper;
import org.user.service.adapter.mapper.UtenteMapper;
import org.user.service.domain.model.UtenteConRuoliRecord;
import org.user.service.domain.model.UtenteRecord;
import org.user.service.domain.port.in.UtenteUseCasePortIn;

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

@WebMvcTest(controllers = UtenteController.class)
class UtenteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtenteUseCasePortIn utenteUseCasePortIn;

    @MockBean
    private UtenteMapper utenteMapper;

    @MockBean
    private RuoloMapper ruoloMapper;

    @Test
    void createUtenteReturnsCreatedUser() throws Exception {
        UtenteRecord saved = userRecord();
        when(utenteUseCasePortIn.save(any(UtenteRecord.class))).thenReturn(saved);
        when(utenteMapper.toUtenteResponse(saved)).thenReturn(userResponse());

        mockMvc.perform(post("/utenti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nomeUtente":"lrossi","nome":"Luca","cognome":"Rossi","email":"luca@example.com","password":"secret"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idUtente").value(7))
                .andExpect(jsonPath("$.nomeUtente").value("lrossi"))
                .andExpect(jsonPath("$.email").value("luca@example.com"));
    }

    @Test
    void createUtenteReturnsBadRequestWhenSaveFails() throws Exception {
        when(utenteUseCasePortIn.save(any(UtenteRecord.class))).thenReturn(null);

        mockMvc.perform(post("/utenti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nomeUtente":"lrossi","nome":"Luca","cognome":"Rossi","email":"luca@example.com","password":"secret"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUtenteReturnsBadRequestWhenBodyIsMissing() throws Exception {
        mockMvc.perform(post("/utenti").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listUtentiReturnsUsers() throws Exception {
        when(utenteUseCasePortIn.findAll()).thenReturn(List.of(userRecord()));
        when(utenteMapper.toUtenteResponse(any(UtenteRecord.class))).thenReturn(userResponse());

        mockMvc.perform(get("/utenti"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idUtente").value(7))
                .andExpect(jsonPath("$[0].nomeUtente").value("lrossi"));
    }

    @Test
    void listUtentiReturnsNotFoundWhenServiceReturnsNull() throws Exception {
        when(utenteUseCasePortIn.findAll()).thenReturn(null);

        mockMvc.perform(get("/utenti"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUtenteByIdReturnsUserWithRoles() throws Exception {
        when(utenteUseCasePortIn.findByIdWithRuoli(7)).thenReturn(Optional.of(userWithRoles()));
        when(utenteMapper.toUtenteResponse(any(UtenteRecord.class))).thenReturn(userResponse());

        mockMvc.perform(get("/utenti/7"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idUtente").value(7))
                .andExpect(jsonPath("$.nomeUtente").value("lrossi"))
                .andExpect(jsonPath("$.ruoli").isArray())
                .andExpect(jsonPath("$.password").value("secret"));
    }

    @Test
    void getUtenteByIdReturnsNotFoundWhenUserDoesNotExist() throws Exception {
        when(utenteUseCasePortIn.findByIdWithRuoli(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/utenti/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUtenteByEmailReturnsUser() throws Exception {
        when(utenteUseCasePortIn.findByEmailWithRuoli("luca@example.com"))
                .thenReturn(Optional.of(userWithRoles()));
        when(utenteMapper.toUtenteResponse(any(UtenteRecord.class))).thenReturn(userResponse());

        mockMvc.perform(get("/utenti/email/luca@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("luca@example.com"));
    }

    @Test
    void getUtenteByEmailReturnsNotFoundWhenUserDoesNotExist() throws Exception {
        when(utenteUseCasePortIn.findByEmailWithRuoli("missing@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/utenti/email/missing@example.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUtenteByNomeUtenteReturnsUser() throws Exception {
        when(utenteUseCasePortIn.findByNomeUtenteWithRuoli("lrossi"))
                .thenReturn(Optional.of(userWithRoles()));
        when(utenteMapper.toUtenteResponse(any(UtenteRecord.class))).thenReturn(userResponse());

        mockMvc.perform(get("/utenti/nome/lrossi"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nomeUtente").value("lrossi"));
    }

    @Test
    void getUtenteByNomeUtenteReturnsNotFoundWhenUserDoesNotExist() throws Exception {
        when(utenteUseCasePortIn.findByNomeUtenteWithRuoli("missing")).thenReturn(Optional.empty());

        mockMvc.perform(get("/utenti/nome/missing"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUtenteByCredentialsReturnsUser() throws Exception {
        when(utenteUseCasePortIn.findByNomeUtenteAndPassword("lrossi", "secret"))
                .thenReturn(Optional.of(userRecord()));
        when(utenteMapper.toUtenteResponse(any(UtenteRecord.class))).thenReturn(userResponse());

        mockMvc.perform(get("/utenti/credentials").queryParam("userName", "lrossi")
                        .queryParam("password", "secret"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idUtente").value(7));
    }

    @Test
    void getUtenteByCredentialsReturnsNotFoundWhenUserDoesNotExist() throws Exception {
        when(utenteUseCasePortIn.findByNomeUtenteAndPassword("missing", "secret"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/utenti/credentials").queryParam("userName", "missing")
                        .queryParam("password", "secret"))
                .andExpect(status().isNotFound());
    }

    @Test
    void cercaUtentiLikeNomeReturnsMatchingUsers() throws Exception {
        when(utenteUseCasePortIn.findByNomeUtenteContainingIgnoreCaseWithRuoli("ross"))
                .thenReturn(List.of(userWithRoles()));
        when(utenteMapper.toUtenteResponse(org.mockito.ArgumentMatchers.any(UtenteRecord.class)))
                .thenReturn(userResponse());

        mockMvc.perform(get("/utenti/cercaByNomeUtenteLike").queryParam("nomeUtente", "ross"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nomeUtente").value("lrossi"));
    }

    @Test
    void cercaUtentiLikeNomeReturnsBadRequestWhenQueryIsMissing() throws Exception {
        mockMvc.perform(get("/utenti/cercaByNomeUtenteLike"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cercaUtentiLikeNomeReturnsEmptyListWhenServiceReturnsNull() throws Exception {
        when(utenteUseCasePortIn.findByNomeUtenteContainingIgnoreCaseWithRuoli("missing")).thenReturn(null);

        mockMvc.perform(get("/utenti/cercaByNomeUtenteLike").queryParam("nomeUtente", "missing"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void updateUtenteReturnsUpdatedUser() throws Exception {
        when(utenteUseCasePortIn.updateById(7, "lrossi", "Luca", "Rossi", "luca@example.com", "secret"))
                .thenReturn(true);
        when(utenteUseCasePortIn.findById(7)).thenReturn(Optional.of(userRecord()));
        when(utenteMapper.toUtenteResponse(any(UtenteRecord.class))).thenReturn(userResponse());

        mockMvc.perform(put("/utenti/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nomeUtente":"lrossi","nome":"Luca","cognome":"Rossi","email":"luca@example.com","password":"secret"}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idUtente").value(7))
                .andExpect(jsonPath("$.email").value("luca@example.com"));
    }

    @Test
    void updateUtenteReturnsNotFoundWhenUserDoesNotExist() throws Exception {
        when(utenteUseCasePortIn.updateById(99, "lrossi", "Luca", "Rossi", "luca@example.com", "secret"))
                .thenReturn(false);

        mockMvc.perform(put("/utenti/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nomeUtente":"lrossi","nome":"Luca","cognome":"Rossi","email":"luca@example.com","password":"secret"}
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUtenteReturnsNotFoundWhenUpdatedUserCannotBeReloaded() throws Exception {
        when(utenteUseCasePortIn.updateById(7, "lrossi", "Luca", "Rossi", "luca@example.com", "secret"))
                .thenReturn(true);
        when(utenteUseCasePortIn.findById(7)).thenReturn(Optional.empty());

        mockMvc.perform(put("/utenti/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nomeUtente":"lrossi","nome":"Luca","cognome":"Rossi","email":"luca@example.com","password":"secret"}
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUtenteReturnsNoContentWhenDeleted() throws Exception {
        when(utenteUseCasePortIn.deleteById(7)).thenReturn(true);

        mockMvc.perform(delete("/utenti/7"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUtenteReturnsNotFoundWhenUserDoesNotExist() throws Exception {
        when(utenteUseCasePortIn.deleteById(99)).thenReturn(false);

        mockMvc.perform(delete("/utenti/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUtenteReturnsInternalServerErrorWhenDeleteResultIsNull() throws Exception {
        when(utenteUseCasePortIn.deleteById(7)).thenReturn(null);

        mockMvc.perform(delete("/utenti/7"))
                .andExpect(status().isInternalServerError());
    }

    private UtenteRecord userRecord() {
        return new UtenteRecord(7, "lrossi", "Luca", "Rossi", "luca@example.com", "secret");
    }

    private UtenteConRuoliRecord userWithRoles() {
        return new UtenteConRuoliRecord(userRecord(), List.of());
    }

    private UtenteResponse userResponse() {
        return new UtenteResponse("lrossi", "Luca", "Rossi", "luca@example.com", 7);
    }
}
