package com.teus.projectrpg.service.skirmish;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.character.dto.CharacterBodyLocalizationDto;
import com.teus.projectrpg.condition.dto.ConditionDto;
import com.teus.projectrpg.bodylocalization.entity.BodyLocalizationEntity;
import com.teus.projectrpg.character.entity.CharacterBodyLocalizationEntity;
import com.teus.projectrpg.character.entity.CharacterCharacteristicEntity;
import com.teus.projectrpg.characteristic.entity.CharacteristicEntity;
import com.teus.projectrpg.condition.entity.CharacterConditionEntity;
import com.teus.projectrpg.condition.entity.ConditionEntity;
import com.teus.projectrpg.character.entity.SkirmishCharacterEntity;
import com.teus.projectrpg.character.mapper.SkirmishCharacterMapper;
import com.teus.projectrpg.character.mapper.CharacterContext;
import com.teus.projectrpg.character.service.SkirmishCharacterService;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import com.teus.projectrpg.characteristic.type.CharacteristicType;
import com.teus.projectrpg.condition.type.ConditionType;
import com.teus.projectrpg.skill.type.SkillType;
import com.teus.projectrpg.skirmish.dto.EndTurnCheckDto;
import com.teus.projectrpg.skirmish.dto.ReceivedDamageDto;
import com.teus.projectrpg.skirmish.dto.TestDto;
import com.teus.projectrpg.skirmish.service.SkirmishServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SkirmishServiceTest {

    @Autowired
    private SkirmishServiceImpl skirmishService;

    @Autowired
    private SkirmishCharacterMapper skirmishCharacterMapper;

    @Autowired
    private CharacterContext characterContext;

    @MockBean
    private SkirmishCharacterService skirmishCharacterService;

    private EndTurnCheckDto endTurnCheck;

    private TestDto createTestDto(SkirmishCharacterEntity character, ConditionType bleeding) {
        TestDto test = new TestDto();
        test.setSkirmishCharacter(skirmishCharacterMapper.toDto(character, characterContext));
        test.setConditionType(new ConditionDto(bleeding));
        test.setModifier(0);
        test.setFeasible(true);
        return test;
    }

    @BeforeEach
    public void setUp() {
        endTurnCheck = new EndTurnCheckDto();
        endTurnCheck.setTests(new ArrayList<>());
    }

    @Test
    void endTurnCheck_whenDead_dontCreateTests() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        character.setCurrentWounds(0);
        character.setIsDead(true);
        addCondition(ConditionType.BLEEDING, 3, 0, character);
        addCondition(ConditionType.POISON, 3, 0, character);
        addCondition(ConditionType.STUNNED, 3, 0, character);

        mockFindAllCharacters(Collections.singletonList(character));
        skirmishService.endTurnCheck(endTurnCheck);

        assertTrue(endTurnCheck.getTests().isEmpty());
    }

    @Test
    void endTurnCheck_whenBleeding_receiveThreeDamage() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.BLEEDING, 3, 0, character);
        int woundsBefore = character.getCurrentWounds();

        mockFindAllCharacters(Collections.singletonList(character));
        skirmishService.endTurnCheck(endTurnCheck);

        assertEquals(woundsBefore - 3, character.getCurrentWounds());
    }

    @Test
    void endTurnCheck_whenBleeding_receiveUnconscious() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.BLEEDING, 4, 0, character);
        character.setCurrentWounds(3);

        mockFindAllCharacters(Collections.singletonList(character));
        skirmishService.endTurnCheck(endTurnCheck);
        Optional<CharacterConditionEntity> unconsciousCondition = character.getConditionByType(ConditionType.UNCONSCIOUS);

        assertEquals(0, character.getCurrentWounds());
        assertTrue(unconsciousCondition.isPresent());
        assertEquals(4, unconsciousCondition.get().getCounter());
    }

    @Test
    void endTurnCheck_whenBleedingAndUnconscious_createTest() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.BLEEDING, 4, 0, character);
        addCondition(ConditionType.UNCONSCIOUS, 1, 0, character);
        character.setCurrentWounds(0);
        TestDto test = createTestDto(character, ConditionType.BLEEDING);

        mockFindAllCharacters(Collections.singletonList(character));
        skirmishService.endTurnCheck(endTurnCheck);

        assertTrue(endTurnCheck.getTests().contains(test));
    }

    @Test
    void endTurnCheck_whenDeafened_removeOneStatus() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.DEAFENED, 2, 0, character);

        mockFindAllCharacters(Collections.singletonList(character));
        skirmishService.endTurnCheck(endTurnCheck);

        Optional<CharacterConditionEntity> deafenedCondition = character.getConditionByType(ConditionType.DEAFENED);
        assertTrue(deafenedCondition.isPresent());
        assertEquals(1, deafenedCondition.get().getValue());
    }

    @Test
    void endTurnCheck_whenDeafened_removeWholeCondition() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.DEAFENED, 1, 0, character);

        mockFindAllCharacters(Collections.singletonList(character));
        skirmishService.endTurnCheck(endTurnCheck);

        assertEquals(0, character.getConditions().size());
    }

    @Test
    void endTurnCheck_whenStunned_createTest() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.STUNNED, 1, 0, character);
        TestDto test = createTestDto(character, ConditionType.STUNNED);

        mockFindAllCharacters(Collections.singletonList(character));
        skirmishService.endTurnCheck(endTurnCheck);

        assertTrue(endTurnCheck.getTests().contains(test));
    }

    @Test
    void endTurnCheck_whenBlinded_removeOneCounter() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.BLINDED, 1, 2, character);

        mockFindAllCharacters(Collections.singletonList(character));
        skirmishService.endTurnCheck(endTurnCheck);

        Optional<CharacterConditionEntity> blindedCondition = character.getConditionByType(ConditionType.BLINDED);
        assertTrue(blindedCondition.isPresent());
        assertEquals(1, blindedCondition.get().getCounter());
    }

    @Test
    void endTurnCheck_whenBlinded_removeWholeCondition() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.BLINDED, 1, 1, character);

        mockFindAllCharacters(Collections.singletonList(character));
        skirmishService.endTurnCheck(endTurnCheck);

        Optional<CharacterConditionEntity> blindedCondition = character.getConditionByType(ConditionType.BLINDED);
        assertTrue(blindedCondition.isEmpty());
    }

    @Test
    void endTurnCheck_whenBlinded_removeOneCondition() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.BLINDED, 2, 1, character);

        mockFindAllCharacters(Collections.singletonList(character));
        skirmishService.endTurnCheck(endTurnCheck);

        Optional<CharacterConditionEntity> blindedCondition = character.getConditionByType(ConditionType.BLINDED);
        assertTrue(blindedCondition.isPresent());
        assertEquals(1, blindedCondition.get().getValue());
    }

    @Test
    void endTurnCheck_whenBroken_createTest() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.BROKEN, 1, 0, character);
        TestDto test = createTestDto(character, ConditionType.BROKEN);

        mockFindAllCharacters(Collections.singletonList(character));
        skirmishService.endTurnCheck(endTurnCheck);

        assertTrue(endTurnCheck.getTests().contains(test));
    }

    @Test
    void endTurnCheck_whenAblaze_createTest() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.ABLAZE, 3, 0, character);
        TestDto test = createTestDto(character, ConditionType.ABLAZE);

        mockFindAllCharacters(Collections.singletonList(character));
        skirmishService.endTurnCheck(endTurnCheck);

        assertTrue(endTurnCheck.getTests().contains(test));
    }

    @Test
    void endTurnCheck_whenSurprised_removeCondition() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.SURPRISED, 1, 0, character);

        mockFindAllCharacters(Collections.singletonList(character));
        skirmishService.endTurnCheck(endTurnCheck);

        assertTrue(character.getConditions().isEmpty());
    }

    @Test
    void endTurnCheck_whenPoison_removeThreeWoundsAndCreateTest() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.POISON, 3, 0, character);

        mockFindAllCharacters(Collections.singletonList(character));
        skirmishService.endTurnCheck(endTurnCheck);

        Optional<TestDto> poisonTest = endTurnCheck.getTests().stream()
                .filter(t -> t.getConditionType().getName().equals(ConditionType.POISON))
                .findFirst();

        assertTrue(poisonTest.isPresent());
        assertTrue(character.getConditionByType(ConditionType.POISON).isPresent());
        assertEquals(7, character.getCurrentWounds());
    }

    @Test
    void endTurnCheck_whenUnconsciousWithZeroValue_decreaseCounter() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.UNCONSCIOUS, 0, 3, character);

        mockFindAllCharacters(Collections.singletonList(character));
        skirmishService.endTurnCheck(endTurnCheck);

        Optional<CharacterConditionEntity> unconscious = character.getConditionByType(ConditionType.UNCONSCIOUS);
        assertTrue(unconscious.isPresent());
        assertEquals(0, unconscious.get().getValue());
        assertEquals(2, unconscious.get().getCounter());
    }

    @Test
    void endTurnCheck_whenUnconsciousWithZeroValue_setOneValueAfterCounterIsDown() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.UNCONSCIOUS, 0, 1, character);

        mockFindAllCharacters(Collections.singletonList(character));
        skirmishService.endTurnCheck(endTurnCheck);

        Optional<CharacterConditionEntity> unconscious = character.getConditionByType(ConditionType.UNCONSCIOUS);
        assertTrue(unconscious.isPresent());
        assertEquals(1, unconscious.get().getValue());
        assertEquals(0, unconscious.get().getCounter());
    }

    @Test
    void endTurnCheckAfterTests_whenBleeding_remainAlive_ifResultHigherThanStatusLevel() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        character.setCurrentWounds(0);
        addCondition(ConditionType.BLEEDING, 2, 0, character);
        addCondition(ConditionType.UNCONSCIOUS, 1, 0, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                null,
                ConditionType.BLEEDING,
                0,
                30,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertFalse(character.getIsDead());
    }

    @Test
    void endTurnCheckAfterTests_whenBleeding_becomeDead_ifResultLowerThanStatusLevel() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        character.setCurrentWounds(0);
        addCondition(ConditionType.BLEEDING, 2, 0, character);
        addCondition(ConditionType.UNCONSCIOUS, 1, 0, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                null,
                ConditionType.BLEEDING,
                0,
                20,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getIsDead());
    }

    @Test
    void endTurnCheckAfterTests_whenBleeding_removeOneCondition_IfDouble() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        character.setCurrentWounds(0);
        addCondition(ConditionType.BLEEDING, 2, 0, character);
        addCondition(ConditionType.UNCONSCIOUS, 1, 0, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                null,
                ConditionType.BLEEDING,
                0,
                11,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getConditionByType(ConditionType.BLEEDING).isPresent());
        assertEquals(1, character.getConditionByType(ConditionType.BLEEDING).get().getValue());
    }

    @Test
    void endTurnCheckAfterTests_whenBleeding_removeWholeCondition_IfDouble() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        character.setCurrentWounds(0);
        addCondition(ConditionType.BLEEDING, 1, 0, character);
        addCondition(ConditionType.UNCONSCIOUS, 1, 0, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                null,
                ConditionType.BLEEDING,
                0,
                11,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getConditionByType(ConditionType.BLEEDING).isEmpty());
    }

    @Test
    void endTurnCheckAfterTests_whenStunned_removeOneCondition_ifZeroSuccessPoints() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.STUNNED, 4, 0, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                SkillType.ENDURANCE,
                ConditionType.STUNNED,
                0,
                40,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getConditionByType(ConditionType.STUNNED).isPresent());
        assertEquals(3, character.getConditionByType(ConditionType.STUNNED).get().getValue());
    }

    @Test
    void endTurnCheckAfterTests_whenStunned_removeTwoConditions_ifOneSuccessPoint() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.STUNNED, 4, 0, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                SkillType.ENDURANCE,
                ConditionType.STUNNED,
                0,
                39,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getConditionByType(ConditionType.STUNNED).isPresent());
        assertEquals(2, character.getConditionByType(ConditionType.STUNNED).get().getValue());
    }

    @Test
    void endTurnCheckAfterTests_whenStunned_removeWholeCondition() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.STUNNED, 3, 0, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                SkillType.ENDURANCE,
                ConditionType.STUNNED,
                0,
                29,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getConditionByType(ConditionType.STUNNED).isEmpty());
        assertTrue(character.getConditionByType(ConditionType.FATIGUED).isPresent());
        assertEquals(1, character.getConditionByType(ConditionType.FATIGUED).get().getValue());
    }

    @Test
    void endTurnCheckAfterTests_whenBroken_removeOneCondition_ifZeroSuccessPoints() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.BROKEN, 2, 0, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                SkillType.COOL,
                ConditionType.BROKEN,
                0,
                40,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getConditionByType(ConditionType.BROKEN).isPresent());
        assertEquals(1, character.getConditionByType(ConditionType.BROKEN).get().getValue());
    }

    @Test
    void endTurnCheckAfterTests_whenBroken_removeWholeCondition() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.BROKEN, 2, 0, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                SkillType.COOL,
                ConditionType.BROKEN,
                0,
                39,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getConditionByType(ConditionType.BROKEN).isEmpty());
        assertTrue(character.getConditionByType(ConditionType.FATIGUED).isPresent());
        assertEquals(1, character.getConditionByType(ConditionType.FATIGUED).get().getValue());
    }

    @Test
    void endTurnCheckAfterTests_whenBrokenAndFatigued_addOneLevelToFatiguedAfterRemovingBroken() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.BROKEN, 1, 0, character);
        addCondition(ConditionType.FATIGUED, 1, 0, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                SkillType.COOL,
                ConditionType.BROKEN,
                0,
                39,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getConditionByType(ConditionType.BROKEN).isEmpty());
        assertTrue(character.getConditionByType(ConditionType.FATIGUED).isPresent());
        assertEquals(2, character.getConditionByType(ConditionType.FATIGUED).get().getValue());
    }

    @Test
    void endTurnCheckAfterTests_whenStunnedAndBrokenWithTestWhichIsNotFeasible_removeStunnedLeaveBroken() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.BROKEN, 2, 0, character);
        addCondition(ConditionType.STUNNED, 1, 0, character);

        TestDto brokenTestDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                SkillType.COOL,
                ConditionType.BROKEN,
                0,
                0,
                false
        );

        TestDto stunnedTestDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                SkillType.ENDURANCE,
                ConditionType.STUNNED,
                1,
                0,
                true
        );

        endTurnCheck.setTests(Arrays.asList(brokenTestDto, stunnedTestDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getConditionByType(ConditionType.BROKEN).isPresent());
        assertEquals(2, character.getConditionByType(ConditionType.BROKEN).get().getValue());
        assertTrue(character.getConditionByType(ConditionType.STUNNED).isEmpty());
        assertTrue(character.getConditionByType(ConditionType.FATIGUED).isPresent());
        assertEquals(1, character.getConditionByType(ConditionType.FATIGUED).get().getValue());
    }

    @Test
    void endTurnCheckAfterTests_whenAblaze_receiveFourDamage() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.ABLAZE, 2, 0, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                null,
                ConditionType.ABLAZE,
                0,
                8,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getConditionByType(ConditionType.ABLAZE).isPresent());
        assertEquals(2, character.getConditionByType(ConditionType.ABLAZE).get().getValue());
        assertEquals(6, character.getCurrentWounds());
    }

    @Test
    void endTurnCheckAfterTests_whenAblaze_setDead() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.ABLAZE, 6, 0, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                null,
                ConditionType.ABLAZE,
                0,
                10,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getConditionByType(ConditionType.ABLAZE).isEmpty());
        assertEquals(0, character.getCurrentWounds());
        assertTrue(character.getIsDead());
    }

    @Test
    void endTurnCheckAfterTests_whenPoison_removeOneCondition_ifZeroSuccessPoints() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.POISON, 4, 0, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                SkillType.ENDURANCE,
                ConditionType.POISON,
                0,
                40,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getConditionByType(ConditionType.POISON).isPresent());
        assertEquals(3, character.getConditionByType(ConditionType.POISON).get().getValue());
    }

    @Test
    void endTurnCheckAfterTests_whenPoison_removeThreeCondition_ifTwoSuccessPoints() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.POISON, 4, 0, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                SkillType.ENDURANCE,
                ConditionType.POISON,
                0,
                20,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getConditionByType(ConditionType.POISON).isPresent());
        assertEquals(1, character.getConditionByType(ConditionType.POISON).get().getValue());
    }

    @Test
    void endTurnCheckAfterTests_whenPoison_removeWholeCondition() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.POISON, 3, 0, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                SkillType.ENDURANCE,
                ConditionType.POISON,
                0,
                29,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getConditionByType(ConditionType.POISON).isEmpty());
        assertTrue(character.getConditionByType(ConditionType.FATIGUED).isPresent());
        assertEquals(1, character.getConditionByType(ConditionType.FATIGUED).get().getValue());
    }

    @Test
    void endTurnCheckAfterTests_whenPoison_setIsDeadIfUnconsciousAndTestFailed() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.POISON, 3, 0, character);
        addCondition(ConditionType.UNCONSCIOUS, 1, 0, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                SkillType.ENDURANCE,
                ConditionType.POISON,
                0,
                41,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getConditionByType(ConditionType.POISON).isPresent());
        assertTrue(character.getConditionByType(ConditionType.UNCONSCIOUS).isPresent());
        assertTrue(character.getIsDead());
    }

    @Test
    void endTurnCheckAfterTests_whenPoison_dontSetIsDeadIfUnconsciousAndTestSucceeded() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.POISON, 3, 0, character);
        addCondition(ConditionType.UNCONSCIOUS, 1, 0, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                SkillType.ENDURANCE,
                ConditionType.POISON,
                0,
                39,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getConditionByType(ConditionType.POISON).isPresent());
        assertTrue(character.getConditionByType(ConditionType.UNCONSCIOUS).isPresent());
        assertFalse(character.getIsDead());
    }

    @Test
    void endTurnCheckAfterTests_whenPoison_dontSetIsDeadIfUnconsciousAreOnlyCounterAndTestFailed() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.POISON, 3, 0, character);
        addCondition(ConditionType.UNCONSCIOUS, 0, 3, character);

        TestDto testDto = new TestDto(
                skirmishCharacterMapper.toDto(character, characterContext),
                SkillType.ENDURANCE,
                ConditionType.POISON,
                0,
                41,
                true
        );
        endTurnCheck.setTests(Collections.singletonList(testDto));

        mockFindById(character);
        skirmishService.endTurnCheckAfterTests(endTurnCheck);

        assertTrue(character.getConditionByType(ConditionType.POISON).isPresent());
        assertTrue(character.getConditionByType(ConditionType.UNCONSCIOUS).isPresent());
        assertFalse(character.getIsDead());
    }

    @Test
    void receiveDamage_removeFourWounds_ifWeaponIsDamaging() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        character.setAdvantage(2);

        ReceivedDamageDto receivedDamageDto = new ReceivedDamageDto();
        receivedDamageDto.setDamage(10);
        receivedDamageDto.setCharacterId(character.getId());
        receivedDamageDto.setBodyLocalization(BodyLocalizationType.BODY);
        receivedDamageDto.setIsWeaponUndamaging(false);
        receivedDamageDto.setIsLosingTest(true);
        receivedDamageDto.setIsSuddenDeath(true);

        mockFindById(character);
        skirmishService.receiveDamage(receivedDamageDto);

        assertEquals(6, character.getCurrentWounds());
        assertEquals(0, character.getAdvantage());
    }

    @Test
    void receiveDamage_removeSurprised() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        addCondition(ConditionType.SURPRISED, 1, 0, character);
        character.setAdvantage(2);

        ReceivedDamageDto receivedDamageDto = new ReceivedDamageDto();
        receivedDamageDto.setDamage(9);
        receivedDamageDto.setCharacterId(character.getId());
        receivedDamageDto.setBodyLocalization(BodyLocalizationType.BODY);
        receivedDamageDto.setIsWeaponUndamaging(false);
        receivedDamageDto.setIsLosingTest(true);
        receivedDamageDto.setIsSuddenDeath(true);

        mockFindById(character);
        skirmishService.receiveDamage(receivedDamageDto);

        assertEquals(7, character.getCurrentWounds());
        assertEquals(0, character.getAdvantage());
        assertTrue(character.getConditionByType(ConditionType.SURPRISED).isEmpty());
    }

    @Test
    void receiveDamage_removeAtLeastOneWound_ifWeaponIsDamaging() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        character.setAdvantage(2);

        ReceivedDamageDto receivedDamageDto = new ReceivedDamageDto();
        receivedDamageDto.setDamage(1);
        receivedDamageDto.setCharacterId(character.getId());
        receivedDamageDto.setBodyLocalization(BodyLocalizationType.BODY);
        receivedDamageDto.setIsWeaponUndamaging(false);
        receivedDamageDto.setIsLosingTest(true);
        receivedDamageDto.setIsSuddenDeath(true);

        mockFindById(character);
        skirmishService.receiveDamage(receivedDamageDto);

        assertEquals(9, character.getCurrentWounds());
        assertEquals(0, character.getAdvantage());
    }

    @Test
    void receiveDamage_removeFourWounds_ifWeaponIsUndamaging() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        character.setAdvantage(2);

        ReceivedDamageDto receivedDamageDto = new ReceivedDamageDto();
        receivedDamageDto.setDamage(10);
        receivedDamageDto.setCharacterId(character.getId());
        receivedDamageDto.setBodyLocalization(BodyLocalizationType.BODY);
        receivedDamageDto.setIsWeaponUndamaging(true);
        receivedDamageDto.setIsLosingTest(true);
        receivedDamageDto.setIsSuddenDeath(true);

        mockFindById(character);
        skirmishService.receiveDamage(receivedDamageDto);

        assertEquals(8, character.getCurrentWounds());
        assertEquals(0, character.getAdvantage());
    }

    @Test
    void receiveDamage_removeAdvantages_ifWeaponIsUndamagingAndLosingTest() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        character.setAdvantage(2);

        ReceivedDamageDto receivedDamageDto = new ReceivedDamageDto();
        receivedDamageDto.setDamage(4);
        receivedDamageDto.setCharacterId(character.getId());
        receivedDamageDto.setBodyLocalization(BodyLocalizationType.BODY);
        receivedDamageDto.setIsWeaponUndamaging(true);
        receivedDamageDto.setIsLosingTest(true);
        receivedDamageDto.setIsSuddenDeath(true);

        mockFindById(character);
        skirmishService.receiveDamage(receivedDamageDto);

        assertEquals(10, character.getCurrentWounds());
        assertEquals(0, character.getAdvantage());
    }

    @Test
    void receiveDamage_dontRemoveAdvantages_ifWeaponIsUndamagingAndNotLosingTest() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        character.setAdvantage(2);

        ReceivedDamageDto receivedDamageDto = new ReceivedDamageDto();
        receivedDamageDto.setDamage(4);
        receivedDamageDto.setCharacterId(character.getId());
        receivedDamageDto.setBodyLocalization(BodyLocalizationType.BODY);
        receivedDamageDto.setIsWeaponUndamaging(true);
        receivedDamageDto.setIsLosingTest(false);
        receivedDamageDto.setIsSuddenDeath(true);

        mockFindById(character);
        skirmishService.receiveDamage(receivedDamageDto);

        assertEquals(10, character.getCurrentWounds());
        assertEquals(2, character.getAdvantage());
    }

    @Test
    void receiveDamage_proneCharacter_ifWoundsAreEqualToZeroAndSuddenDeathIsOff() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        character.setAdvantage(2);

        ReceivedDamageDto receivedDamageDto = new ReceivedDamageDto();
        receivedDamageDto.setDamage(20);
        receivedDamageDto.setCharacterId(character.getId());
        receivedDamageDto.setBodyLocalization(BodyLocalizationType.BODY);
        receivedDamageDto.setIsWeaponUndamaging(true);
        receivedDamageDto.setIsLosingTest(true);
        receivedDamageDto.setIsSuddenDeath(false);

        mockFindById(character);
        skirmishService.receiveDamage(receivedDamageDto);

        assertEquals(0, character.getCurrentWounds());
        assertEquals(0, character.getAdvantage());
        assertTrue(character.getConditionByType(ConditionType.PRONE).isPresent());
        assertTrue(character.getConditionByType(ConditionType.UNCONSCIOUS).isPresent());
        assertEquals(skirmishService.getBonusPoints(character.getCharacteristicValueByType(CharacteristicType.TOUGHNESS)), character.getConditionByType(ConditionType.UNCONSCIOUS).get().getCounter());
    }

    @Test
    void receiveDamage_killCharacter_ifWoundsAreEqualToZeroAndSuddenDeathIsOn() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        character.setAdvantage(2);

        ReceivedDamageDto receivedDamageDto = new ReceivedDamageDto();
        receivedDamageDto.setDamage(20);
        receivedDamageDto.setCharacterId(character.getId());
        receivedDamageDto.setBodyLocalization(BodyLocalizationType.BODY);
        receivedDamageDto.setIsWeaponUndamaging(true);
        receivedDamageDto.setIsLosingTest(true);
        receivedDamageDto.setIsSuddenDeath(true);

        mockFindById(character);
        skirmishService.receiveDamage(receivedDamageDto);

        assertEquals(0, character.getCurrentWounds());
        assertEquals(0, character.getAdvantage());
        assertTrue(character.getIsDead());
    }

    @Test
    void addAdvantagePoint_addOnePoint() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        character.setAdvantage(0);

        mockFindById(character);
        skirmishService.addAdvantagePoint(0L);

        assertEquals(1, character.getAdvantage());
    }

    @Test
    void removeAdvantagePoint_removeOnePoint() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        character.setAdvantage(1);

        mockFindById(character);
        skirmishService.removeAdvantagePoint(0L);

        assertEquals(0, character.getAdvantage());
    }

    @Test
    void addAdditionalArmorPoint_addOnePoint() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        BaseDto<BodyLocalizationType> bodyLocalizationDto = new BaseDto<>();
        bodyLocalizationDto.setName(BodyLocalizationType.BODY);

        CharacterBodyLocalizationDto characterBodyLocalizationDto = new CharacterBodyLocalizationDto();
        characterBodyLocalizationDto.setAdditionalArmorPoints(0);
        characterBodyLocalizationDto.setArmorPoints(0);
        characterBodyLocalizationDto.setInjuries(new ArrayList<>());
        characterBodyLocalizationDto.setCharacterId(character.getId());
        characterBodyLocalizationDto.setBodyLocalization(bodyLocalizationDto);

        mockFindById(character);
        skirmishService.addAdditionalArmorPoint(characterBodyLocalizationDto);

        Optional<CharacterBodyLocalizationEntity> bodyLocalization = character.getBodyLocalizations().stream()
                .filter(o -> o.getBodyLocalization().getName().equals(characterBodyLocalizationDto.getBodyLocalization().getName()))
                .findFirst();

        assertTrue(bodyLocalization.isPresent());
        assertEquals(1, bodyLocalization.get().getAdditionalArmorPoints());
    }

    @Test
    void addAdditionalArmorPoint_removeOnePoint() {
        SkirmishCharacterEntity character = this.createSkirmishCharacterTestList().get(0);
        BaseDto<BodyLocalizationType> bodyLocalizationDto = new BaseDto<>();
        bodyLocalizationDto.setName(BodyLocalizationType.BODY);

        CharacterBodyLocalizationDto characterBodyLocalizationDto = new CharacterBodyLocalizationDto();
        characterBodyLocalizationDto.setAdditionalArmorPoints(0);
        characterBodyLocalizationDto.setArmorPoints(0);
        characterBodyLocalizationDto.setInjuries(new ArrayList<>());
        characterBodyLocalizationDto.setCharacterId(character.getId());
        characterBodyLocalizationDto.setBodyLocalization(bodyLocalizationDto);

        mockFindById(character);
        skirmishService.removeAdditionalArmorPoint(characterBodyLocalizationDto);

        Optional<CharacterBodyLocalizationEntity> bodyLocalization = character.getBodyLocalizations().stream()
                .filter(o -> o.getBodyLocalization().getName().equals(characterBodyLocalizationDto.getBodyLocalization().getName()))
                .findFirst();

        assertTrue(bodyLocalization.isPresent());
        assertEquals(-1, bodyLocalization.get().getAdditionalArmorPoints());
    }

    private void mockFindAllCharacters(List<SkirmishCharacterEntity> characters) {
        Mockito.when(skirmishCharacterService.findAll()).thenReturn(characters);
    }

    private void mockFindById(SkirmishCharacterEntity character) {
        Mockito.when(skirmishCharacterService.findById(character.getId())).thenReturn(character);
    }

    private List<SkirmishCharacterEntity> createSkirmishCharacterTestList() {
        List<SkirmishCharacterEntity> testList = new ArrayList<>();

        SkirmishCharacterEntity skirmishCharacter1 = new SkirmishCharacterEntity();
        skirmishCharacter1.setId(0L);
        skirmishCharacter1.setName("Test");
        skirmishCharacter1.setDescription("Test");
        skirmishCharacter1.setIsRightHanded(true);
        skirmishCharacter1.setIsDead(false);
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
                        createTestCharacterBodyLocalization(skirmishCharacter1, 2, BodyLocalizationType.HEAD),
                        createTestCharacterBodyLocalization(skirmishCharacter1, 2, BodyLocalizationType.LEFT_ARM),
                        createTestCharacterBodyLocalization(skirmishCharacter1, 2, BodyLocalizationType.RIGHT_ARM),
                        createTestCharacterBodyLocalization(skirmishCharacter1, 2, BodyLocalizationType.BODY),
                        createTestCharacterBodyLocalization(skirmishCharacter1, 2, BodyLocalizationType.LEFT_LEG),
                        createTestCharacterBodyLocalization(skirmishCharacter1, 2, BodyLocalizationType.RIGHT_LEG)
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
        bodyLocalization.setArmorPoints(armorPoints);
        bodyLocalization.setBodyLocalization(this.createTestBodyLocalization(bodyLocalizationType));
        bodyLocalization.setAdditionalArmorPoints(0);
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