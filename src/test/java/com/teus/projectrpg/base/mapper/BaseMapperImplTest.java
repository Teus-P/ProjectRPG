package com.teus.projectrpg.base.mapper;

import com.teus.projectrpg.armor.entity.ArmorPenaltyEntity;
import com.teus.projectrpg.armor.type.ArmorPenaltyType;
import com.teus.projectrpg.base.dto.BaseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BaseMapperImplTest {

    @InjectMocks
    private BaseMapperImpl baseMapper;

    @Test
    public void shouldMapToDto() {
        ArmorPenaltyEntity armorPenalty = new ArmorPenaltyEntity();
        armorPenalty.setId(1L);
        armorPenalty.setName(ArmorPenaltyType.MINUS_TEN_PERCEPTION);

        BaseDto<ArmorPenaltyType> baseDto = new BaseDto<>(1L, ArmorPenaltyType.MINUS_TEN_PERCEPTION, null);

        Assertions.assertEquals(baseDto, baseMapper.toDto(armorPenalty));
    }

    @Test
    public void shouldMapToDtos() {
        ArmorPenaltyEntity armorPenalty1 = new ArmorPenaltyEntity();
        armorPenalty1.setId(1L);
        armorPenalty1.setName(ArmorPenaltyType.MINUS_TEN_PERCEPTION);

        ArmorPenaltyEntity armorPenalty2 = new ArmorPenaltyEntity();
        armorPenalty2.setId(2L);
        armorPenalty2.setName(ArmorPenaltyType.MINUS_TEN_STEALTH);

        List<ArmorPenaltyEntity> entities = Arrays.asList(armorPenalty1, armorPenalty2);

        BaseDto<ArmorPenaltyType> baseDto1 = new BaseDto<>(1L, ArmorPenaltyType.MINUS_TEN_PERCEPTION, null);
        BaseDto<ArmorPenaltyType> baseDto2 = new BaseDto<>(2L, ArmorPenaltyType.MINUS_TEN_STEALTH, null);

        List<BaseDto<ArmorPenaltyType>> dtos = Arrays.asList(baseDto1, baseDto2);

        Assertions.assertEquals(dtos, baseMapper.toDtos(entities));
    }
}