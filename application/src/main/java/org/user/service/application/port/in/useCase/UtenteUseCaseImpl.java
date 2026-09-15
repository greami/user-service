package org.user.service.application.port.in.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.user.service.domain.model.UtenteRecord;
import org.user.service.domain.port.in.UtenteUseCasePortIn;
import org.user.service.domain.port.out.UtenteRepositoryPortOut;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtenteUseCaseImpl implements UtenteUseCasePortIn {

    @Autowired
    private final UtenteRepositoryPortOut utenteRepository;

    @Override
    public Optional<UtenteRecord> findById(Integer id) {
        return utenteRepository.findById(id);
    }

    @Override
    public UtenteRecord save(UtenteRecord utente) {
        return utenteRepository.save(utente);
    }

    @Override
    public List<UtenteRecord> findAll() {
        return utenteRepository.findAll();
    }

    @Override
    public Boolean deleteById(Integer id) {
        return utenteRepository.deleteById(id);
    }

    @Override
    public Boolean updateById(Integer id, String nomeUtente, String nome, String cognome, String email, String password) {
        return utenteRepository.updateById(id, nomeUtente, nome, cognome, email, password);
    }

    @Override
    public Optional<UtenteRecord> findByNomeUtente(String nomeUtente) {
        return utenteRepository.findByNomeUtente(nomeUtente);
    }

    @Override
    public Optional<UtenteRecord> findByNomeUtenteAndPassword(String nomeUtente, String password) {
        return utenteRepository.findByNomeUtenteAndPassword(nomeUtente, password);
    }

    @Override
    public Optional<UtenteRecord> findByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }
}
