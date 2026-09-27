package org.user.service.adapter.port.out;

import org.springframework.stereotype.Repository;
import org.user.service.adapter.mapper.UtenteMapper;
import org.user.service.adapter.repository.JpaUtenteRepository;
import org.user.service.adapter.repository.entity.UtenteEntity;
import org.user.service.domain.model.UtenteRecord;
import org.user.service.domain.port.out.UtenteRepositoryPortOut;

import java.util.List;
import java.util.Optional;

@Repository
public class UtenteRepositoryPortOutImpl implements UtenteRepositoryPortOut {

    private final JpaUtenteRepository jpaUtenteRepository;
    private final UtenteMapper utenteMapper;

    public UtenteRepositoryPortOutImpl(JpaUtenteRepository jpaUtenteRepository, UtenteMapper utenteMapper) {
        this.jpaUtenteRepository = jpaUtenteRepository;
        this.utenteMapper = utenteMapper;
    }

    @Override
    public Optional<UtenteRecord> findById(Integer id) {
        try {
            return jpaUtenteRepository.findById(id)
                    .map(entity -> new UtenteRecord(
                            entity.getId(),
                            entity.getNomeUtente(),
                            entity.getNome(),
                            entity.getCognome(),
                            entity.getEmail(),
                            entity.getPassword()
                    ));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public UtenteRecord save(UtenteRecord utente) {
        try {
            UtenteEntity saved = jpaUtenteRepository.save(utenteMapper.toUtenteEntity(utente));
            return new UtenteRecord(
                    saved.getId(),
                    saved.getNomeUtente(),
                    saved.getNome(),
                    saved.getCognome(),
                    saved.getEmail(),
                    saved.getPassword()
            );
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<UtenteRecord> findAll() {
        try {
            return jpaUtenteRepository.findAll().stream()
                    .map(entity -> new UtenteRecord(
                            entity.getId(),
                            entity.getNomeUtente(),
                            entity.getNome(),
                            entity.getCognome(),
                            entity.getEmail(),
                            entity.getPassword()
                    ))
                    .toList();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<UtenteRecord> findByNomeUtenteContainingIgnoreCase(String nomeUtente) {
        try {
            return jpaUtenteRepository.findByNomeUtenteContainingIgnoreCase(nomeUtente).stream()
                    .map(entity -> new UtenteRecord(
                            entity.getId(),
                            entity.getNomeUtente(),
                            entity.getNome(),
                            entity.getCognome(),
                            entity.getEmail(),
                            entity.getPassword()
                    ))
                    .toList();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Boolean deleteById(Integer id) {
        try {
            if (!jpaUtenteRepository.existsById(id)) {
                return false;
            }
            jpaUtenteRepository.deleteById(id);
            return true;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Boolean updateById(Integer id, String nomeUtente, String nome, String cognome, String email, String password) {
        try {
            return jpaUtenteRepository.findById(id)
                    .map(existing -> {
                        UtenteEntity updated = new UtenteEntity(
                                existing.getId(),
                                nomeUtente,
                                nome,
                                cognome,
                                email,
                                password
                        );
                        return jpaUtenteRepository.save(updated) != null;
                    })
                    .orElse(false);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Optional<UtenteRecord> findByNomeUtente(String nomeUtente) {
        try {
            return jpaUtenteRepository.findByNomeUtente(nomeUtente)
                    .map(entity -> new UtenteRecord(
                            entity.getId(),
                            entity.getNomeUtente(),
                            entity.getNome(),
                            entity.getCognome(),
                            entity.getEmail(),
                            entity.getPassword()
                    ));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UtenteRecord> findByNomeUtenteAndPassword(String nomeUtente, String password) {
        try {
            return jpaUtenteRepository.findByNomeUtenteAndPassword(nomeUtente, password)
                    .map(entity -> new UtenteRecord(
                            entity.getId(),
                            entity.getNomeUtente(),
                            entity.getNome(),
                            entity.getCognome(),
                            entity.getEmail(),
                            entity.getPassword()
                    ));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UtenteRecord> findByEmail(String email) {
        try {
            return jpaUtenteRepository.findByEmail(email)
                    .map(entity -> new UtenteRecord(
                            entity.getId(),
                            entity.getNomeUtente(),
                            entity.getNome(),
                            entity.getCognome(),
                            entity.getEmail(),
                            entity.getPassword()
                    ));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
