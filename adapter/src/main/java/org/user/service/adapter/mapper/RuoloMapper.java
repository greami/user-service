package org.user.service.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.RuoloResponse;
import org.user.service.adapter.repository.entity.RuoloEntity;
import org.user.service.domain.model.RuoloRecord;

@Mapper(componentModel = "spring")
public interface RuoloMapper {

    @Mapping(target = "idRuolo", source = "id")
    RuoloResponse toRuoloResponse(RuoloRecord ruoloRecord);

    @Mapping(target = "id", source = "idRuolo")
    RuoloRecord toRuoloRecord(RuoloResponse ruoloResponse);

    RuoloEntity toRuoloEntity(RuoloRecord ruoloRecord);
}
