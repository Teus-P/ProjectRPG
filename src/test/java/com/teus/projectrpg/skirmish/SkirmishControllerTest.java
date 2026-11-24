package com.teus.projectrpg.skirmish;

import com.teus.projectrpg.characteristic.type.CharacteristicType;
import com.teus.projectrpg.skill.type.SkillType;
import com.teus.projectrpg.skirmish.dto.AttackTestRequestDto;
import com.teus.projectrpg.skirmish.dto.AttackTestResultDto;
import com.teus.projectrpg.skirmish.dto.DefenderOptionDto;
import com.teus.projectrpg.skirmish.service.SkirmishGroupService;
import com.teus.projectrpg.skirmish.service.SkirmishService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
class SkirmishControllerTest {

    @Mock
    private SkirmishService skirmishService;

    @Mock
    private SkirmishGroupService skirmishGroupService;

    @InjectMocks
    private SkirmishController skirmishController;

    @Test
    void attackTest_returnsTheResultOfTheService() {
        AttackTestRequestDto request = new AttackTestRequestDto();
        AttackTestResultDto expected = new AttackTestResultDto();
        Mockito.when(skirmishService.resolveAttackTest(request)).thenReturn(expected);

        AttackTestResultDto result = skirmishController.attackTest(request);

        assertSame(expected, result);
        Mockito.verify(skirmishService).resolveAttackTest(request);
    }

    @Test
    void defenderOptions_returnsTheOptionsOfTheCharacter() {
        List<DefenderOptionDto> expected = List.of(
                new DefenderOptionDto(SkillType.DODGE, CharacteristicType.AGILITY, 40));
        Mockito.when(skirmishService.findDefenderOptions(2L)).thenReturn(expected);

        List<DefenderOptionDto> result = skirmishController.defenderOptions(2L);

        assertEquals(expected, result);
        Mockito.verify(skirmishService).findDefenderOptions(2L);
    }
}
