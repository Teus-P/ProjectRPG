package com.teus.projectrpg.armor.mapper;

import com.teus.projectrpg.armor.dto.ArmorDto;
import com.teus.projectrpg.armor.entity.ArmorEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArmorMapper {

    ArmorDto toDto(ArmorEntity entity);

    ArmorEntity toEntity(ArmorDto dto);

    @IterableMapping(elementTargetType = ArmorDto.class)
    List<ArmorDto> toDtos(List<ArmorEntity> entities);

    void updateEntityFromDto(ArmorDto dto, @MappingTarget ArmorEntity entity);
}