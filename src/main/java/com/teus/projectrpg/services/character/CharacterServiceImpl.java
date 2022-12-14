package com.teus.projectrpg.services.character;

import com.teus.projectrpg.dto.*;
import com.teus.projectrpg.entity.armor.ArmorBodyLocalizationEntity;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.entity.character.*;
import com.teus.projectrpg.entity.condition.CharacterConditionEntity;
import com.teus.projectrpg.entity.injury.CharacterBodyLocalizationInjuryEntity;
import com.teus.projectrpg.entity.note.NoteEntity;
import com.teus.projectrpg.repository.character.CharacterRepository;
import com.teus.projectrpg.services.armorservices.armor.ArmorService;
import com.teus.projectrpg.services.bodylocalization.BodyLocalizationService;
import com.teus.projectrpg.services.characteristic.CharacteristicService;
import com.teus.projectrpg.services.condition.ConditionService;
import com.teus.projectrpg.services.creaturetrait.CreatureTraitService;
import com.teus.projectrpg.services.injury.InjuryService;
import com.teus.projectrpg.services.skill.SkillService;
import com.teus.projectrpg.services.spell.SpellService;
import com.teus.projectrpg.services.talent.TalentService;
import com.teus.projectrpg.services.weaponservices.weapon.WeaponService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
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

    public CharacterServiceImpl(CharacterRepository characterRepository, CharacteristicService characteristicService, SkillService skillService, TalentService talentService, SpellService spellService, CreatureTraitService creatureTraitService, WeaponService weaponService, ArmorService armorService, BodyLocalizationService bodyLocalizationService, InjuryService injuryService, ConditionService conditionService) {
        this.characterRepository = characterRepository;
        this.characteristicService = characteristicService;
        this.skillService = skillService;
        this.talentService = talentService;
        this.spellService = spellService;
        this.creatureTraitService = creatureTraitService;
        this.weaponService = weaponService;
        this.armorService = armorService;
        this.bodyLocalizationService = bodyLocalizationService;
        this.injuryService = injuryService;
        this.conditionService = conditionService;
    }

    @Override
    public List<CharacterEntity> findAll() {
        return characterRepository.findAll();
    }

    @Override
    public CharacterEntity save(CharacterEntity characterEntity) {
        return characterRepository.save(characterEntity);
    }

    @Override
    public void deleteById(Long id) {
        characterRepository.deleteById(id);
    }

    @Override
    public void createCharacterFromDto(CharacterDto newCharacter, CharacterEntity characterEntity) {
        if (newCharacter.getId() != null) {
            characterEntity.setId(newCharacter.getId());
        }
        characterEntity.setName(newCharacter.getName());
        characterEntity.setDescription(newCharacter.getDescription());
        characterEntity.setGroupColumn(newCharacter.getGroup());
        characterEntity.setIsRightHanded(newCharacter.getIsRightHanded());

        setCharacterCharacteristics(newCharacter, characterEntity);
        setCharacterSkills(newCharacter, characterEntity);
        setCharacterTalents(newCharacter, characterEntity);
        setCharacterTraits(newCharacter, characterEntity);
        setCharacterSpells(newCharacter, characterEntity);
        setCharacterWeapons(newCharacter, characterEntity);
        setCharacterArmors(newCharacter, characterEntity);
        setCharacterBodyLocalizations(newCharacter, characterEntity);
        setCharacterConditions(newCharacter, characterEntity);
        setCharacterNotes(newCharacter, characterEntity);
    }

    private void setCharacterCharacteristics(CharacterDto newCharacter, CharacterEntity characterEntity) {
        for (CharacterCharacteristicDto characterCharacteristicDto : newCharacter.getCharacteristics()) {
            if (characterCharacteristicDto.getCharacteristic() != null) {
                CharacterCharacteristicEntity characterCharacteristicEntity = new CharacterCharacteristicEntity();
                characterCharacteristicEntity.setCharacter(characterEntity);
                characterCharacteristicEntity.setCharacteristic(characteristicService.findByName(characterCharacteristicDto.getCharacteristic().getName()));
                characterCharacteristicEntity.setValue(characterCharacteristicDto.getValue());
                characterEntity.addCharacteristic(characterCharacteristicEntity);
            }
        }
    }

    private void setCharacterSkills(CharacterDto newCharacter, CharacterEntity characterEntity) {
        for (CharacterSkillDto characterSkillDto : newCharacter.getSkills()) {
            if (characterSkillDto.getSkill() != null) {
                CharacterSkillEntity characterSkillEntity = new CharacterSkillEntity();
                characterSkillEntity.setCharacter(characterEntity);
                characterSkillEntity.setSkill(skillService.findByName(characterSkillDto.getSkill().getName()));
                characterSkillEntity.setValue(characterSkillDto.getValue());
                characterEntity.addSkill(characterSkillEntity);
            }
        }
    }

    private void setCharacterTalents(CharacterDto newCharacter, CharacterEntity characterEntity) {
        for (CharacterTalentDto characterTalentDto : newCharacter.getTalents()) {
            if (characterTalentDto.getTalent() != null) {
                CharacterTalentEntity characterTalentEntity = new CharacterTalentEntity();
                characterTalentEntity.setCharacter(characterEntity);
                characterTalentEntity.setTalent(talentService.findByName(characterTalentDto.getTalent().getName()));
                characterTalentEntity.setValue(characterTalentDto.getValue());
                characterEntity.addTalent(characterTalentEntity);
            }
        }
    }

    private void setCharacterTraits(CharacterDto newCharacter, CharacterEntity characterEntity) {
        for (CharacterCreatureTraitDto traitDto : newCharacter.getTraits()) {
            if (traitDto.getTrait() != null) {
                CharacterCreatureTraitEntity traitEntity = new CharacterCreatureTraitEntity();
                traitEntity.setCharacter(characterEntity);
                traitEntity.setCreatureTrait(creatureTraitService.findByName(traitDto.getTrait().getName()));
                traitEntity.setValue(traitDto.getTrait().getHasValue() ? traitDto.getValue() : null);
                characterEntity.addTrait(traitEntity);
            }
        }
    }

    private void setCharacterConditions(CharacterDto newCharacter, CharacterEntity characterEntity) {
        for (CharacterConditionDto conditionDto : newCharacter.getConditions()) {
            if (conditionDto.getCondition() != null) {
                CharacterConditionEntity conditionEntity = new CharacterConditionEntity();
                conditionEntity.setCondition(conditionService.findByName(conditionDto.getCondition().getName()));
                conditionEntity.setCharacter(characterEntity);
                conditionEntity.setValue(conditionDto.getValue());
                conditionEntity.setCounter(conditionDto.getCounter());
                characterEntity.addCondition(conditionEntity);
            }
        }
    }

    private void setCharacterSpells(CharacterDto newCharacter, CharacterEntity characterEntity) {
        for (SpellDto spellDto : newCharacter.getSpells()) {
            if (spellDto != null) {
                characterEntity.addSpell(spellService.findByName(spellDto.getName()));
            }
        }
    }

    private void setCharacterWeapons(CharacterDto newCharacter, CharacterEntity characterEntity) {
        for (CharacterWeaponDto characterWeaponDto : newCharacter.getWeapons()) {
            if (characterWeaponDto.getWeapon() != null) {
                CharacterWeaponEntity characterWeaponEntity = new CharacterWeaponEntity();
                characterWeaponEntity.setCharacter(characterEntity);
                characterWeaponEntity.setWeapon(weaponService.findByName(characterWeaponDto.getWeapon().getName()));
                characterWeaponEntity.setValue(characterWeaponDto.getValue());
                characterEntity.addWeapon(characterWeaponEntity);
            }
        }
    }

    private void setCharacterArmors(CharacterDto newCharacter, CharacterEntity characterEntity) {
        for (ArmorDto armorDto : newCharacter.getArmors()) {
            if (armorDto.getName() != null) {
                characterEntity.addArmor(armorService.findByName(armorDto.getName()));
            }
        }
    }

    private void setCharacterBodyLocalizations(CharacterDto newCharacter, CharacterEntity characterEntity) {
        for (CharacterBodyLocalizationDto characterBodyLocalizationDto : newCharacter.getBodyLocalizations()) {
            if (characterBodyLocalizationDto.getBodyLocalization() != null) {
                CharacterBodyLocalizationEntity characterBodyLocalizationEntity = new CharacterBodyLocalizationEntity();
                characterBodyLocalizationEntity.setCharacter(characterEntity);
                characterBodyLocalizationEntity.setBodyLocalization(bodyLocalizationService.findByName(characterBodyLocalizationDto.getBodyLocalization().getName()));
                characterBodyLocalizationEntity.setArmorPoints(
                        this.calculateArmorPointsForBodyLocalization(characterBodyLocalizationEntity, characterEntity.getArmors())
                );
                characterBodyLocalizationEntity.setAdditionalArmorPoints(0);
                setInjuries(characterBodyLocalizationDto, characterBodyLocalizationEntity);
                characterEntity.addBodyLocalization(characterBodyLocalizationEntity);
            }
        }
    }

    @Override
    public int calculateArmorPointsForBodyLocalization(CharacterBodyLocalizationEntity bodyLocalization, List<ArmorEntity> armors) {
        int armorPoints = 0;
        for (ArmorEntity armor : armors) {
            for (ArmorBodyLocalizationEntity armorBodyLocalization : armor.getArmorBodyLocalizations()) {
                if (armorBodyLocalization.getBodyLocalization().equals(bodyLocalization.getBodyLocalization())) {
                    armorPoints += armorBodyLocalization.getArmorPoints();
                }
            }
        }

        return armorPoints;
    }

    private void setInjuries(CharacterBodyLocalizationDto characterBodyLocalizationDto, CharacterBodyLocalizationEntity characterBodyLocalizationEntity) {
        for (CharacterBodyLocalizationInjuryDto injuryDto : characterBodyLocalizationDto.getInjuries()) {
            CharacterBodyLocalizationInjuryEntity injury = new CharacterBodyLocalizationInjuryEntity();
            injury.setId(injuryDto.getId());
            injury.setCharacterBodyLocalization(characterBodyLocalizationEntity);
            injury.setValue(injuryDto.getValue());
            injury.setInjury(injuryService.findByName(injuryDto.getInjury().getName()));
            characterBodyLocalizationEntity.addInjury(injury);
        }
    }

    private void setCharacterNotes(CharacterDto newCharacter, CharacterEntity characterEntity) {
        for (String note : newCharacter.getNotes()) {
            if (!note.isEmpty()) {
                NoteEntity noteEntity = new NoteEntity();
                noteEntity.setNote(note);
                noteEntity.setCharacter(characterEntity);
                characterEntity.addNote(noteEntity);
            }
        }
    }
}
