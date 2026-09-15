package org.user.service.adapter.port.in;

import org.openapitools.api.RuoliApi;
import org.openapitools.model.RuoloCreateRequest;
import org.openapitools.model.RuoloResponse;
import org.openapitools.model.RuoloUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.user.service.adapter.mapper.RuoloMapper;
import org.user.service.domain.model.RuoloRecord;
import org.user.service.domain.port.in.RuoloUseCasePortIn;

import java.util.List;

@RestController
public class RuoloController implements RuoliApi {

    @Autowired
    private RuoloUseCasePortIn ruoloUseCasePortIn;

    @Autowired
    private RuoloMapper ruoloMapper;

    @Override
    public ResponseEntity<RuoloResponse> createRuolo(RuoloCreateRequest ruoloCreateRequest) {
        RuoloRecord saved = ruoloUseCasePortIn.save(
                new RuoloRecord(
                        ruoloCreateRequest.getIdRuolo().longValue(),
                        ruoloCreateRequest.getNome(),
                        ruoloCreateRequest.getDescrizione()
                ));
        return saved != null
                ? ResponseEntity.status(201).body(ruoloMapper.toRuoloResponse(saved))
                : ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<Void> deleteRuolo(Integer idRuolo) {
        return ruoloUseCasePortIn.deleteById(idRuolo)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<RuoloResponse> getRuoloById(Integer idRuolo) {
        return ruoloUseCasePortIn.findById(idRuolo)
                .map(record -> ResponseEntity.ok(ruoloMapper.toRuoloResponse(record)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<RuoloResponse>> listRuoli() {
        List<RuoloResponse> ruoli = ruoloUseCasePortIn.findAll().stream()
                .map(ruoloMapper::toRuoloResponse)
                .toList();
        return ResponseEntity.ok(ruoli);
    }

    @Override
    public ResponseEntity<RuoloResponse> updateRuolo(Integer idRuolo, RuoloUpdateRequest ruoloUpdateRequest) {
        boolean updated = ruoloUseCasePortIn.updateById(
                idRuolo,
                ruoloUpdateRequest.getNome(),
                ruoloUpdateRequest.getDescrizione()
        );

        if (!updated) {
            return ResponseEntity.notFound().build();
        }

        return ruoloUseCasePortIn.findById(idRuolo)
                .map(record -> ResponseEntity.ok(ruoloMapper.toRuoloResponse(record)))
                .orElse(ResponseEntity.notFound().build());
    }
}
