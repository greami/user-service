package org.user.service.adapter.port.in;

import org.openapitools.api.UtentiApi;
import org.openapitools.model.UtenteCreateRequest;
import org.openapitools.model.UtenteResponse;
import org.openapitools.model.UtenteUpdateRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.user.service.adapter.mapper.UtenteMapper;
import org.user.service.domain.model.UtenteRecord;
import org.user.service.domain.port.in.UtenteUseCasePortIn;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class UtenteController implements UtentiApi {

    @Autowired
    private UtenteUseCasePortIn utenteUseCasePortIn;

    @Autowired
    private UtenteMapper utenteMapper;

    @Override
    public ResponseEntity<UtenteResponse> createUtente(UtenteCreateRequest utenteCreateRequest) {
        UtenteRecord saved = utenteUseCasePortIn.save(
                new UtenteRecord(
                        null,
                        utenteCreateRequest.getNomeUtente(),
                        utenteCreateRequest.getNome(),
                        utenteCreateRequest.getCognome(),
                        utenteCreateRequest.getEmail(),
                        utenteCreateRequest.getPassword()
                ));
        return saved != null
                ? ResponseEntity.status(201).body(utenteMapper.toUtenteResponse(saved))
                : ResponseEntity.badRequest().build();
    }


    @Override
    public ResponseEntity<Void> deleteUtente(Integer idUtente) {
        Boolean deleted = utenteUseCasePortIn.deleteById(idUtente);
        if (deleted == null) {
            return ResponseEntity.internalServerError().build();
        }
        return deleted
                ? ResponseEntity.noContent().<Void>build()
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<UtenteResponse> getUtenteByEmail(String email) {
        Optional<UtenteRecord> utente = utenteUseCasePortIn.findByEmail(email);
        if (utente == null) {
            return ResponseEntity.notFound().build();
        }
        return utente
                .map(record -> ResponseEntity.ok(utenteMapper.toUtenteResponse(record)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<UtenteResponse> getUtenteById(Integer idUtente) {
        Optional<UtenteRecord> utente = utenteUseCasePortIn.findById(idUtente);
        if (utente == null) {
            return ResponseEntity.notFound().build();
        }
        return utente
                .map(record -> ResponseEntity.ok(utenteMapper.toUtenteResponse(record)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<UtenteResponse> getUtenteByNomeUtente(String nomeUtente) {
        Optional<UtenteRecord> utente = utenteUseCasePortIn.findByNomeUtente(nomeUtente);
        if (utente == null) {
            return ResponseEntity.notFound().build();
        }
        return utente
                .map(record -> {
                    UtenteResponse baseResponse = utenteMapper.toUtenteResponse(record);
                    UtenteByNomeUtenteResponse response = new UtenteByNomeUtenteResponse(baseResponse);
                    response.setPassword(Objects.requireNonNullElse(record.password(), ""));
                    return ResponseEntity.ok((UtenteResponse) response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<UtenteResponse> getUtenteByUserNameAndPassword(String userName, String password) {
        Optional<UtenteRecord> utente = utenteUseCasePortIn.findByNomeUtenteAndPassword(userName, password);
        if (utente == null) {
            return ResponseEntity.notFound().build();
        }
        return utente
                .map(record -> ResponseEntity.ok(utenteMapper.toUtenteResponse(record)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<UtenteResponse>> listUtenti() {
        List<UtenteRecord> utentiRecords = utenteUseCasePortIn.findAll();
        if (utentiRecords == null) {
            return ResponseEntity.notFound().build();
        }
        List<UtenteResponse> utenti = utentiRecords.stream()
                .map(utenteMapper::toUtenteResponse)
                .toList();
        return ResponseEntity.ok(utenti);
    }

    @Override
    public ResponseEntity<List<UtenteResponse>> cercaUtentiLikeNome(String nomeUtente) {
        List<UtenteRecord> utentiRecords =
                utenteUseCasePortIn.findByNomeUtenteContainingIgnoreCase(nomeUtente);
        if (utentiRecords == null) {
            utentiRecords = List.of();
        }
        List<UtenteResponse> utenti = utentiRecords.stream()
                .map(utenteMapper::toUtenteResponse)
                .toList();
        return ResponseEntity.ok(utenti);
    }

    @Override
    public ResponseEntity<UtenteResponse> updateUtente(Integer idUtente, UtenteUpdateRequest utenteUpdateRequest) {
        Boolean updated = utenteUseCasePortIn.updateById(
                idUtente,
                utenteUpdateRequest.getNomeUtente(),
                utenteUpdateRequest.getNome(),
                utenteUpdateRequest.getCognome(),
                utenteUpdateRequest.getEmail(),
                utenteUpdateRequest.getPassword());

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        if (!updated) {
            return ResponseEntity.notFound().build();
        }

        Optional<UtenteRecord> utente = utenteUseCasePortIn.findById(idUtente);
        if (utente == null) {
            return ResponseEntity.internalServerError().build();
        }
        return utente
                .map(record -> ResponseEntity.ok(utenteMapper.toUtenteResponse(record)))
                .orElse(ResponseEntity.notFound().build());
    }

    private static final class UtenteByNomeUtenteResponse extends UtenteResponse {
        @JsonProperty("password")
        private String password;

        private UtenteByNomeUtenteResponse(UtenteResponse source) {
            this.setIdUtente(source.getIdUtente());
            this.setNomeUtente(source.getNomeUtente());
            this.setNome(source.getNome());
            this.setCognome(source.getCognome());
            this.setEmail(source.getEmail());
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
