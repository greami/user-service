package org.user.service.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.UtenteRuoloResponse;
import org.user.service.adapter.repository.entity.UtenteRuoloEntity;
import org.user.service.domain.model.UtenteRuoloRecord;

@Mapper(componentModel = "spring")
public interface UtenteRuoloMapper {

    @Mapping(target = "idUtenteRuolo", source = "id")
    UtenteRuoloResponse toUtenteRuoloResponse(UtenteRuoloRecord utenteRuoloRecord);

    @Mapping(target = "id", source = "idUtenteRuolo")
    UtenteRuoloRecord toUtenteRuoloRecord(UtenteRuoloResponse utenteRuoloResponse);

    // MapStruct handles Long (record) → Integer (entity) numeric conversion automatically
    UtenteRuoloEntity toUtenteRuoloEntity(UtenteRuoloRecord utenteRuoloRecord);
}
