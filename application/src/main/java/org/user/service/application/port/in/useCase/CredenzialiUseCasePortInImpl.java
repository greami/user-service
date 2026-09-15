package org.user.service.application.port.in.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.user.service.domain.model.RuoloRecord;
import org.user.service.domain.model.UtenteRecord;
import org.user.service.domain.model.UtenteRuoloRecord;
import org.user.service.domain.port.in.CredenzialiUseCasePortIn;
import org.user.service.domain.port.out.CredenzialiRepositoryPortOut;

import java.util.List;
import java.util.Optional;

public class CredenzialiUseCasePortInImpl implements CredenzialiUseCasePortIn {

    @Autowired
    CredenzialiRepositoryPortOut credenzialiRepositoryPortOut;


    @Override
    public Boolean existsUtenteById(Integer id) {
        return credenzialiRepositoryPortOut.existsUtenteById(id);
    }

    @Override
    public Optional<UtenteRecord> findUtenteById(Integer id) {
        return credenzialiRepositoryPortOut.findUtenteById(id);
    }

    @Override
    public Boolean saveUtente(UtenteRecord utente) {
        return credenzialiRepositoryPortOut.saveUtente(utente);
    }

    @Override
    public List<UtenteRecord> findAll() {
        return credenzialiRepositoryPortOut.findAll();
    }

    @Override
    public Boolean deleteUtenteById(Integer id) {
        return credenzialiRepositoryPortOut.deleteUtenteById(id);
    }

    @Override
    public Boolean updateUtenteById(Integer id, String nome, String cognome, String email, String password) {
        return credenzialiRepositoryPortOut.updateUtenteById(id, nome, cognome, email, password);
    }

    @Override
    public List<UtenteRuoloRecord> getRuolofromUtente() {
        return credenzialiRepositoryPortOut.getRuolofromUtente();
    }

    @Override
    public Boolean saveRuoloUtente(UtenteRuoloRecord utenteRuoloRecord) {
        return credenzialiRepositoryPortOut.saveRuoloUtente(utenteRuoloRecord);
    }

    @Override
    public boolean updateRuoloUtente(Integer id, Integer idRuolo) {
        return credenzialiRepositoryPortOut.updateRuoloUtente(id, idRuolo);
    }

    @Override
    public boolean deleteRuoloUtente(Integer id) {
        return credenzialiRepositoryPortOut.deleteRuoloUtente(id);
    }

    @Override
    public List<UtenteRuoloRecord> getRuolifromUtente() {
        return credenzialiRepositoryPortOut.getRuolifromUtente();
    }

    @Override
    public Boolean saveRuoloUtente(RuoloRecord ruoloRecord) {
        return credenzialiRepositoryPortOut.saveRuoloUtente(ruoloRecord);
    }

    @Override
    public boolean updateRuolo(Integer id, String nome, String descrizione) {
        return credenzialiRepositoryPortOut.updateRuolo(id, nome, descrizione);
    }

    @Override
    public boolean deleteRuolo(Integer id) {
        return credenzialiRepositoryPortOut.deleteRuolo(id);
    }
}
