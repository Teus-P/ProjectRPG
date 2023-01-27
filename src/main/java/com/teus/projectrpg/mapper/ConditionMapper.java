package com.teus.projectrpg.mapper;

import com.teus.projectrpg.dto.ConditionDto;
import com.teus.projectrpg.entity.condition.ConditionEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConditionMapper {

    ConditionDto toDto(ConditionEntity entity);

    @IterableMapping(elementTargetType = ConditionDto.class)
    List<ConditionDto> toDtos(List<ConditionEntity> entities);
}
