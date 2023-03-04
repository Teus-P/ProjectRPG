package com.teus.projectrpg.weapon.mapper;

import com.teus.projectrpg.weapon.dto.WeaponDto;
import com.teus.projectrpg.weapon.entity.WeaponEntity;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WeaponMapper {

    WeaponDto toDto(WeaponEntity entity);

    WeaponEntity toEntity(WeaponDto dto, @Context WeaponContext context);

    @IterableMapping(elementTargetType = WeaponDto.class)
    List<WeaponDto> toDtos(List<WeaponEntity> entities);
}
