package com.teus.projectrpg.service.skirmish;

import com.teus.projectrpg.dto.*;
import com.teus.projectrpg.entity.character.CharacterBodyLocalizationEntity;
import com.teus.projectrpg.entity.character.CharacterSkillEntity;
import com.teus.projectrpg.entity.condition.CharacterConditionEntity;
import com.teus.projectrpg.entity.skirmishcharacter.SkirmishCharacterEntity;
import com.teus.projectrpg.service.characteristic.CharacteristicService;
import com.teus.projectrpg.service.condition.ConditionService;
import com.teus.projectrpg.service.skill.SkillService;
import com.teus.projectrpg.service.skirmishcharacter.SkirmishCharacterService;
import com.teus.projectrpg.type.characteristic.CharacteristicType;
import com.teus.projectrpg.type.condition.ConditionType;
import com.teus.projectrpg.type.skill.SkillType;
import org.springframework.stereotype.Service;

import java.util.*;

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
                this.checkUnconscious(character.getConditions());
                if (!character.getIsDead()) {
                    ListIterator<CharacterConditionEntity> iterator = character.getConditions().listIterator();
                    while (iterator.hasNext()) {
                        CharacterConditionEntity condition = iterator.next();
                        if (!character.getIsDead()) {
                            switch (condition.getCondition().getName()) {
                                case BLEEDING -> this.checkBleeding(condition, character, iterator);
                                case DEAFENED -> this.checkDeafened(condition, iterator);
                                case STUNNED -> this.checkStunned(character);
                                case BLINDED -> this.checkBlinded(condition, iterator);
                                case BROKEN -> this.checkBroken(character);
                                case ABLAZE -> this.checkAblaze(character);
                                case SURPRISED -> this.checkSurprised(iterator);
                                case POISON -> this.checkPoison(condition, character);
                            }
                        } else {
                            break;
                        }
                    }
                    this.checkIfProne(character);
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

    private void checkAblaze(SkirmishCharacterEntity character) {
        createTestForCondition(character, ConditionType.ABLAZE, 0);
    }

    private void checkSurprised(ListIterator<CharacterConditionEntity> iterator) {
        iterator.remove();
    }

    private void checkPoison(CharacterConditionEntity condition, SkirmishCharacterEntity character) {
        character.setCurrentWounds(character.getCurrentWounds() - condition.getValue());
        createTestForCondition(character, ConditionType.POISON, 0);
    }

    private void checkUnconscious(List<CharacterConditionEntity> conditions) {
        Optional<CharacterConditionEntity> unconscious = conditions.stream()
                .filter(c -> c.getCondition().getName().equals(ConditionType.UNCONSCIOUS))
                .findFirst();

        if(unconscious.isPresent()) {
            if(unconscious.get().getCounter() > 0) {
                unconscious.get().setCounter(unconscious.get().getCounter() - 1);
                if(unconscious.get().getCounter() <= 0) {
                    unconscious.get().setValue(1);
                    unconscious.get().setCounter(0);
                }
            }
        }
    }

    private void createTestForCondition(SkirmishCharacterEntity character, ConditionType conditionType, int modifier) {
        TestDto test = new TestDto();
        test.setSkirmishCharacter(new SkirmishCharacterDto(character));
        test.setModifier(modifier);
        test.setConditionType(new ConditionDto(conditionType));
        test.setFeasible(true);
        endTurnCheck.getTests().add(test);
    }

    public EndTurnCheckDto endTurnCheckAfterTests(EndTurnCheckDto endTurnCheck) {
        this.endTurnCheck = endTurnCheck;
        List<SkirmishCharacterEntity> skirmishCharacters = new ArrayList<>();
        for (TestDto test : this.endTurnCheck.getTests()) {
            SkirmishCharacterEntity character = skirmishCharacterService.findById(test.getSkirmishCharacter().getId());
            if (!character.getIsDead()) {
                this.checkConditionAfterTests(test, character);
                skirmishCharacters.add(character);
            }
        }

        this.skirmishCharacterService.saveAll(skirmishCharacters);
        return this.endTurnCheck;
    }

    private void checkConditionAfterTests(TestDto test, SkirmishCharacterEntity character) {
        Optional<CharacterConditionEntity> condition = character.getConditionByType(test.getConditionType().getName());
        if (condition.isPresent()) {
            switch (condition.get().getCondition().getName()) {
                case BLEEDING -> this.checkBleedingTest(test, condition.get(), character);
                case STUNNED -> this.checkStunnedTest(test, condition.get(), character);
                case BROKEN -> this.checkBrokenTest(test, condition.get(), character);
                case ABLAZE -> this.checkAblazeTest(test, condition.get(), character);
                case POISON -> this.checkPoisonTest(test, condition.get(), character);
            }
        }

    }

    private void checkBleedingTest(TestDto test,
                                   CharacterConditionEntity condition,
                                   SkirmishCharacterEntity character) {
        if ((test.getResult() / 10) % 100 == test.getResult() % 10) {
            condition.setValue(condition.getValue() - 1);
            if (condition.getValue() <= 0) {
                character.removeConditionByType(condition.getCondition().getName());
            }
        } else if (test.getResult() <= (condition.getValue() * 10)) {
            character.setIsDead(true);
        }
    }

    private void checkStunnedTest(TestDto test,
                                  CharacterConditionEntity condition,
                                  SkirmishCharacterEntity character) {
        Optional<CharacterSkillEntity> skill = skillService.getSkillByType(character.getSkills(), SkillType.ENDURANCE);
        int skillValue = skill.map(CharacterSkillEntity::getValue).orElseGet(() -> characteristicService.getCharacteristicValueByType(character.getCharacteristics(), CharacteristicType.TOUGHNESS));
        if (test.getResult() <= (skillValue + test.getModifier())) {
            addFatiguedAfterConditionRemoving(test, condition, character, skillValue);
        }
    }

    private void checkBrokenTest(TestDto test,
                                 CharacterConditionEntity condition,
                                 SkirmishCharacterEntity character) {
        if (test.isFeasible()) {
            Optional<CharacterSkillEntity> skill = skillService.getSkillByType(character.getSkills(), SkillType.COOL);
            int skillValue = skill.map(CharacterSkillEntity::getValue).orElseGet(() -> characteristicService.getCharacteristicValueByType(character.getCharacteristics(), CharacteristicType.WILLPOWER));
            if (test.getResult() > 0 && test.getResult() < 100 && test.getResult() <= (skillValue + test.getModifier())) {
                condition.setValue(condition.getValue() - (((skillValue / 10) % 100) - ((test.getResult() / 10) % 100)) - 1);
                if (condition.getValue() <= 0) {
                    character.removeConditionByType(condition.getCondition().getName());
                    if (character.getConditionByType(ConditionType.FATIGUED).isEmpty()) {
                        CharacterConditionEntity fatigued = new CharacterConditionEntity();
                        fatigued.setCondition(conditionService.findByName(ConditionType.FATIGUED));
                        fatigued.setValue(1);
                        fatigued.setCharacter(character);
                        character.addCondition(fatigued);
                    } else {
                        CharacterConditionEntity fatigued = character.getConditionByType(ConditionType.FATIGUED).get();
                        fatigued.setValue(fatigued.getValue() + 1);
                    }
                }
            }
        }
    }

    private void checkAblazeTest(TestDto test,
                                 CharacterConditionEntity condition,
                                 SkirmishCharacterEntity character) {
        int damage = test.getResult() + condition.getValue();
        int toughnessBonus = getBonusPoints(character.getCharacteristicValueByType(CharacteristicType.TOUGHNESS));
        int lowestArmor = character.getBodyLocalizations().stream()
                .min(Comparator.comparing(CharacterBodyLocalizationEntity::getArmorPoints))
                .orElseThrow(NoSuchElementException::new)
                .getArmorPoints();

        int finalDamage = damage - toughnessBonus - lowestArmor;
        finalDamage = Math.max(finalDamage, 1);

        character.setCurrentWounds(character.getCurrentWounds() - finalDamage);
        character.setAdvantage(0);

        if (character.getCurrentWounds() <= 0) {
            character.setIsDead(true);
            character.setCurrentWounds(0);
            character.removeConditionByType(ConditionType.ABLAZE);
        }
    }

    private void checkPoisonTest(TestDto test,
                                 CharacterConditionEntity condition,
                                 SkirmishCharacterEntity character) {
        Optional<CharacterSkillEntity> skill = skillService.getSkillByType(character.getSkills(), SkillType.ENDURANCE);
        int skillValue = skill.map(CharacterSkillEntity::getValue).orElseGet(() -> characteristicService.getCharacteristicValueByType(character.getCharacteristics(), CharacteristicType.TOUGHNESS));

        boolean isTestSuccessful = test.getResult() <= (skillValue + test.getModifier());

        Optional<CharacterConditionEntity> unconscious = character.getConditionByType(ConditionType.UNCONSCIOUS);
        if (unconscious.isPresent() && unconscious.get().getValue() > 0) {
            if (!isTestSuccessful) {
                character.setIsDead(true);
            }
        } else if (isTestSuccessful) {
            addFatiguedAfterConditionRemoving(test, condition, character, skillValue);
        }
    }

    private void addFatiguedAfterConditionRemoving(TestDto test, CharacterConditionEntity condition, SkirmishCharacterEntity character, int skillValue) {
        condition.setValue(condition.getValue() - (getBonusPoints(skillValue) - getBonusPoints(test.getResult())) - 1);
        if (condition.getValue() <= 0) {
            character.removeConditionByType(condition.getCondition().getName());
            if (character.getConditionByType(ConditionType.FATIGUED).isEmpty()) {
                CharacterConditionEntity newCondition = new CharacterConditionEntity();
                newCondition.setCondition(conditionService.findByName(ConditionType.FATIGUED));
                newCondition.setValue(1);
                newCondition.setCharacter(character);
                character.addCondition(newCondition);
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

        this.checkIfProne(character);

        if (character.getConditionByType(ConditionType.SURPRISED).isPresent()) {
            character.removeConditionByType(ConditionType.SURPRISED);
        }

        skirmishCharacterService.save(character);
    }


    public void checkIfProne(SkirmishCharacterEntity character) {
        if (character.getCurrentWounds() <= 0) {
            character.setCurrentWounds(0);
            if (character.getConditionByType(ConditionType.PRONE).isEmpty()) {
                CharacterConditionEntity prone = new CharacterConditionEntity();
                prone.setCondition(conditionService.findByName(ConditionType.PRONE));
                prone.setValue(1);
                prone.setCharacter(character);
                character.addCondition(prone);
            }

            if (character.getConditionByType(ConditionType.UNCONSCIOUS).isEmpty()) {
                CharacterConditionEntity unconscious = new CharacterConditionEntity();
                unconscious.setCondition(conditionService.findByName(ConditionType.UNCONSCIOUS));
                unconscious.setValue(0);
                unconscious.setCounter(getBonusPoints(character.getCharacteristicValueByType(CharacteristicType.TOUGHNESS)));
                unconscious.setCharacter(character);
                character.addCondition(unconscious);
            }
        }
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

    public int getBonusPoints(int value) {
        return (value / 10) % 100;
    }

    public void addAdditionalArmorPoint(CharacterBodyLocalizationDto bodyLocalization) {
        SkirmishCharacterEntity character = skirmishCharacterService.findById(bodyLocalization.getCharacterId());
        List<CharacterBodyLocalizationEntity> bodyLocalizations = character.getBodyLocalizations();
        bodyLocalizations.stream()
                .filter(o -> o.getBodyLocalization().getName().equals(bodyLocalization.getBodyLocalization().getName()))
                .findFirst()
                .ifPresent(o -> o.setAdditionalArmorPoints(o.getAdditionalArmorPoints() + 1));

        character.setBodyLocalizations(bodyLocalizations);
        skirmishCharacterService.save(character);
    }

    public void removeAdditionalArmorPoint(CharacterBodyLocalizationDto bodyLocalization) {
        SkirmishCharacterEntity character = skirmishCharacterService.findById(bodyLocalization.getCharacterId());
        List<CharacterBodyLocalizationEntity> bodyLocalizations = character.getBodyLocalizations();
        bodyLocalizations.stream()
                .filter(o -> o.getBodyLocalization().getName().equals(bodyLocalization.getBodyLocalization().getName()))
                .findFirst()
                .ifPresent(o -> o.setAdditionalArmorPoints(o.getAdditionalArmorPoints() - 1));

        character.setBodyLocalizations(bodyLocalizations);
        skirmishCharacterService.save(character);
    }
}
