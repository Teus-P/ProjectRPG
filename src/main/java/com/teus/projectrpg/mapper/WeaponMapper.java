package com.teus.projectrpg.mapper;

import com.teus.projectrpg.dto.WeaponDto;
import com.teus.projectrpg.entity.weapon.WeaponEntity;
import com.teus.projectrpg.mapper.context.WeaponContext;
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
