package org.user.service.adapter.repository;

import org.user.service.adapter.repository.entity.UtenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUtenteRepository extends JpaRepository<UtenteEntity, Integer>
{
    Optional<UtenteEntity> findByNomeUtente(String nomeUtente);

    Optional<UtenteEntity> findByNomeUtenteAndPassword(String nomeUtente, String password);

    Optional<UtenteEntity> findByEmail(String email);
}
