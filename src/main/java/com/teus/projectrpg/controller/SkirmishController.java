package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.EndTurnCheckDto;
import com.teus.projectrpg.dto.SkirmishCharacterDto;
import com.teus.projectrpg.dto.TestDto;
import com.teus.projectrpg.entity.character.CharacterSkillEntity;
import com.teus.projectrpg.entity.condition.CharacterConditionEntity;
import com.teus.projectrpg.entity.skill.SkillEntity;
import com.teus.projectrpg.entity.skirmishcharacter.SkirmishCharacterEntity;
import com.teus.projectrpg.services.characteristic.CharacteristicService;
import com.teus.projectrpg.services.condition.ConditionService;
import com.teus.projectrpg.services.skill.SkillService;
import com.teus.projectrpg.services.skirmishcharacter.SkirmishCharacterService;
import com.teus.projectrpg.type.characteristic.CharacteristicType;
import com.teus.projectrpg.type.condition.ConditionType;
import com.teus.projectrpg.type.skill.SkillType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.concurrent.locks.Condition;

@RestController
public class SkirmishController {

    private final SkirmishCharacterService skirmishCharacterService;
    private final CharacteristicService characteristicService;
    private final SkillService skillService;
    private final ConditionService conditionService;

    private EndTurnCheckDto endTurnCheck = new EndTurnCheckDto();

    public SkirmishController(SkirmishCharacterService skirmishCharacterService, CharacteristicService characteristicService, SkillService skillService, ConditionService conditionService) {
        this.skirmishCharacterService = skirmishCharacterService;
        this.characteristicService = characteristicService;
        this.skillService = skillService;
        this.conditionService = conditionService;
    }

    @PostMapping("/endTurnCheck")
    EndTurnCheckDto endTurnCheck(@RequestBody EndTurnCheckDto endTurnCheck) {
        this.endTurnCheck = endTurnCheck;
        List<SkirmishCharacterEntity> skirmishCharacters = this.skirmishCharacterService.findAll();
        skirmishCharacters.forEach(skirmishCharacterEntity -> {
            if (!skirmishCharacterEntity.getConditions().isEmpty()) {
                checkConditions(skirmishCharacterEntity);
            }
        });

        this.skirmishCharacterService.saveAll(skirmishCharacters);
        return this.endTurnCheck;
    }

    private void checkConditions(SkirmishCharacterEntity character) {
        ListIterator<CharacterConditionEntity> iterator = character.getConditions().listIterator();
        while (iterator.hasNext()) {
            CharacterConditionEntity condition = iterator.next();
            switch (condition.getCondition().getName()) {
                case BLEEDING -> this.checkBleeding(condition, character, iterator);
                case DEAFENED -> this.checkDeafened(condition, iterator);
                case STUNNED -> this.checkStunned(character, iterator);
                case BLINDED -> this.checkBlinded(condition, iterator);
            }
        }
    }

    private void checkBleeding(CharacterConditionEntity condition, SkirmishCharacterEntity character, ListIterator<CharacterConditionEntity> iterator) {
        if (character.getConditionByType(ConditionType.UNCONSCIOUS).isPresent() && character.getCurrentWounds() <= 0) {
            TestDto test = new TestDto();
            test.setSkirmishCharacter(new SkirmishCharacterDto(character));
            test.setModifier(0);
            test.setConditionType(ConditionType.BLEEDING);
            endTurnCheck.getTests().add(test);
        } else {
            character.setCurrentWounds(character.getCurrentWounds() - condition.getValue());
            if (character.getCurrentWounds() <= 0) {
                character.setCurrentWounds(0);
                if (character.getConditionByType(ConditionType.UNCONSCIOUS).isEmpty()) {
                    CharacterConditionEntity newCondition = new CharacterConditionEntity();
                    newCondition.setCharacter(character);
                    newCondition.setValue(1);
                    int toughness = this.characteristicService.getCharacteristicValueByType(character.getCharacteristics(), CharacteristicType.TOUGHNESS);
                    newCondition.setCounter((toughness % 100) / 10);
                    newCondition.setCondition(conditionService.findByName(ConditionType.UNCONSCIOUS));
                    iterator.add(newCondition);
                }
            }
        }
    }

    private void checkDeafened(CharacterConditionEntity condition, ListIterator<CharacterConditionEntity> iterator) {
        condition.setValue(condition.getValue() - 1);
        if (condition.getValue() <= 0) {
            iterator.remove();
        }
    }

    private void checkStunned(SkirmishCharacterEntity character, ListIterator<CharacterConditionEntity> iterator) {
        TestDto test = new TestDto();
        test.setSkirmishCharacter(new SkirmishCharacterDto(character));
        test.setModifier(0);
        test.setConditionType(ConditionType.STUNNED);
        endTurnCheck.getTests().add(test);
    }

    private void checkBlinded(CharacterConditionEntity condition, ListIterator<CharacterConditionEntity> iterator) {
        condition.setCounter(condition.getCounter() - 1);
        if(condition.getCounter() == 0 || condition.getCounter() % 2 == 0) {
            condition.setValue(condition.getValue() - 1);
            if(condition.getValue() <= 0) {
                iterator.remove();
            }
        }
    }

    @PostMapping("/endTurnTestsCheck")
    EndTurnCheckDto endTurnTestsCheck(@RequestBody EndTurnCheckDto endTurnCheck) {
        this.endTurnCheck = endTurnCheck;
        List<SkirmishCharacterEntity> skirmishCharacters = new ArrayList<>();
        for (TestDto test : this.endTurnCheck.getTests()) {
            SkirmishCharacterEntity character = skirmishCharacterService.findById(test.getSkirmishCharacter().getId());
            this.checkConditionTests(test, character);
            skirmishCharacters.add(character);
        }


        this.skirmishCharacterService.saveAll(skirmishCharacters);
        return this.endTurnCheck;
    }

    private void checkConditionTests(TestDto test, SkirmishCharacterEntity character) {
        ListIterator<CharacterConditionEntity> iterator = character.getConditions().listIterator();
        while (iterator.hasNext()) {
            CharacterConditionEntity condition = iterator.next();
            switch (condition.getCondition().getName()) {
                case BLEEDING -> this.checkBleedingTest(test, condition, character, iterator);
                case STUNNED -> this.checkStunnedTest(test, condition, character, iterator);
            }
        }
    }

    private void checkBleedingTest(TestDto test, CharacterConditionEntity condition, SkirmishCharacterEntity character, ListIterator<CharacterConditionEntity> iterator) {
        if (test.getResult() <= (condition.getValue() * 10)) {
            character.setIsDead(true);
        } else if ((test.getResult()/10) % 100 == test.getResult() % 10) {
            condition.setValue(condition.getValue() - 1);
            if (condition.getValue() <= 0) {
                iterator.remove();
            }
        }
    }

    private void checkStunnedTest(TestDto test, CharacterConditionEntity condition, SkirmishCharacterEntity character, ListIterator<CharacterConditionEntity> iterator) {
        Optional<CharacterSkillEntity> skill = skillService.getSkillByType(character.getSkills(), SkillType.ENDURANCE);
        int skillValue = skill.map(CharacterSkillEntity::getValue).orElseGet(() -> characteristicService.getCharacteristicValueByType(character.getCharacteristics(), CharacteristicType.TOUGHNESS));
        //TODO prepare endurance test
        int testResult = test.getResult() + test.getModifier();
        if (testResult <= skillValue) {
            condition.setValue(condition.getValue() - (((skillValue/10) % 100) - ((testResult/10) % 100)));
            if (condition.getValue() <= 0) {
                iterator.remove();
                if(character.getConditionByType(ConditionType.FATIGUED).isEmpty()) {
                    CharacterConditionEntity newCondition = new CharacterConditionEntity();
                    newCondition.setCondition(conditionService.findByName(ConditionType.FATIGUED));
                    newCondition.setValue(1);
                    newCondition.setCharacter(character);
                    iterator.add(newCondition);
                }
            }
        }
    }
}
