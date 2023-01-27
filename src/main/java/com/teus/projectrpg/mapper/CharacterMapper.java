package com.teus.projectrpg.mapper;

import com.teus.projectrpg.dto.CharacterDto;
import com.teus.projectrpg.entity.character.CharacterEntity;
import com.teus.projectrpg.entity.note.NoteEntity;
import com.teus.projectrpg.mapper.context.CharacterContext;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    @Mapping(source = "notes", target = "notes", qualifiedByName = "notesToStrings")
    CharacterDto toDto(CharacterEntity entity, @Context CharacterContext context);

    @Mapping(source = "notes", target = "notes", qualifiedByName = "stringsToNotes")
    CharacterEntity toEntity(CharacterDto dto, @Context CharacterContext context);

    @IterableMapping(elementTargetType = CharacterDto.class)
    List<CharacterDto> toDtos(List<CharacterEntity> entities, @Context CharacterContext context);

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
