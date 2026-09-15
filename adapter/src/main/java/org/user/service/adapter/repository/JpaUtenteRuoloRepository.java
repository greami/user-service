package org.user.service.adapter.repository;

import org.user.service.adapter.repository.entity.UtenteRuoloEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaUtenteRuoloRepository extends JpaRepository<UtenteRuoloEntity, Long>
{
    Optional<UtenteRuoloEntity> findByIdUtenteAndIdRuolo(Long idUtente, Long idRuolo);
    List<UtenteRuoloEntity> findAllByIdUtente(Long idUtente);
}
