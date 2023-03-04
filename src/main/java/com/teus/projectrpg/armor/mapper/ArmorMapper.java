package com.teus.projectrpg.armor.mapper;

import com.teus.projectrpg.armor.dto.ArmorDto;
import com.teus.projectrpg.armor.entity.ArmorEntity;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArmorMapper {

    ArmorDto toDto(ArmorEntity entity);

    ArmorEntity toEntity(ArmorDto dto, @Context ArmorContext context);

    @IterableMapping(elementTargetType = ArmorDto.class)
    List<ArmorDto> toDtos(List<ArmorEntity> entities);
}