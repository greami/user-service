package org.user.service.adapter.port.out;

import org.user.service.adapter.repository.JpaRuoloRepository;
import org.user.service.adapter.repository.JpaUtenteRuoloRepository;
import org.user.service.adapter.repository.JpaUtenteRepository;
import org.user.service.domain.model.RuoloRecord;
import org.user.service.domain.model.UtenteRecord;
import org.user.service.domain.model.UtenteRuoloRecord;
import org.user.service.domain.port.out.CredenzialiRepositoryPortOut;

import java.util.List;
import java.util.Optional;

public class CredenzialiRepositoryPortOutImpl implements CredenzialiRepositoryPortOut
{
    private final JpaRuoloRepository jpaRuoloRepository;
    private final JpaUtenteRuoloRepository jpaUtenteRuoloRepository;
    private final JpaUtenteRepository jpaUtenteRepository;

    public CredenzialiRepositoryPortOutImpl(JpaRuoloRepository jpaRuoloRepository, JpaUtenteRuoloRepository jpaUtenteRuoloRepository, JpaUtenteRepository jpaUtenteRepository) {
        this.jpaRuoloRepository = jpaRuoloRepository;
        this.jpaUtenteRuoloRepository = jpaUtenteRuoloRepository;
        this.jpaUtenteRepository = jpaUtenteRepository;
    }


    @Override
    public Boolean existsUtenteById(Integer id) {
        return null; //jpaUtenteRepository.existsById(id);
    }

    @Override
    public Optional<UtenteRecord> findUtenteById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Boolean saveUtente(UtenteRecord utente) {
        return null;
    }

    @Override
    public List<UtenteRecord> findAll() {
        return List.of();
    }

    @Override
    public Boolean deleteUtenteById(Integer id) {
        return null;
    }

    @Override
    public Boolean updateUtenteById(Integer id, String nome, String cognome, String email, String password) {
        return null;
    }

    @Override
    public List<UtenteRuoloRecord> getRuolofromUtente() {
        return List.of();
    }

    @Override
    public Boolean saveRuoloUtente(UtenteRuoloRecord utenteRuoloRecord) {
        return null;
    }

    @Override
    public boolean updateRuoloUtente(Integer id, Integer idRuolo) {
        return false;
    }

    @Override
    public boolean deleteRuoloUtente(Integer id) {
        return false;
    }

    @Override
    public List<UtenteRuoloRecord> getRuolifromUtente() {
        return List.of();
    }

    @Override
    public Boolean saveRuoloUtente(RuoloRecord ruoloRecord) {
        return null;
    }

    @Override
    public boolean updateRuolo(Integer id, String nome, String descrizione) {
        return false;
    }

    @Override
    public boolean deleteRuolo(Integer id) {
        return false;
    }
}
