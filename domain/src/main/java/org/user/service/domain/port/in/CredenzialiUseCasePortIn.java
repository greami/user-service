package org.user.service.domain.port.in;

import org.user.service.domain.model.RuoloRecord;
import org.user.service.domain.model.UtenteRecord;
import org.user.service.domain.model.UtenteRuoloRecord;

import java.util.List;
import java.util.Optional;

public interface CredenzialiUseCasePortIn
{
    Boolean existsUtenteById(Integer id);
    Optional<UtenteRecord> findUtenteById(Integer id);
    Boolean saveUtente(UtenteRecord utente);
    List<UtenteRecord> findAll();
    Boolean deleteUtenteById(Integer id);
    Boolean updateUtenteById(Integer id, String nome, String cognome, String email, String password);

    List<UtenteRuoloRecord> getRuolofromUtente();
    Boolean saveRuoloUtente(UtenteRuoloRecord utenteRuoloRecord);
    boolean updateRuoloUtente(Integer id, Integer idRuolo);
    boolean deleteRuoloUtente(Integer id);

    List<UtenteRuoloRecord> getRuolifromUtente();
    Boolean saveRuoloUtente(RuoloRecord ruoloRecord);
    boolean updateRuolo(Integer id, String nome, String descrizione);
    boolean deleteRuolo(Integer id);

}


