package com.teus.projectrpg.skirmish.mapper;

import com.teus.projectrpg.skirmish.dto.SkirmishGroupDto;
import com.teus.projectrpg.skirmish.entity.SkirmishGroupEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkirmishGroupMapper {
    SkirmishGroupDto toDto(SkirmishGroupEntity entity);

    @IterableMapping(elementTargetType = SkirmishGroupDto.class)
    List<SkirmishGroupDto> toDtos(List<SkirmishGroupEntity> entities);

    SkirmishGroupEntity toEntity(SkirmishGroupDto dto);

    void updateEntityFromDto(SkirmishGroupDto newSkirmishGroupDto, @MappingTarget SkirmishGroupEntity skirmishGroupEntity);
}
