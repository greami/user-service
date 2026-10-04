package org.user.service.domain.model;

import java.util.List;

public record UtenteConRuoliRecord(UtenteRecord utente, List<RuoloRecord> ruoli) {
}
