package org.user.service.domain.port.in;

import org.user.service.domain.model.UtenteRecord;
import org.user.service.domain.model.UtenteConRuoliRecord;

import java.util.List;
import java.util.Optional;

public interface UtenteUseCasePortIn {
    Optional<UtenteRecord> findById(Integer id);
    Optional<UtenteConRuoliRecord> findByIdWithRuoli(Integer id);
    UtenteRecord save(UtenteRecord utente);
    List<UtenteRecord> findAll();
    List<UtenteRecord> findByNomeUtenteContainingIgnoreCase(String nomeUtente);
    List<UtenteConRuoliRecord> findByNomeUtenteContainingIgnoreCaseWithRuoli(String nomeUtente);
    Boolean deleteById(Integer id);
    Boolean updateById(Integer id, String nomeUtente, String nome, String cognome, String email, String password);
    Optional<UtenteRecord> findByNomeUtente(String nomeUtente);
    Optional<UtenteConRuoliRecord> findByNomeUtenteWithRuoli(String nomeUtente);
    Optional<UtenteRecord> findByNomeUtenteAndPassword(String nomeUtente, String password);
    Optional<UtenteRecord> findByEmail(String email);
    Optional<UtenteConRuoliRecord> findByEmailWithRuoli(String email);
}
