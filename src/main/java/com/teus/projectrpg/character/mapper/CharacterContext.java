package com.teus.projectrpg.character.mapper;

import com.teus.projectrpg.armor.entity.CharacterArmorBodyLocalizationEntity;
import com.teus.projectrpg.armor.service.armor.ArmorService;
import com.teus.projectrpg.bodylocalization.service.BodyLocalizationService;
import com.teus.projectrpg.character.dto.CharacterBodyLocalizationDto;
import com.teus.projectrpg.character.dto.CharacterDto;
import com.teus.projectrpg.character.entity.*;
import com.teus.projectrpg.characteristic.service.CharacteristicService;
import com.teus.projectrpg.condition.service.ConditionService;
import com.teus.projectrpg.creaturetrait.service.CreatureTraitService;
import com.teus.projectrpg.injury.service.InjuryService;
import com.teus.projectrpg.skill.service.SkillService;
import com.teus.projectrpg.spell.entity.SpellEntity;
import com.teus.projectrpg.spell.service.SpellService;
import com.teus.projectrpg.talent.service.TalentService;
import com.teus.projectrpg.weapon.service.weapon.WeaponService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterContext {

	private final CharacteristicService characteristicService;
	private final SkillService skillService;
	private final TalentService talentService;
	private final SpellService spellService;
	private final CreatureTraitService creatureTraitService;
	private final WeaponService weaponService;
	private final ArmorService armorService;
	private final BodyLocalizationService bodyLocalizationService;
	private final InjuryService injuryService;
	private final ConditionService conditionService;

	@AfterMapping
	public void setCharacterDtoParameters(@MappingTarget CharacterDto characterDto) {
		characterDto.setBodyLocalizations(this.setBodyLocalizations(characterDto));
	}

	protected List<CharacterBodyLocalizationDto> setBodyLocalizations(CharacterDto character) {
		return character.getBodyLocalizations().stream().peek(element -> element.setCharacterId(character.getId())).toList();

	}

	@AfterMapping
	public void setCharacterEntityParameters(@MappingTarget CharacterEntity character) {
		setCharacteristics(character);
		setSkills(character);
		setTalents(character);
		setTraits(character);
		setSpells(character);
		setWeapons(character);
		setArmor(character);
		setBodyLocalizations(character);
		setConditions(character);
		setNotes(character);
	}

	protected void setCharacteristics(CharacterEntity character) {
		List<CharacterCharacteristicEntity> characteristics = character.getCharacteristics().stream().peek(element -> {
			element.setCharacter(character);
			element.setCharacteristic(characteristicService.findByName(element.getCharacteristic().getName()));
		}).toList();
		character.setCharacteristics(characteristics);
	}

	protected void setSkills(CharacterEntity character) {
		List<CharacterSkillEntity> skills = character.getSkills().stream().peek(element -> {
			element.setCharacter(character);
			element.setModel(skillService.findByName(element.getModel().getName()));
		}).toList();
		character.setSkills(skills);
	}

	protected void setTalents(CharacterEntity character) {
		List<CharacterTalentEntity> talents = character.getTalents().stream().peek(element -> {
			element.setCharacter(character);
			element.setModel(talentService.findByName(element.getModel().getName()));
		}).toList();
		character.setTalents(talents);
	}

	protected void setTraits(CharacterEntity character) {
		List<CharacterCreatureTraitEntity> traits = character.getTraits().stream().peek(element -> {
			element.setCharacter(character);
			element.setModel(creatureTraitService.findByName(element.getModel().getName()));
		}).toList();
		character.setTraits(traits);
	}

	protected void setSpells(CharacterEntity character) {
		List<SpellEntity> spells = character.getSpells().stream().map(element -> spellService.findByName(element.getName())).toList();
		character.setSpells(spells);
	}

	protected void setWeapons(CharacterEntity character) {
		List<CharacterWeaponEntity> weapons = character.getWeapons().stream().peek(element -> {
			element.setCharacter(character);
			element.setWeapon(weaponService.findByName(element.getWeapon().getName()));
		}).toList();
		character.setWeapons(weapons);
	}

	protected void setArmor(CharacterEntity character) {
		List<CharacterArmorEntity> armors = character.getArmors().stream().peek(element -> {
			element.setCharacter(character);
			element.setArmor(armorService.findByName(element.getArmor().getName()));
			List<CharacterArmorBodyLocalizationEntity> list = element.getArmorBodyLocalizations().stream()
					.peek(armorBodyLocalization -> armorBodyLocalization.setCharacterArmor(element)).toList();
			element.setArmorBodyLocalizations(list);
		}).toList();
		character.setArmors(armors);
	}

	protected void setBodyLocalizations(CharacterEntity character) {
		List<CharacterBodyLocalizationEntity> bodyLocalizations = character.getBodyLocalizations().stream().peek(element -> {
			element.setCharacter(character);
			element.setBodyLocalization(bodyLocalizationService.findByName(element.getBodyLocalization().getName()));
			this.setInjuries(element);
		}).toList();
		character.setBodyLocalizations(bodyLocalizations);
	}

	protected void setInjuries(CharacterBodyLocalizationEntity bodyLocalization) {
		List<CharacterBodyLocalizationInjuryEntity> injuries = bodyLocalization.getInjuries().stream().peek(element -> {
			element.setCharacterBodyLocalization(bodyLocalization);
			element.setModel(injuryService.findByName(element.getModel().getName()));
		}).toList();

		bodyLocalization.setInjuries(injuries);
	}

	protected void setConditions(CharacterEntity character) {
		List<CharacterConditionEntity> conditions = character.getConditions().stream().peek(element -> {
			element.setCharacter(character);
			element.setCondition(conditionService.findByName(element.getCondition().getName()));
		}).toList();
		character.setConditions(conditions);
	}

	protected void setNotes(CharacterEntity character) {
		List<NoteEntity> notes = character.getNotes().stream().peek(note -> note.setCharacter(character)).toList();

		character.setNotes(notes);
	}

}
