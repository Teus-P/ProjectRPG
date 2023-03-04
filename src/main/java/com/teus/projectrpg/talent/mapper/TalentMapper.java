package com.teus.projectrpg.talent.mapper;

import com.teus.projectrpg.talent.dto.TalentDto;
import com.teus.projectrpg.talent.entity.TalentEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TalentMapper {

    TalentDto toDto(TalentEntity entity);

    @IterableMapping(elementTargetType = TalentDto.class)
    List<TalentDto> toDtos(List<TalentEntity> entities);
}
