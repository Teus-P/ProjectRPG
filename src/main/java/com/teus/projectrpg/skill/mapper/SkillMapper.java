package com.teus.projectrpg.skill.mapper;

import com.teus.projectrpg.skill.dto.SkillDto;
import com.teus.projectrpg.skill.entity.SkillEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillMapper {

    SkillDto toDto(SkillEntity entity);

    @IterableMapping(elementTargetType = SkillDto.class)
    List<SkillDto> toDtos(List<SkillEntity> entities);
}
