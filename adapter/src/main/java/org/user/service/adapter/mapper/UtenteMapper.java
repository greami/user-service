package org.user.service.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.UtenteResponse;
import org.user.service.adapter.repository.entity.UtenteEntity;
import org.user.service.domain.model.UtenteRecord;

@Mapper(componentModel = "spring")
public interface UtenteMapper {

    @Mapping(target = "idUtente", source = "id")
    UtenteResponse toUtenteResponse(UtenteRecord utenteRecord);

    @Mapping(target = "id", source = "idUtente")
    UtenteRecord toUtenteRecord(UtenteResponse utenteResponse);

    // MapStruct handles Long (record) → Integer (entity) numeric conversion automatically
    UtenteEntity toUtenteEntity(UtenteRecord utenteRecord);
}
