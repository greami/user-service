package org.user.service.application.port.in.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.user.service.domain.model.UtenteRecord;
import org.user.service.domain.model.UtenteConRuoliRecord;
import org.user.service.domain.port.in.UtenteUseCasePortIn;
import org.user.service.domain.port.out.UtenteRepositoryPortOut;
import org.user.service.domain.port.out.RuoloRepositoryPortOut;
import org.user.service.domain.port.out.UtenteRuoloRepositoryPortOut;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtenteUseCaseImpl implements UtenteUseCasePortIn {

    @Autowired
    private final UtenteRepositoryPortOut utenteRepository;
    private final RuoloRepositoryPortOut ruoloRepository;
    private final UtenteRuoloRepositoryPortOut utenteRuoloRepository;

    @Override
    public Optional<UtenteRecord> findById(Integer id) {
        return utenteRepository.findById(id);
    }

    @Override
    public Optional<UtenteConRuoliRecord> findByIdWithRuoli(Integer id) {
        return utenteRepository.findById(id)
                .map(this::withRuoli);
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
    public List<UtenteRecord> findByNomeUtenteContainingIgnoreCase(String nomeUtente) {
        return utenteRepository.findByNomeUtenteContainingIgnoreCase(nomeUtente);
    }

    @Override
    public List<UtenteConRuoliRecord> findByNomeUtenteContainingIgnoreCaseWithRuoli(String nomeUtente) {
        return utenteRepository.findByNomeUtenteContainingIgnoreCase(nomeUtente).stream()
                .map(utente -> new UtenteConRuoliRecord(
                        utente,
                        utenteRuoloRepository.findIdRuoloByIdUtente(utente.id()).stream()
                                .map(ruoloRepository::findById)
                                .flatMap(Optional::stream)
                                .toList()
                ))
                .toList();
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
    public Optional<UtenteConRuoliRecord> findByNomeUtenteWithRuoli(String nomeUtente) {
        return utenteRepository.findByNomeUtente(nomeUtente)
                .map(this::withRuoli);
    }

    @Override
    public Optional<UtenteRecord> findByNomeUtenteAndPassword(String nomeUtente, String password) {
        return utenteRepository.findByNomeUtenteAndPassword(nomeUtente, password);
    }

    @Override
    public Optional<UtenteRecord> findByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }

    @Override
    public Optional<UtenteConRuoliRecord> findByEmailWithRuoli(String email) {
        return utenteRepository.findByEmail(email)
                .map(this::withRuoli);
    }

    private UtenteConRuoliRecord withRuoli(UtenteRecord utente) {
        return new UtenteConRuoliRecord(
                utente,
                utenteRuoloRepository.findIdRuoloByIdUtente(utente.id()).stream()
                        .map(ruoloRepository::findById)
                        .flatMap(Optional::stream)
                        .toList()
        );
    }
}
