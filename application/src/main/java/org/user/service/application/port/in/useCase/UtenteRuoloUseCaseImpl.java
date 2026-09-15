package org.user.service.application.port.in.useCase;

import org.springframework.stereotype.Service;
import org.user.service.domain.model.UtenteRuoloRecord;
import org.user.service.domain.port.in.UtenteRuoloUseCasePortIn;
import org.user.service.domain.port.out.UtenteRuoloRepositoryPortOut;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteRuoloUseCaseImpl implements UtenteRuoloUseCasePortIn {

    private final UtenteRuoloRepositoryPortOut utenteRuoloRepositoryPortOut;

    public UtenteRuoloUseCaseImpl(UtenteRuoloRepositoryPortOut utenteRuoloRepositoryPortOut) {
        this.utenteRuoloRepositoryPortOut = utenteRuoloRepositoryPortOut;
    }

    @Override
    public Optional<UtenteRuoloRecord> findById(Integer id) {
        return utenteRuoloRepositoryPortOut.findById(id);
    }

    @Override
    public UtenteRuoloRecord save(UtenteRuoloRecord utenteRuolo) {
        return utenteRuoloRepositoryPortOut.save(utenteRuolo);
    }

    @Override
    public List<UtenteRuoloRecord> findAll() {
        return utenteRuoloRepositoryPortOut.findAll();
    }

    @Override
    public Boolean deleteById(Integer id) {
        return utenteRuoloRepositoryPortOut.deleteById(id);
    }

    @Override
    public Boolean updateById(Integer id, Long idUtente, Long idRuolo) {
        return utenteRuoloRepositoryPortOut.updateById(id, idUtente, idRuolo);
    }

    @Override
    public Optional<UtenteRuoloRecord> findByIdUtenteAndIdRuolo(Integer idUtente, Integer idRuolo) {
        return utenteRuoloRepositoryPortOut.findByIdUtenteAndIdRuolo(idUtente, idRuolo);
    }

    @Override
    public List<Integer> findIdRuoloByIdUtente(Integer idUtente) {
        return utenteRuoloRepositoryPortOut.findIdRuoloByIdUtente(idUtente);
    }
}
