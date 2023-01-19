package com.teus.projectrpg.mapper;

import com.teus.projectrpg.dto.ArmorBodyLocalizationDto;
import com.teus.projectrpg.entity.armor.ArmorBodyLocalizationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArmorBodyLocalizationMapper {

    ArmorBodyLocalizationMapper INSTANCE = Mappers.getMapper(ArmorBodyLocalizationMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "bodyLocalization", target = "bodyLocalization")
    @Mapping(source = "armorPoints", target = "armorPoints")
    ArmorBodyLocalizationDto entityToDto(ArmorBodyLocalizationEntity armorBodyLocalization);

    ArmorBodyLocalizationEntity dtoToEntity(ArmorBodyLocalizationDto armorBodyLocalizationDto);

    List<ArmorBodyLocalizationDto> entitiesToDtos(List<ArmorBodyLocalizationEntity> armorBodyLocalizationEntities);

    List<ArmorBodyLocalizationEntity> dtosToEntities(List<ArmorBodyLocalizationDto> armorBodyLocalizationDtos);
}
