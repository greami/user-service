package org.user.service.adapter.port.out;

import org.springframework.stereotype.Repository;
import org.user.service.adapter.mapper.RuoloMapper;
import org.user.service.adapter.repository.JpaRuoloRepository;
import org.user.service.adapter.repository.entity.RuoloEntity;
import org.user.service.domain.model.RuoloRecord;
import org.user.service.domain.port.out.RuoloRepositoryPortOut;

import java.util.List;
import java.util.Optional;

@Repository
public class RuoloRepositoryPortOutImpl implements RuoloRepositoryPortOut {

    private final JpaRuoloRepository jpaRuoloRepository;
    private final RuoloMapper ruoloMapper;

    public RuoloRepositoryPortOutImpl(JpaRuoloRepository jpaRuoloRepository, RuoloMapper ruoloMapper) {
        this.jpaRuoloRepository = jpaRuoloRepository;
        this.ruoloMapper = ruoloMapper;
    }

    @Override
    public Optional<RuoloRecord> findById(Integer id) {
        return jpaRuoloRepository.findById(id)
                .map(entity -> new RuoloRecord(
                        entity.getId().longValue(),
                        entity.getNome(),
                        entity.getDescrizione()
                ));
    }

    @Override
    public RuoloRecord save(RuoloRecord ruolo) {
        RuoloEntity saved = jpaRuoloRepository.save(ruoloMapper.toRuoloEntity(ruolo));
        return new RuoloRecord(
                saved.getId().longValue(),
                saved.getNome(),
                saved.getDescrizione()
        );
    }

    @Override
    public List<RuoloRecord> findAll() {
        return jpaRuoloRepository.findAll().stream()
                .map(entity -> new RuoloRecord(
                        entity.getId().longValue(),
                        entity.getNome(),
                        entity.getDescrizione()
                ))
                .toList();
    }

    @Override
    public Boolean deleteById(Integer id) {
        if (!jpaRuoloRepository.existsById(id)) {
            return false;
        }
        jpaRuoloRepository.deleteById(id);
        return true;
    }

    @Override
    public Boolean updateById(Integer id, String nome, String descrizione) {
        return jpaRuoloRepository.findById(id)
                .map(existing -> {
                    RuoloEntity updated = new RuoloEntity(
                            existing.getId(),
                            nome,
                            descrizione
                    );
                    return jpaRuoloRepository.save(updated) != null;
                })
                .orElse(false);
    }
}
