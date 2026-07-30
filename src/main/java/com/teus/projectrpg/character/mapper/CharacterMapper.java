package com.teus.projectrpg.character.mapper;

import com.teus.projectrpg.character.dto.CharacterDto;
import com.teus.projectrpg.character.entity.CharacterEntity;
import java.util.List;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
		componentModel = "spring",
		collectionMappingStrategy = org.mapstruct.CollectionMappingStrategy.TARGET_IMMUTABLE
)
public interface CharacterMapper {

	CharacterDto toDto(CharacterEntity entity, @Context CharacterContext context);

	@Mapping(target = "characteristics", ignore = true)
	@Mapping(target = "skills", ignore = true)
	@Mapping(target = "talents", ignore = true)
	@Mapping(target = "traits", ignore = true)
	@Mapping(target = "spells", ignore = true)
	@Mapping(target = "weapons", ignore = true)
	@Mapping(target = "armors", ignore = true)
	@Mapping(target = "bodyLocalizations", ignore = true)
	@Mapping(target = "conditions", ignore = true)
	@Mapping(target = "notes", ignore = true)
	CharacterEntity toEntity(CharacterDto dto, @Context CharacterContext context);

	@IterableMapping(elementTargetType = CharacterDto.class)
	List<CharacterDto> toDtos(List<CharacterEntity> entities, @Context CharacterContext context);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "characteristics", ignore = true)
	@Mapping(target = "skills", ignore = true)
	@Mapping(target = "talents", ignore = true)
	@Mapping(target = "traits", ignore = true)
	@Mapping(target = "spells", ignore = true)
	@Mapping(target = "weapons", ignore = true)
	@Mapping(target = "armors", ignore = true)
	@Mapping(target = "bodyLocalizations", ignore = true)
	@Mapping(target = "conditions", ignore = true)
	@Mapping(target = "notes", ignore = true)
	void updateEntityFromDto(CharacterDto dto, @MappingTarget CharacterEntity entity, @Context CharacterContext context);
}
