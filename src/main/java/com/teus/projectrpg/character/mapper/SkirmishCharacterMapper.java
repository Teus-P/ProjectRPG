package com.teus.projectrpg.character.mapper;

import com.teus.projectrpg.character.dto.SkirmishCharacterDto;
import com.teus.projectrpg.character.entity.SkirmishCharacterEntity;
import java.util.List;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CharacterMapper.class)
public interface SkirmishCharacterMapper {

	SkirmishCharacterDto toDto(SkirmishCharacterEntity entity, @Context CharacterContext context);

	SkirmishCharacterEntity toEntity(SkirmishCharacterDto dto, @Context CharacterContext context);

	@IterableMapping(elementTargetType = SkirmishCharacterDto.class)
	List<SkirmishCharacterDto> toDtos(List<SkirmishCharacterEntity> entities, @Context CharacterContext context);

	@IterableMapping(elementTargetType = SkirmishCharacterEntity.class)
	List<SkirmishCharacterEntity> toEntities(List<SkirmishCharacterDto> dtos, @Context CharacterContext context);

}
