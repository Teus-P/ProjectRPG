package com.teus.projectrpg.spell.mapper;

import com.teus.projectrpg.spell.dto.SpellDto;
import com.teus.projectrpg.spell.entity.SpellEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpellMapper {

    SpellDto toDto(SpellEntity entity);

    @IterableMapping(elementTargetType = SpellDto.class)
    List<SpellDto> toDtos(List<SpellEntity> entities);
}
