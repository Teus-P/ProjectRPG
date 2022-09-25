package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.EndTurnCheckDto;
import com.teus.projectrpg.dto.SkirmishCharacterDto;
import com.teus.projectrpg.dto.TestDto;
import com.teus.projectrpg.entity.condition.CharacterConditionEntity;
import com.teus.projectrpg.entity.skirmishcharacter.SkirmishCharacterEntity;
import com.teus.projectrpg.services.characteristic.CharacteristicService;
import com.teus.projectrpg.services.condition.ConditionService;
import com.teus.projectrpg.services.skirmishcharacter.SkirmishCharacterService;
import com.teus.projectrpg.type.characteristic.CharacteristicType;
import com.teus.projectrpg.type.condition.ConditionType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@RestController
public class SkirmishController {

    private final CharacteristicService characteristicService;
    private final SkirmishCharacterService skirmishCharacterService;
    private final ConditionService conditionService;

    public SkirmishController(CharacteristicService characteristicService, SkirmishCharacterService skirmishCharacterService, ConditionService conditionService) {
        this.characteristicService = characteristicService;
        this.skirmishCharacterService = skirmishCharacterService;
        this.conditionService = conditionService;
    }

    @PostMapping("/endTurnCheck")
    EndTurnCheckDto endTurnCheck(@RequestBody EndTurnCheckDto endTurnCheck) {
        List<SkirmishCharacterEntity> skirmishCharacters = this.skirmishCharacterService.findAll();
        skirmishCharacters.forEach(skirmishCharacterEntity -> {
            if (!skirmishCharacterEntity.getConditions().isEmpty()) {
                checkConditions(endTurnCheck, skirmishCharacterEntity);
            }
        });

        this.skirmishCharacterService.saveAll(skirmishCharacters);
        return endTurnCheck;
    }

    private void checkConditions(EndTurnCheckDto endTurnCheck, SkirmishCharacterEntity character) {
        ListIterator<CharacterConditionEntity> iterator = character.getConditions().listIterator();
        while (iterator.hasNext()) {
            CharacterConditionEntity condition = iterator.next();
            switch (condition.getCondition().getName()) {
                case BLEEDING -> this.checkBleeding(endTurnCheck, condition, character, iterator);
                case DEAFENED -> this.checkDeafened(condition, iterator);
            }
        }
    }

    private void checkBleeding(EndTurnCheckDto endTurnCheck, CharacterConditionEntity condition, SkirmishCharacterEntity character, ListIterator<CharacterConditionEntity> iterator) {
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
                if(character.getConditionByType(ConditionType.UNCONSCIOUS).isEmpty()) {
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

    @PostMapping("/endTurnTestsCheck")
    EndTurnCheckDto endTurnTestsCheck(@RequestBody EndTurnCheckDto endTurnCheck) {
        List<SkirmishCharacterEntity> skirmishCharacters = new ArrayList<>();
        for (TestDto test : endTurnCheck.getTests()) {
            SkirmishCharacterEntity character = skirmishCharacterService.findById(test.getSkirmishCharacter().getId());
            this.checkConditionTests(test, character);
            skirmishCharacters.add(character);
        }


        this.skirmishCharacterService.saveAll(skirmishCharacters);
        return endTurnCheck;
    }

    private void checkConditionTests(TestDto test, SkirmishCharacterEntity character) {
        ListIterator<CharacterConditionEntity> iterator = character.getConditions().listIterator();
        while (iterator.hasNext()) {
            CharacterConditionEntity condition = iterator.next();
            switch (condition.getCondition().getName()) {
                case BLEEDING -> this.checkBleedingTest(test, condition, character, iterator);
            }
        }
    }

    private void checkBleedingTest(TestDto test, CharacterConditionEntity condition, SkirmishCharacterEntity character, ListIterator<CharacterConditionEntity> iterator) {
        if (test.getResult() <= (condition.getValue() * 10)) {
            character.setIsDead(true);
        } else if (test.getResult() % 100 == test.getResult() % 10) {
            condition.setValue(condition.getValue() - 1);
            if (condition.getValue() <= 0) {
                iterator.remove();
            }
        }
    }

    private void checkDeafened(CharacterConditionEntity condition, ListIterator<CharacterConditionEntity> iterator) {
        condition.setValue(condition.getValue() - 1);
        if (condition.getValue() <= 0) {
            iterator.remove();
        }
    }
}
