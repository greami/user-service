package org.user.service.adapter.port.out;

import org.springframework.stereotype.Repository;
import org.user.service.adapter.mapper.UtenteRuoloMapper;
import org.user.service.adapter.repository.JpaUtenteRuoloRepository;
import org.user.service.adapter.repository.entity.UtenteRuoloEntity;
import org.user.service.domain.model.UtenteRuoloRecord;
import org.user.service.domain.port.out.UtenteRuoloRepositoryPortOut;

import java.util.List;
import java.util.Optional;

@Repository
public class UtenteRuoloRepositoryPortOutImpl implements UtenteRuoloRepositoryPortOut {

    private final JpaUtenteRuoloRepository jpaUtenteRuoloRepository;
    private final UtenteRuoloMapper utenteRuoloMapper;

    public UtenteRuoloRepositoryPortOutImpl(JpaUtenteRuoloRepository jpaUtenteRuoloRepository, UtenteRuoloMapper utenteRuoloMapper) {
        this.jpaUtenteRuoloRepository = jpaUtenteRuoloRepository;
        this.utenteRuoloMapper = utenteRuoloMapper;
    }

    @Override
    public Optional<UtenteRuoloRecord> findById(Integer id) {
        return jpaUtenteRuoloRepository.findById(id.longValue())
                .map(entity -> new UtenteRuoloRecord(
                        entity.getId().longValue(),
                        entity.getIdUtente(),
                        entity.getIdRuolo()
                ));
    }

    @Override
    public UtenteRuoloRecord save(UtenteRuoloRecord utenteRuolo) {
        UtenteRuoloEntity saved = jpaUtenteRuoloRepository.save(utenteRuoloMapper.toUtenteRuoloEntity(utenteRuolo));
        return new UtenteRuoloRecord(
                saved.getId().longValue(),
                saved.getIdUtente(),
                saved.getIdRuolo()
        );
    }

    @Override
    public List<UtenteRuoloRecord> findAll() {
        return jpaUtenteRuoloRepository.findAll().stream()
                .map(entity -> new UtenteRuoloRecord(
                        entity.getId().longValue(),
                        entity.getIdUtente(),
                        entity.getIdRuolo()
                ))
                .toList();
    }

    @Override
    public Boolean deleteById(Integer id) {
        if (!jpaUtenteRuoloRepository.existsById(id.longValue())) {
            return false;
        }
        jpaUtenteRuoloRepository.deleteById(id.longValue());
        return true;
    }

    @Override
    public Boolean updateById(Integer id, Long idUtente, Long idRuolo) {
        return jpaUtenteRuoloRepository.findById(id.longValue())
                .map(existing -> {
                    UtenteRuoloEntity updated = new UtenteRuoloEntity(
                            existing.getId(),
                            idUtente,
                            idRuolo
                    );
                    return jpaUtenteRuoloRepository.save(updated) != null;
                })
                .orElse(false);
    }

    @Override
    public Optional<UtenteRuoloRecord> findByIdUtenteAndIdRuolo(Integer idUtente, Integer idRuolo) {
        return jpaUtenteRuoloRepository.findByIdUtenteAndIdRuolo(idUtente.longValue(), idRuolo.longValue())
                .map(entity -> new UtenteRuoloRecord(
                        entity.getId().longValue(),
                        entity.getIdUtente(),
                        entity.getIdRuolo()
                ));
    }

    @Override
    public List<Integer> findIdRuoloByIdUtente(Integer idUtente) {
        return jpaUtenteRuoloRepository.findAllByIdUtente(idUtente.longValue()).stream()
                .map(UtenteRuoloEntity::getIdRuolo)
                .map(Long::intValue)
                .toList();
    }
}
