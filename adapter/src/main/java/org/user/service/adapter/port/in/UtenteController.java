package org.user.service.adapter.port.in;

import org.openapitools.api.UtentiApi;
import org.openapitools.model.UtenteCreateRequest;
import org.openapitools.model.UtenteResponse;
import org.openapitools.model.UtenteConRuoliResponse;
import org.openapitools.model.UtenteUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.user.service.adapter.mapper.UtenteMapper;
import org.user.service.adapter.mapper.RuoloMapper;
import org.user.service.domain.model.UtenteRecord;
import org.user.service.domain.model.UtenteConRuoliRecord;
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

    @Autowired
    private RuoloMapper ruoloMapper;

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
    public ResponseEntity<UtenteConRuoliResponse> getUtenteByEmail(String email) {
        Optional<UtenteConRuoliRecord> utente = utenteUseCasePortIn.findByEmailWithRuoli(email);
        if (utente == null) {
            return ResponseEntity.notFound().build();
        }
        return utente
                .map(record -> ResponseEntity.ok(toUtenteConRuoliResponse(record)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<UtenteConRuoliResponse> getUtenteById(Integer idUtente) {
        Optional<UtenteConRuoliRecord> utente = utenteUseCasePortIn.findByIdWithRuoli(idUtente);
        if (utente == null) {
            return ResponseEntity.notFound().build();
        }
        return utente
                .map(record -> ResponseEntity.ok(toUtenteConRuoliResponse(record)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<UtenteConRuoliResponse> getUtenteByNomeUtente(String nomeUtente) {
        Optional<UtenteConRuoliRecord> utente = utenteUseCasePortIn.findByNomeUtenteWithRuoli(nomeUtente);
        if (utente == null) {
            return ResponseEntity.notFound().build();
        }
        return utente
                .map(record -> ResponseEntity.ok(toUtenteConRuoliResponse(record)))
                .orElse(ResponseEntity.notFound().build());
    }

    private UtenteConRuoliResponse toUtenteConRuoliResponse(UtenteConRuoliRecord record) {
        UtenteResponse baseResponse = utenteMapper.toUtenteResponse(record.utente());
        UtenteConRuoliResponse response = new UtenteConRuoliResponse();
        response.setIdUtente(baseResponse.getIdUtente());
        response.setNomeUtente(baseResponse.getNomeUtente());
        response.setNome(baseResponse.getNome());
        response.setCognome(baseResponse.getCognome());
        response.setEmail(baseResponse.getEmail());
        response.setRuoli(record.ruoli().stream()
                .map(ruoloMapper::toRuoloResponse)
                .toList());
        response.setPassword(Objects.requireNonNullElse(record.utente().password(), ""));
        return response;
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
    public ResponseEntity<List<UtenteConRuoliResponse>> cercaUtentiLikeNome(String nomeUtente) {
        List<UtenteConRuoliRecord> utentiRecords =
                utenteUseCasePortIn.findByNomeUtenteContainingIgnoreCaseWithRuoli(nomeUtente);
        if (utentiRecords == null) {
            utentiRecords = List.of();
        }
        List<UtenteConRuoliResponse> utenti = utentiRecords.stream()
                .map(this::toUtenteConRuoliResponse)
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

}
