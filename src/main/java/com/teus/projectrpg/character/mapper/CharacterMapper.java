package com.teus.projectrpg.character.mapper;

import com.teus.projectrpg.character.dto.CharacterDto;
import com.teus.projectrpg.character.entity.CharacterEntity;
import java.util.List;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

	CharacterDto toDto(CharacterEntity entity, @Context CharacterContext context);

	CharacterEntity toEntity(CharacterDto dto, @Context CharacterContext context);

	@IterableMapping(elementTargetType = CharacterDto.class)
	List<CharacterDto> toDtos(List<CharacterEntity> entities, @Context CharacterContext context);

}
