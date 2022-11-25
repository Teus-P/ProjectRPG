package com.teus.projectrpg.services.skirmish;

import com.teus.projectrpg.dto.EndTurnCheckDto;
import com.teus.projectrpg.dto.ReceivedDamageDto;
import com.teus.projectrpg.dto.SkirmishCharacterDto;
import com.teus.projectrpg.dto.TestDto;
import com.teus.projectrpg.entity.character.CharacterSkillEntity;
import com.teus.projectrpg.entity.condition.CharacterConditionEntity;
import com.teus.projectrpg.entity.skirmishcharacter.SkirmishCharacterEntity;
import com.teus.projectrpg.services.characteristic.CharacteristicService;
import com.teus.projectrpg.services.condition.ConditionService;
import com.teus.projectrpg.services.skill.SkillService;
import com.teus.projectrpg.services.skirmishcharacter.SkirmishCharacterService;
import com.teus.projectrpg.type.characteristic.CharacteristicType;
import com.teus.projectrpg.type.condition.ConditionType;
import com.teus.projectrpg.type.skill.SkillType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@Service
public class SkirmishService {
    private final SkirmishCharacterService skirmishCharacterService;
    private final CharacteristicService characteristicService;
    private final SkillService skillService;
    private final ConditionService conditionService;

    private EndTurnCheckDto endTurnCheck = new EndTurnCheckDto();

    public SkirmishService(SkirmishCharacterService skirmishCharacterService, CharacteristicService characteristicService, SkillService skillService, ConditionService conditionService) {
        this.skirmishCharacterService = skirmishCharacterService;
        this.characteristicService = characteristicService;
        this.skillService = skillService;
        this.conditionService = conditionService;
    }

    public void endTurnCheck(EndTurnCheckDto endTurnCheck) {
        this.endTurnCheck = endTurnCheck;
        List<SkirmishCharacterEntity> skirmishCharacters = this.skirmishCharacterService.findAll();
        checkConditions(skirmishCharacters);

        this.skirmishCharacterService.saveAll(skirmishCharacters);
    }

    private void checkConditions(List<SkirmishCharacterEntity> skirmishCharacters) {
        skirmishCharacters.forEach(character -> {
            if (!character.getConditions().isEmpty()) {
                ListIterator<CharacterConditionEntity> iterator = character.getConditions().listIterator();
                while (iterator.hasNext()) {
                    CharacterConditionEntity condition = iterator.next();
                    switch (condition.getCondition().getName()) {
                        case BLEEDING -> this.checkBleeding(condition, character, iterator);
                        case DEAFENED -> this.checkDeafened(condition, iterator);
                        case STUNNED -> this.checkStunned(character);
                        case BLINDED -> this.checkBlinded(condition, iterator);
                        case BROKEN -> this.checkBroken(character);
                    }
                }
            }
        });
    }

    private void checkBleeding(CharacterConditionEntity condition, SkirmishCharacterEntity character, ListIterator<CharacterConditionEntity> iterator) {
        if (character.getConditionByType(ConditionType.UNCONSCIOUS).isPresent() && character.getCurrentWounds() <= 0) {
            createTestForCondition(character, ConditionType.BLEEDING, 0);
        } else {
            setDamageFromBleeding(condition, character, iterator);
        }
    }

    private void setDamageFromBleeding(CharacterConditionEntity condition, SkirmishCharacterEntity character, ListIterator<CharacterConditionEntity> iterator) {
        character.setCurrentWounds(character.getCurrentWounds() - condition.getValue());
        if (character.getCurrentWounds() <= 0) {
            character.setCurrentWounds(0);
            if (character.getConditionByType(ConditionType.UNCONSCIOUS).isEmpty()) {
                CharacterConditionEntity newCondition = new CharacterConditionEntity();
                newCondition.setCharacter(character);
                newCondition.setValue(1);
                int toughness = character.getCharacteristicValueByType(CharacteristicType.TOUGHNESS);
                newCondition.setCounter((toughness % 100) / 10);
                newCondition.setCondition(conditionService.findByName(ConditionType.UNCONSCIOUS));
                iterator.add(newCondition);
            }
        }
    }

    private void checkDeafened(CharacterConditionEntity condition, ListIterator<CharacterConditionEntity> iterator) {
        condition.setValue(condition.getValue() - 1);
        if (condition.getValue() <= 0) {
            iterator.remove();
        }
    }

    private void checkStunned(SkirmishCharacterEntity character) {
        createTestForCondition(character, ConditionType.STUNNED, 0);
    }

    private void checkBlinded(CharacterConditionEntity condition, ListIterator<CharacterConditionEntity> iterator) {
        condition.setCounter(condition.getCounter() - 1);
        if (condition.getCounter() == 0 || condition.getCounter() % 2 == 0) {
            condition.setValue(condition.getValue() - 1);
            if (condition.getValue() <= 0) {
                iterator.remove();
            }
        }
    }

    private void checkBroken(SkirmishCharacterEntity character) {
        createTestForCondition(character, ConditionType.BROKEN, 0);
    }

    private void createTestForCondition(SkirmishCharacterEntity character, ConditionType conditionType, int modifier) {
        TestDto test = new TestDto();
        test.setSkirmishCharacter(new SkirmishCharacterDto(character));
        test.setModifier(modifier);
        test.setConditionType(conditionType);
        endTurnCheck.getTests().add(test);
    }

    public EndTurnCheckDto endTurnCheckAfterTests(EndTurnCheckDto endTurnCheck) {
        this.endTurnCheck = endTurnCheck;
        List<SkirmishCharacterEntity> skirmishCharacters = new ArrayList<>();
        for (TestDto test : this.endTurnCheck.getTests()) {
            SkirmishCharacterEntity character = skirmishCharacterService.findById(test.getSkirmishCharacter().getId());
            this.checkConditionAfterTests(test, character);
            skirmishCharacters.add(character);
        }

        this.skirmishCharacterService.saveAll(skirmishCharacters);
        return this.endTurnCheck;
    }

    private void checkConditionAfterTests(TestDto test, SkirmishCharacterEntity character) {
        ListIterator<CharacterConditionEntity> iterator = character.getConditions().listIterator();
        while (iterator.hasNext()) {
            CharacterConditionEntity condition = iterator.next();
            switch (condition.getCondition().getName()) {
                case BLEEDING -> this.checkBleedingTest(test, condition, character, iterator);
                case STUNNED -> this.checkStunnedTest(test, condition, character, iterator);
                case BROKEN -> this.checkBrokenTest(test, condition, character, iterator);
            }
        }
    }

    private void checkBleedingTest(TestDto test,
                                   CharacterConditionEntity condition,
                                   SkirmishCharacterEntity character,
                                   ListIterator<CharacterConditionEntity> iterator) {
        if ((test.getResult() / 10) % 100 == test.getResult() % 10) {
            condition.setValue(condition.getValue() - 1);
            if (condition.getValue() <= 0) {
                iterator.remove();
            }
        } else if (test.getResult() <= (condition.getValue() * 10)) {
            character.setIsDead(true);
        }
    }

    private void checkStunnedTest(TestDto test,
                                  CharacterConditionEntity condition,
                                  SkirmishCharacterEntity character,
                                  ListIterator<CharacterConditionEntity> iterator) {
        Optional<CharacterSkillEntity> skill = skillService.getSkillByType(character.getSkills(), SkillType.ENDURANCE);
        int skillValue = skill.map(CharacterSkillEntity::getValue).orElseGet(() -> characteristicService.getCharacteristicValueByType(character.getCharacteristics(), CharacteristicType.TOUGHNESS));
        if (test.getResult() <= (skillValue + test.getModifier())) {
            condition.setValue(condition.getValue() - (((skillValue / 10) % 100) - ((test.getResult() / 10) % 100)) - 1);
            if (condition.getValue() <= 0) {
                iterator.remove();
                if (character.getConditionByType(ConditionType.FATIGUED).isEmpty()) {
                    CharacterConditionEntity newCondition = new CharacterConditionEntity();
                    newCondition.setCondition(conditionService.findByName(ConditionType.FATIGUED));
                    newCondition.setValue(1);
                    newCondition.setCharacter(character);
                    iterator.add(newCondition);
                }
            }
        }
    }

    private void checkBrokenTest(TestDto test,
                                   CharacterConditionEntity condition,
                                   SkirmishCharacterEntity character,
                                   ListIterator<CharacterConditionEntity> iterator) {
        Optional<CharacterSkillEntity> skill = skillService.getSkillByType(character.getSkills(), SkillType.COOL);
        int skillValue = skill.map(CharacterSkillEntity::getValue).orElseGet(() -> characteristicService.getCharacteristicValueByType(character.getCharacteristics(), CharacteristicType.WILLPOWER));
        if (test.getResult() <= (skillValue + test.getModifier())) {
            condition.setValue(condition.getValue() - (((skillValue / 10) % 100) - ((test.getResult() / 10) % 100)) - 1);
            if (condition.getValue() <= 0) {
                iterator.remove();
                if (character.getConditionByType(ConditionType.FATIGUED).isEmpty()) {
                    CharacterConditionEntity newCondition = new CharacterConditionEntity();
                    newCondition.setCondition(conditionService.findByName(ConditionType.FATIGUED));
                    newCondition.setValue(1);
                    newCondition.setCharacter(character);
                    iterator.add(newCondition);
                }
            }
        }
    }

    public void receiveDamage(ReceivedDamageDto receivedDamage) {
        int finalDamage;
        SkirmishCharacterEntity character = skirmishCharacterService.findById(receivedDamage.getCharacterId());
        int toughnessBonus = ((character.getCharacteristicValueByType(CharacteristicType.TOUGHNESS) / 10) % 100);
        int armorForLocalization = character.getArmorForLocalization(receivedDamage.getBodyLocalization());

        if (receivedDamage.getIsWeaponUndamaging()) {
            armorForLocalization *= 2;
            finalDamage = receivedDamage.getDamage() - toughnessBonus - armorForLocalization;
            if (finalDamage > 0) {
                character.setCurrentWounds(
                        character.getCurrentWounds() - finalDamage
                );
                character.setAdvantage(0);
            } else {
                if (receivedDamage.getIsLosingTest()) {
                    character.setAdvantage(0);
                }
            }
        } else {
            finalDamage = receivedDamage.getDamage() - toughnessBonus - armorForLocalization;
            if (finalDamage >= 1) {
                character.setCurrentWounds(
                        character.getCurrentWounds() - finalDamage
                );
            } else {
                character.setCurrentWounds(
                        character.getCurrentWounds() - 1
                );
            }
            character.setAdvantage(0);
        }

        if (character.getCurrentWounds() <= 0) {
            character.setIsDead(true);
            character.setCurrentWounds(0);
        }

        skirmishCharacterService.save(character);
    }

    public void addAdvantagePoint(Long skirmishCharacterId) {
        SkirmishCharacterEntity character = skirmishCharacterService.findById(skirmishCharacterId);
        int newAdvantage = character.getAdvantage() + 1;
        character.setAdvantage(newAdvantage);

        skirmishCharacterService.save(character);
    }

    public void removeAdvantagePoint(Long skirmishCharacterId) {
        SkirmishCharacterEntity character = skirmishCharacterService.findById(skirmishCharacterId);
        int newAdvantage = character.getAdvantage() - 1;
        if (newAdvantage >= 0) {
            character.setAdvantage(newAdvantage);
        }

        skirmishCharacterService.save(character);
    }
}
