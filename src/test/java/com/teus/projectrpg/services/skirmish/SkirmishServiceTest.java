package com.teus.projectrpg.services.skirmish;

import com.teus.projectrpg.entity.armor.BodyLocalizationEntity;
import com.teus.projectrpg.entity.character.CharacterBodyLocalizationEntity;
import com.teus.projectrpg.entity.character.CharacterCharacteristicEntity;
import com.teus.projectrpg.entity.characteristic.CharacteristicEntity;
import com.teus.projectrpg.entity.condition.CharacterConditionEntity;
import com.teus.projectrpg.entity.condition.ConditionEntity;
import com.teus.projectrpg.entity.skirmishcharacter.SkirmishCharacterEntity;
import com.teus.projectrpg.services.skirmishcharacter.SkirmishCharacterService;
import com.teus.projectrpg.type.armor.BodyLocalizationType;
import com.teus.projectrpg.type.characteristic.CharacteristicType;
import com.teus.projectrpg.type.condition.ConditionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SkirmishServiceTest {

    @Autowired
    private SkirmishService skirmishService;

    @MockBean
    private SkirmishCharacterService skirmishCharacterService;

    @Test
    void endTurnCheck_whenDeafened() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.DEAFENED, 1, 0, character);

        this.skirmishService.checkConditions(Collections.singletonList(character));

        assertEquals(character.getConditions().size(), 0);
    }

    @Test
    void endTurnTestsCheck() {
    }

    private List<SkirmishCharacterEntity> createSkirmishCharacterTestList() {
        List<SkirmishCharacterEntity> testList = new ArrayList<>();

        SkirmishCharacterEntity skirmishCharacter1 = new SkirmishCharacterEntity();
        skirmishCharacter1.setName("Test");
        skirmishCharacter1.setDescription("Test");
        skirmishCharacter1.setIsRightHanded(true);
        skirmishCharacter1.setCharacteristics(
                Arrays.asList(
                        createTestCharacterCharacteristic(skirmishCharacter1, 4, CharacteristicType.MOVEMENT),
                        createTestCharacterCharacteristic(skirmishCharacter1, 40, CharacteristicType.WEAPON_SKILL),
                        createTestCharacterCharacteristic(skirmishCharacter1, 40, CharacteristicType.BALLISTIC_SKILL),
                        createTestCharacterCharacteristic(skirmishCharacter1, 40, CharacteristicType.STRENGTH),
                        createTestCharacterCharacteristic(skirmishCharacter1, 40, CharacteristicType.TOUGHNESS),
                        createTestCharacterCharacteristic(skirmishCharacter1, 40, CharacteristicType.INITIATIVE),
                        createTestCharacterCharacteristic(skirmishCharacter1, 40, CharacteristicType.AGILITY),
                        createTestCharacterCharacteristic(skirmishCharacter1, 40, CharacteristicType.DEXTERITY),
                        createTestCharacterCharacteristic(skirmishCharacter1, 40, CharacteristicType.INTELLIGENCE),
                        createTestCharacterCharacteristic(skirmishCharacter1, 40, CharacteristicType.WILLPOWER),
                        createTestCharacterCharacteristic(skirmishCharacter1, 40, CharacteristicType.FELLOWSHIP),
                        createTestCharacterCharacteristic(skirmishCharacter1, 12, CharacteristicType.WOUNDS)
                )
        );
        skirmishCharacter1.setBodyLocalizations(
                Arrays.asList(
                        createTestCharacterBodyLocalization(skirmishCharacter1, 0, BodyLocalizationType.HEAD),
                        createTestCharacterBodyLocalization(skirmishCharacter1, 0, BodyLocalizationType.LEFT_ARM),
                        createTestCharacterBodyLocalization(skirmishCharacter1, 0, BodyLocalizationType.RIGHT_ARM),
                        createTestCharacterBodyLocalization(skirmishCharacter1, 0, BodyLocalizationType.BODY),
                        createTestCharacterBodyLocalization(skirmishCharacter1, 0, BodyLocalizationType.LEFT_LEG),
                        createTestCharacterBodyLocalization(skirmishCharacter1, 0, BodyLocalizationType.RIGHT_LEG)
                )
        );

        skirmishCharacter1.setCurrentWounds(10);
        skirmishCharacter1.setConditions(new ArrayList<>());
        testList.add(skirmishCharacter1);
        return testList;
    }

    private CharacterCharacteristicEntity createTestCharacterCharacteristic(SkirmishCharacterEntity character, int value, CharacteristicType characteristicType) {
        CharacterCharacteristicEntity characteristic = new CharacterCharacteristicEntity();
        characteristic.setCharacter(character);
        characteristic.setValue(value);
        characteristic.setCharacteristic(this.createTestCharacteristic(characteristicType));
        return characteristic;
    }

    private CharacteristicEntity createTestCharacteristic(CharacteristicType characteristicType) {
        CharacteristicEntity characteristic = new CharacteristicEntity();
        characteristic.setName(characteristicType);
        return characteristic;
    }

    private CharacterBodyLocalizationEntity createTestCharacterBodyLocalization(SkirmishCharacterEntity character, int armorPoints, BodyLocalizationType bodyLocalizationType) {
        CharacterBodyLocalizationEntity bodyLocalization = new CharacterBodyLocalizationEntity();
        bodyLocalization.setCharacter(character);
        bodyLocalization.setBodyLocalization(this.createTestBodyLocalization(bodyLocalizationType));
        return bodyLocalization;
    }

    private BodyLocalizationEntity createTestBodyLocalization(BodyLocalizationType bodyLocalizationType) {
        BodyLocalizationEntity bodyLocalization = new BodyLocalizationEntity();
        bodyLocalization.setName(bodyLocalizationType);
        return bodyLocalization;
    }

    private void addCondition(ConditionType conditionType, int value, int counter, SkirmishCharacterEntity character) {
        ConditionEntity conditionEntity = new ConditionEntity();
        conditionEntity.setName(conditionType);
        CharacterConditionEntity characterCondition = new CharacterConditionEntity();
        characterCondition.setCondition(conditionEntity);
        characterCondition.setValue(value);
        characterCondition.setCounter(counter);
        characterCondition.setCharacter(character);
        character.addCondition(characterCondition);
    }
}