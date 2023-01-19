package com.teus.projectrpg.mapper;

import com.teus.projectrpg.dto.WeaponQualityValueDto;
import com.teus.projectrpg.entity.weapon.WeaponQualityValueEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WeaponQualityValueMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "value", target = "value")
    @Mapping(source = "weaponQuality", target = "name")
    WeaponQualityValueDto toDto(WeaponQualityValueEntity qualityValue);

    WeaponQualityValueEntity toEntity(WeaponQualityValueDto qualityValueDto);
}
