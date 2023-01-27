package com.teus.projectrpg.mapper;

import com.teus.projectrpg.dto.ArmorDto;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.mapper.context.ArmorContext;
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