package org.user.service.application.port.in.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.user.service.domain.model.RuoloRecord;
import org.user.service.domain.port.in.RuoloUseCasePortIn;
import org.user.service.domain.port.out.RuoloRepositoryPortOut;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RuoloUseCaseImpl implements RuoloUseCasePortIn {

    @Autowired
    private final RuoloRepositoryPortOut ruoloRepository;

    @Override
    public Optional<RuoloRecord> findById(Integer id) {
        return ruoloRepository.findById(id);
    }

    @Override
    public RuoloRecord save(RuoloRecord ruolo) {
        return ruoloRepository.save(ruolo);
    }

    @Override
    public List<RuoloRecord> findAll() {
        return ruoloRepository.findAll();
    }

    @Override
    public Boolean deleteById(Integer id) {
        return ruoloRepository.deleteById(id);
    }

    @Override
    public Boolean updateById(Integer id, String nome, String descrizione) {
        return ruoloRepository.updateById(id, nome, descrizione);
    }
}
