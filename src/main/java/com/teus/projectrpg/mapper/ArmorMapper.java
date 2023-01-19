package com.teus.projectrpg.mapper;

import com.teus.projectrpg.dto.ArmorDto;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.mapper.context.ArmorContext;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArmorMapper {

    ArmorMapper INSTANCE = Mappers.getMapper(ArmorMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "nameTranslation", target = "nameTranslation")
    @Mapping(source = "armorCategory", target = "armorCategory")
    @Mapping(source = "armorBodyLocalizations", target = "armorBodyLocalizations")
    @Mapping(source = "armorPenalties", target = "armorPenalties")
    @Mapping(source = "armorQualities", target = "armorQualities")
    ArmorDto toDto(ArmorEntity armorEntity);

    ArmorEntity toEntity(ArmorDto armorDto, @Context ArmorContext armorContext);

    @IterableMapping(elementTargetType = ArmorDto.class)
    List<ArmorDto> toDtos(List<ArmorEntity> armorEntities);

    @IterableMapping(elementTargetType = ArmorEntity.class)
    List<ArmorEntity> toEntities(List<ArmorDto> armorDtos);
}