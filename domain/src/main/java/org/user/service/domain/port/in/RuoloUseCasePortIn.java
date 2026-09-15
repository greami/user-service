package org.user.service.domain.port.in;

import org.user.service.domain.model.RuoloRecord;

import java.util.List;
import java.util.Optional;

public interface RuoloUseCasePortIn {
    Optional<RuoloRecord> findById(Integer id);
    RuoloRecord save(RuoloRecord ruolo);
    List<RuoloRecord> findAll();
    Boolean deleteById(Integer id);
    Boolean updateById(Integer id, String nome, String descrizione);
}
