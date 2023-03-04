package com.teus.projectrpg.condition.mapper;

import com.teus.projectrpg.condition.dto.ConditionDto;
import com.teus.projectrpg.condition.entity.ConditionEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConditionMapper {

    ConditionDto toDto(ConditionEntity entity);

    @IterableMapping(elementTargetType = ConditionDto.class)
    List<ConditionDto> toDtos(List<ConditionEntity> entities);
}
