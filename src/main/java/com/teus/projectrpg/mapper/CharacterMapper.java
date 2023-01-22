package com.teus.projectrpg.mapper;

import com.teus.projectrpg.dto.CharacterDto;
import com.teus.projectrpg.entity.character.CharacterEntity;
import com.teus.projectrpg.entity.note.NoteEntity;
import com.teus.projectrpg.mapper.context.CharacterContext;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CharacterMapper {


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

//    @IterableMapping(elementTargetType = CharacterDto.class)
//    List<CharacterDto> toDtos(List<CharacterEntity> characterEntities);
//
//    @IterableMapping(elementTargetType = CharacterEntity.class)
//    List<CharacterEntity> toEntities(List<CharacterDto> characterEntities);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "group", target = "group")
    @Mapping(source = "isRightHanded", target = "isRightHanded")
    @Mapping(source = "characteristics", target = "characteristics")
    @Mapping(source = "skills", target = "skills")
    @Mapping(source = "talents", target = "talents")
    @Mapping(source = "traits", target = "traits")
    @Mapping(source = "spells", target = "spells")
    @Mapping(source = "weapons", target = "weapons")
    @Mapping(source = "armors", target = "armors")
    @Mapping(source = "bodyLocalizations", target = "bodyLocalizations")
    @Mapping(source = "conditions", target = "conditions")
    @Mapping(source = "notes", target = "notes", qualifiedByName = "notesToStrings")
    CharacterDto toDto(CharacterEntity characterEntity);

    @Mapping(source = "notes", target = "notes", qualifiedByName = "stringsToNotes")
    CharacterEntity toEntity(CharacterDto characterDto, @Context CharacterContext context);
}
