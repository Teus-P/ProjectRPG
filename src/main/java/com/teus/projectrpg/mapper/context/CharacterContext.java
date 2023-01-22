package com.teus.projectrpg.mapper.context;

import com.teus.projectrpg.entity.character.*;
import com.teus.projectrpg.entity.condition.CharacterConditionEntity;
import com.teus.projectrpg.entity.note.NoteEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

import java.util.List;

public class CharacterContext {

    @AfterMapping
    public void setCharacterParameters(@MappingTarget CharacterEntity character) {
        setCharacteristics(character);
        setSkills(character);
        setTalents(character);
        setTraits(character);
        setWeapons(character);
        setBodyLocalizations(character);
        setConditions(character);
        setNotes(character);
    }

    private static void setCharacteristics(CharacterEntity character) {
        List<CharacterCharacteristicEntity> characteristics = character.getCharacteristics().stream()
                .peek(element -> element.setCharacter(character))
                .toList();
        character.setCharacteristics(characteristics);
    }

    private static void setSkills(CharacterEntity character) {
        List<CharacterSkillEntity> skills = character.getSkills().stream()
                .peek(element -> element.setCharacter(character))
                .toList();
        character.setSkills(skills);
    }

    private static void setTalents(CharacterEntity character) {
        List<CharacterTalentEntity> talents = character.getTalents().stream()
                .peek(element -> element.setCharacter(character))
                .toList();
        character.setTalents(talents);
    }

    private static void setTraits(CharacterEntity character) {
        List<CharacterCreatureTraitEntity> traits = character.getTraits().stream()
                .peek(element -> element.setCharacter(character))
                .toList();
        character.setTraits(traits);
    }

    private static void setWeapons(CharacterEntity character) {
        List<CharacterWeaponEntity> weapons = character.getWeapons().stream()
                .peek(element -> element.setCharacter(character))
                .toList();
        character.setWeapons(weapons);
    }

    private static void setBodyLocalizations(CharacterEntity character) {
        List<CharacterBodyLocalizationEntity> bodyLocalizations = character.getBodyLocalizations().stream()
                .peek(element -> element.setCharacter(character))
                .toList();
        character.setBodyLocalizations(bodyLocalizations);
    }

    private static void setConditions(CharacterEntity character) {
        List<CharacterConditionEntity> conditions = character.getConditions().stream()
                .peek(element -> element.setCharacter(character))
                .toList();
        character.setConditions(conditions);
    }

    private void setNotes(CharacterEntity character) {
        List<NoteEntity> notes = character.getNotes().stream()
                .peek(note -> note.setCharacter(character))
                .toList();

        character.setNotes(notes);
    }
}
