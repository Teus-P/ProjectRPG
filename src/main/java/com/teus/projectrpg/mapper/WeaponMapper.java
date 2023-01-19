package com.teus.projectrpg.mapper;

import com.teus.projectrpg.dto.WeaponDto;
import com.teus.projectrpg.entity.weapon.WeaponEntity;
import com.teus.projectrpg.mapper.context.WeaponContext;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WeaponMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "nameTranslation", target = "nameTranslation")
    @Mapping(source = "weaponType", target = "weaponType")
    @Mapping(source = "weaponGroup", target = "weaponGroup")
    @Mapping(source = "weaponReach", target = "weaponReach")
    @Mapping(source = "weaponRange", target = "weaponRange")
    @Mapping(source = "isUsingStrength", target = "isUsingStrength")
    @Mapping(source = "isUsingStrengthInRange", target = "isUsingStrengthInRange")
    @Mapping(source = "damage", target = "damage")
    @Mapping(source = "weaponQualities", target = "weaponQualities")
    WeaponDto toDto(WeaponEntity weaponEntity);

    WeaponEntity toEntity(WeaponDto weaponDto, @Context WeaponContext weaponContext);

    @IterableMapping(elementTargetType = WeaponDto.class)
    List<WeaponDto> toDtos(List<WeaponEntity> weaponEntities);

    @IterableMapping(elementTargetType = WeaponEntity.class)
    List<WeaponEntity> toEntities(List<WeaponDto> weaponDtos);
}
