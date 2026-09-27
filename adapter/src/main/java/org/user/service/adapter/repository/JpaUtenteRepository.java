package org.user.service.adapter.repository;

import org.user.service.adapter.repository.entity.UtenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface JpaUtenteRepository extends JpaRepository<UtenteEntity, Integer>
{
    Optional<UtenteEntity> findByNomeUtente(String nomeUtente);

    List<UtenteEntity> findByNomeUtenteContainingIgnoreCase(String nomeUtente);

    Optional<UtenteEntity> findByNomeUtenteAndPassword(String nomeUtente, String password);

    Optional<UtenteEntity> findByEmail(String email);
}
