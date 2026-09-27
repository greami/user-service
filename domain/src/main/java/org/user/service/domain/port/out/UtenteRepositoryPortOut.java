package org.user.service.domain.port.out;

import org.user.service.domain.model.UtenteRecord;

import java.util.List;
import java.util.Optional;

public interface UtenteRepositoryPortOut {
    Optional<UtenteRecord> findById(Integer id);
    UtenteRecord save(UtenteRecord utente);
    List<UtenteRecord> findAll();
    List<UtenteRecord> findByNomeUtenteContainingIgnoreCase(String nomeUtente);
    Boolean deleteById(Integer id);
    Boolean updateById(Integer id, String nomeUtente, String nome, String cognome, String email, String password);
    Optional<UtenteRecord> findByNomeUtente(String nomeUtente);
    Optional<UtenteRecord> findByNomeUtenteAndPassword(String nomeUtente, String password);
    Optional<UtenteRecord> findByEmail(String email);
}
