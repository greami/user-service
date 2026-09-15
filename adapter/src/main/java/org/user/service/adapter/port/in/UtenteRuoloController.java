package org.user.service.adapter.port.in;

import org.openapitools.api.UtenteRuoliApi;
import org.openapitools.model.UtenteRuoloBase;
import org.openapitools.model.UtenteRuoloResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.user.service.adapter.mapper.UtenteRuoloMapper;
import org.user.service.domain.model.UtenteRuoloRecord;
import org.user.service.domain.port.in.UtenteRuoloUseCasePortIn;

import java.util.List;

@RestController
public class UtenteRuoloController implements UtenteRuoliApi {

    @Autowired
    private UtenteRuoloUseCasePortIn utenteRuoloUseCasePortIn;

    @Autowired
    private UtenteRuoloMapper utenteRuoloMapper;

    @Override
    public ResponseEntity<UtenteRuoloResponse> createUtenteRuolo(UtenteRuoloBase utenteRuoloBase) {
        UtenteRuoloRecord saved = utenteRuoloUseCasePortIn.save(
                new UtenteRuoloRecord(
                        null,
                        utenteRuoloBase.getIdUtente().longValue(),
                        utenteRuoloBase.getIdRuolo().longValue()
                ));
        return saved != null
                ? ResponseEntity.status(201).body(utenteRuoloMapper.toUtenteRuoloResponse(saved))
                : ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<Void> deleteUtenteRuolo(Integer idUtenteRuolo) {
        return utenteRuoloUseCasePortIn.deleteById(idUtenteRuolo)
                ? ResponseEntity.noContent().<Void>build()
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<UtenteRuoloResponse> getUtenteRuoloById(Integer idUtenteRuolo) {
        return utenteRuoloUseCasePortIn.findById(idUtenteRuolo)
                .map(record -> ResponseEntity.ok(utenteRuoloMapper.toUtenteRuoloResponse(record)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<UtenteRuoloResponse> getUtenteRuoloByUtenteAndRuolo(Integer idUtente, Integer idRuolo) {
        return utenteRuoloUseCasePortIn.findByIdUtenteAndIdRuolo(idUtente, idRuolo)
                .map(record -> ResponseEntity.ok(utenteRuoloMapper.toUtenteRuoloResponse(record)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<Integer>> getRuoliByUtente(Integer idUtente) {
        List<Integer> idRuoli = utenteRuoloUseCasePortIn.findIdRuoloByIdUtente(idUtente);
        /*if (idRuoli == null || idRuoli.isEmpty()) {
            return ResponseEntity.notFound().build();
        }*/
        return ResponseEntity.ok(idRuoli);
    }

    @Override
    public ResponseEntity<List<UtenteRuoloResponse>> listUtenteRuoli() {
        List<UtenteRuoloResponse> utenteRuoli = utenteRuoloUseCasePortIn.findAll().stream()
                .map(utenteRuoloMapper::toUtenteRuoloResponse)
                .toList();
        return ResponseEntity.ok(utenteRuoli);
    }

    @Override
    public ResponseEntity<UtenteRuoloResponse> updateUtenteRuolo(Integer idUtenteRuolo, UtenteRuoloBase utenteRuoloBase) {
        boolean updated = utenteRuoloUseCasePortIn.updateById(
                idUtenteRuolo,
                utenteRuoloBase.getIdUtente().longValue(),
                utenteRuoloBase.getIdRuolo().longValue());

        if (!updated) {
            return ResponseEntity.notFound().build();
        }

        return utenteRuoloUseCasePortIn.findById(idUtenteRuolo)
                .map(record -> ResponseEntity.ok(utenteRuoloMapper.toUtenteRuoloResponse(record)))
                .orElse(ResponseEntity.notFound().build());
    }
}
