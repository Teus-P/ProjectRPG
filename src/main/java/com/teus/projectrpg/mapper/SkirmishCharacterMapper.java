package com.teus.projectrpg.mapper;

import com.teus.projectrpg.dto.SkirmishCharacterDto;
import com.teus.projectrpg.entity.note.NoteEntity;
import com.teus.projectrpg.entity.skirmishcharacter.SkirmishCharacterEntity;
import com.teus.projectrpg.mapper.context.CharacterContext;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkirmishCharacterMapper {

    @Mapping(source = "notes", target = "notes", qualifiedByName = "notesToStrings")
    SkirmishCharacterDto toDto(SkirmishCharacterEntity entity, @Context CharacterContext context);

    @Mapping(source = "notes", target = "notes", qualifiedByName = "stringsToNotes")
    SkirmishCharacterEntity toEntity(SkirmishCharacterDto dto, @Context CharacterContext context);

    @IterableMapping(elementTargetType = SkirmishCharacterDto.class)
    List<SkirmishCharacterDto> toDtos(List<SkirmishCharacterEntity> entities, @Context CharacterContext context);

    @IterableMapping(elementTargetType = SkirmishCharacterEntity.class)
    List<SkirmishCharacterEntity> toEntities(List<SkirmishCharacterDto> dtos, @Context CharacterContext context);

    @Named("notesToStrings")
    static List<String> notesToStrings(List<NoteEntity> notes) {
        return notes.stream()
                .map(NoteEntity::getNote)
                .toList();
    }

    @Named("stringsToNotes")
    static List<NoteEntity> stringsToNotes(List<String> notes) {
        return notes.stream()
                .filter(note -> !note.isEmpty())
                .map(note -> {
                    NoteEntity noteEntity = new NoteEntity();
                    noteEntity.setNote(note);
                    return noteEntity;
                })
                .toList();
    }

}
