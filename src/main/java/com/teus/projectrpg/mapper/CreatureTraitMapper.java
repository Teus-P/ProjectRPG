package com.teus.projectrpg.mapper;

import com.teus.projectrpg.dto.CreatureTraitDto;
import com.teus.projectrpg.entity.creaturetrait.CreatureTraitEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CreatureTraitMapper {

    CreatureTraitDto toDto(CreatureTraitEntity entity);

    @IterableMapping(elementTargetType = CreatureTraitDto.class)
    List<CreatureTraitDto> toDtos(List<CreatureTraitEntity> entities);
}
