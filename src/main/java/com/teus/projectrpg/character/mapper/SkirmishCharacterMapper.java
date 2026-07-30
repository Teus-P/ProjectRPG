package com.teus.projectrpg.character.mapper;

import com.teus.projectrpg.character.dto.SkirmishCharacterDto;
import com.teus.projectrpg.character.entity.SkirmishCharacterEntity;
import java.util.List;

import org.mapstruct.*;

@Mapper(componentModel = "spring",
		collectionMappingStrategy = org.mapstruct.CollectionMappingStrategy.TARGET_IMMUTABLE,
		uses = CharacterMapper.class
)
public interface SkirmishCharacterMapper {

	SkirmishCharacterDto toDto(SkirmishCharacterEntity entity, @Context CharacterContext context);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "character", ignore = true)
	SkirmishCharacterEntity toEntity(SkirmishCharacterDto dto, @Context CharacterContext context);

	@IterableMapping(elementTargetType = SkirmishCharacterDto.class)
	List<SkirmishCharacterDto> toDtos(List<SkirmishCharacterEntity> entities, @Context CharacterContext context);

	@IterableMapping(elementTargetType = SkirmishCharacterEntity.class)
	List<SkirmishCharacterEntity> toEntities(List<SkirmishCharacterDto> dtos, @Context CharacterContext context);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "character", ignore = true)
	void updateEntityFromDto(SkirmishCharacterDto dto, @MappingTarget SkirmishCharacterEntity entity, @Context CharacterContext context);
}
