package com.teus.projectrpg.character.mapper;

import com.teus.projectrpg.armor.entity.CharacterArmorBodyLocalizationEntity;
import com.teus.projectrpg.armor.service.armor.ArmorService;
import com.teus.projectrpg.bodylocalization.service.BodyLocalizationService;
import com.teus.projectrpg.character.dto.CharacterArmorDto;
import com.teus.projectrpg.character.dto.CharacterBodyLocalizationDto;
import com.teus.projectrpg.character.dto.CharacterDto;
import com.teus.projectrpg.character.entity.CharacterArmorEntity;
import com.teus.projectrpg.character.entity.CharacterBodyLocalizationEntity;
import com.teus.projectrpg.character.entity.CharacterBodyLocalizationInjuryEntity;
import com.teus.projectrpg.character.entity.CharacterCharacteristicEntity;
import com.teus.projectrpg.character.entity.CharacterConditionEntity;
import com.teus.projectrpg.character.entity.CharacterCreatureTraitEntity;
import com.teus.projectrpg.character.entity.CharacterEntity;
import com.teus.projectrpg.character.entity.CharacterSkillEntity;
import com.teus.projectrpg.character.entity.CharacterTalentEntity;
import com.teus.projectrpg.character.entity.CharacterWeaponEntity;
import com.teus.projectrpg.character.dto.SkirmishCharacterDto;
import com.teus.projectrpg.character.entity.NoteEntity;
import com.teus.projectrpg.character.entity.SkirmishCharacterEntity;
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
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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
		characterDto.setBodyLocalizations(enrichBodyLocalizations(characterDto));
	}

	private List<CharacterBodyLocalizationDto> enrichBodyLocalizations(CharacterDto character) {
		return character.getBodyLocalizations().stream()
				.peek(element -> element.setCharacterId(character.getId()))
				.toList();
	}

	@AfterMapping
	public void setCharacterEntityParameters(CharacterDto dto, @MappingTarget CharacterEntity character) {
		setCharacteristics(dto, character);
		setSkills(dto, character);
		setTalents(dto, character);
		setTraits(dto, character);
		setSpells(dto, character);
		setWeapons(dto, character);
		setArmor(dto, character);
		setBodyLocalizations(dto, character);
		setConditions(dto, character);
		setNotes(dto, character);
	}

	@AfterMapping
	public void setSkirmishCharacterEntityParameters(SkirmishCharacterDto dto, @MappingTarget SkirmishCharacterEntity skirmishCharacter) {
		CharacterEntity character = skirmishCharacter.getCharacter();
		if (character == null) {
			character = new CharacterEntity();
			skirmishCharacter.setCharacter(character);
		}
		CharacterDto characterDto = dto.getCharacter();
		character.setName(characterDto.getName());
		character.setDescription(characterDto.getDescription());
		character.setGroupType(characterDto.getGroupType());
		character.setGroup(characterDto.getGroup());
		character.setStatus(characterDto.getStatus());
		character.setIsRightHanded(characterDto.getIsRightHanded());
		character.setType(characterDto.getType());
		setCharacterEntityParameters(characterDto, character);
	}

	protected void setCharacteristics(CharacterDto dto, CharacterEntity character) {
		Map<Long, CharacterCharacteristicEntity> existingById = toIdMap(character.getCharacteristics(), CharacterCharacteristicEntity::getId);
		List<CharacterCharacteristicEntity> merged = dto.getCharacteristics().stream().map(d -> {
			CharacterCharacteristicEntity e = getOrCreate(existingById, d.getId(), CharacterCharacteristicEntity::new);
			e.setCharacter(character);
			e.setCharacteristic(characteristicService.findByName(d.getCharacteristic().getName()));
			e.setValue(d.getValue());
			return e;
		}).toList();
		replaceCollection(character.getCharacteristics(), merged);
	}

	protected void setSkills(CharacterDto dto, CharacterEntity character) {
		Map<Long, CharacterSkillEntity> existingById = toIdMap(character.getSkills(), CharacterSkillEntity::getId);
		List<CharacterSkillEntity> merged = dto.getSkills().stream().map(d -> {
			CharacterSkillEntity e = getOrCreate(existingById, d.getId(), CharacterSkillEntity::new);
			e.setCharacter(character);
			e.setModel(skillService.findByName(d.getModel().getName()));
			e.setValue(d.getValue());
			e.setSpecialisation(d.getSpecialisation());
			return e;
		}).toList();
		replaceCollection(character.getSkills(), merged);
	}

	protected void setTalents(CharacterDto dto, CharacterEntity character) {
		Map<Long, CharacterTalentEntity> existingById = toIdMap(character.getTalents(), CharacterTalentEntity::getId);
		List<CharacterTalentEntity> merged = dto.getTalents().stream().map(d -> {
			CharacterTalentEntity e = getOrCreate(existingById, d.getId(), CharacterTalentEntity::new);
			e.setCharacter(character);
			e.setModel(talentService.findByName(d.getModel().getName()));
			e.setValue(d.getValue());
			e.setSpecialisation(d.getSpecialisation());
			return e;
		}).toList();
		replaceCollection(character.getTalents(), merged);
	}

	protected void setTraits(CharacterDto dto, CharacterEntity character) {
		Map<Long, CharacterCreatureTraitEntity> existingById = toIdMap(character.getTraits(), CharacterCreatureTraitEntity::getId);
		List<CharacterCreatureTraitEntity> merged = dto.getTraits().stream().map(d -> {
			CharacterCreatureTraitEntity e = getOrCreate(existingById, d.getId(), CharacterCreatureTraitEntity::new);
			e.setCharacter(character);
			e.setModel(creatureTraitService.findByName(d.getModel().getName()));
			e.setValue(d.getValue());
			return e;
		}).toList();
		replaceCollection(character.getTraits(), merged);
	}

	protected void setSpells(CharacterDto dto, CharacterEntity character) {
		List<SpellEntity> spells = dto.getSpells().stream()
				.map(s -> spellService.findByName(s.getName()))
				.toList();
		character.setSpells(spells);
	}

	protected void setWeapons(CharacterDto dto, CharacterEntity character) {
		Map<Long, CharacterWeaponEntity> existingById = toIdMap(character.getWeapons(), CharacterWeaponEntity::getId);
		List<CharacterWeaponEntity> merged = dto.getWeapons().stream().map(d -> {
			CharacterWeaponEntity e = getOrCreate(existingById, d.getId(), CharacterWeaponEntity::new);
			e.setCharacter(character);
			e.setWeapon(weaponService.findByName(d.getWeapon().getName()));
			e.setValue(d.getValue());
			return e;
		}).toList();
		replaceCollection(character.getWeapons(), merged);
	}

	protected void setArmor(CharacterDto dto, CharacterEntity character) {
		Map<Long, CharacterArmorEntity> existingById = toIdMap(character.getArmors(), CharacterArmorEntity::getId);
		List<CharacterArmorEntity> merged = dto.getArmors().stream().map(d -> {
			CharacterArmorEntity e = getOrCreate(existingById, d.getId(), CharacterArmorEntity::new);
			e.setCharacter(character);
			e.setArmor(armorService.findByName(d.getArmor().getName()));
			e.setDuration(d.getDuration());
			setArmorBodyLocalizations(d, e);
			return e;
		}).toList();
		replaceCollection(character.getArmors(), merged);
	}

	private void setArmorBodyLocalizations(CharacterArmorDto dto, CharacterArmorEntity armor) {
		Map<Long, CharacterArmorBodyLocalizationEntity> existingById = toIdMap(armor.getArmorBodyLocalizations(), CharacterArmorBodyLocalizationEntity::getId);
		List<CharacterArmorBodyLocalizationEntity> merged = dto.getArmorBodyLocalizations().stream().map(d -> {
			CharacterArmorBodyLocalizationEntity e = getOrCreate(existingById, d.getId(), CharacterArmorBodyLocalizationEntity::new);
			e.setCharacterArmor(armor);
			e.setBodyLocalization(bodyLocalizationService.findByName(d.getBodyLocalization().getName()));
			e.setArmorPoints(d.getArmorPoints());
			return e;
		}).toList();
		replaceCollection(armor.getArmorBodyLocalizations(), merged);
	}

	protected void setBodyLocalizations(CharacterDto dto, CharacterEntity character) {
		Map<Long, CharacterBodyLocalizationEntity> existingById = toIdMap(character.getBodyLocalizations(), CharacterBodyLocalizationEntity::getId);
		List<CharacterBodyLocalizationEntity> merged = dto.getBodyLocalizations().stream().map(d -> {
			CharacterBodyLocalizationEntity e = getOrCreate(existingById, d.getId(), CharacterBodyLocalizationEntity::new);
			e.setCharacter(character);
			e.setBodyLocalization(bodyLocalizationService.findByName(d.getBodyLocalization().getName()));
			setInjuries(d, e);
			return e;
		}).toList();
		replaceCollection(character.getBodyLocalizations(), merged);
	}

	protected void setInjuries(CharacterBodyLocalizationDto dto, CharacterBodyLocalizationEntity bodyLocalization) {
		Map<Long, CharacterBodyLocalizationInjuryEntity> existingById = toIdMap(bodyLocalization.getInjuries(), CharacterBodyLocalizationInjuryEntity::getId);
		List<CharacterBodyLocalizationInjuryEntity> merged = dto.getInjuries().stream().map(d -> {
			CharacterBodyLocalizationInjuryEntity e = getOrCreate(existingById, d.getId(), CharacterBodyLocalizationInjuryEntity::new);
			e.setCharacterBodyLocalization(bodyLocalization);
			e.setModel(injuryService.findByName(d.getModel().getName()));
			e.setValue(d.getValue());
			return e;
		}).toList();
		replaceCollection(bodyLocalization.getInjuries(), merged);
	}

	protected void setConditions(CharacterDto dto, CharacterEntity character) {
		Map<Long, CharacterConditionEntity> existingById = toIdMap(character.getConditions(), CharacterConditionEntity::getId);
		List<CharacterConditionEntity> merged = dto.getConditions().stream().map(d -> {
			CharacterConditionEntity e = getOrCreate(existingById, d.getId(), CharacterConditionEntity::new);
			e.setCharacter(character);
			e.setCondition(conditionService.findByName(d.getCondition().getName()));
			e.setValue(d.getValue());
			e.setCounter(d.getCounter());
			return e;
		}).collect(Collectors.toList());
		replaceCollection(character.getConditions(), merged);
	}

	protected void setNotes(CharacterDto dto, CharacterEntity character) {
		Map<Long, NoteEntity> existingById = toIdMap(character.getNotes(), NoteEntity::getId);
		List<NoteEntity> merged = dto.getNotes().stream().map(d -> {
			NoteEntity e = getOrCreate(existingById, d.getId(), NoteEntity::new);
			e.setCharacter(character);
			e.setNote(d.getNote());
			return e;
		}).toList();
		replaceCollection(character.getNotes(), merged);
	}

	private <E> Map<Long, E> toIdMap(List<E> entities, Function<E, Long> idGetter) {
		return entities.stream()
				.filter(e -> idGetter.apply(e) != null)
				.collect(Collectors.toMap(idGetter, Function.identity()));
	}

	private <E> E getOrCreate(Map<Long, E> existingById, Long id, Supplier<E> factory) {
		if (id != null) {
			E existing = existingById.get(id);
			if (existing != null) return existing;
		}
		return factory.get();
	}

	private <E> void replaceCollection(List<E> collection, List<E> newItems) {
		collection.clear();
		collection.addAll(newItems);
	}
}
