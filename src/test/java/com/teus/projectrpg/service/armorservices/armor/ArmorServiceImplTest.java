package com.teus.projectrpg.service.armorservices.armor;

import com.teus.projectrpg.dto.ArmorBodyLocalizationDto;
import com.teus.projectrpg.dto.ArmorDto;
import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.armor.*;
import com.teus.projectrpg.mapper.context.ArmorContext;
import com.teus.projectrpg.mapper.ArmorMapper;
import com.teus.projectrpg.type.armor.ArmorCategoryType;
import com.teus.projectrpg.type.armor.ArmorPenaltyType;
import com.teus.projectrpg.type.armor.ArmorQualityType;
import com.teus.projectrpg.type.armor.BodyLocalizationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArmorServiceImplTest {

    @Autowired
    ArmorMapper armorMapper;

    @Autowired
    ArmorContext armorContext;

    @Test
    void whenConvertArmorDtoToArmorEntity_thenCorrect() {
        ArmorDto armorDto = prepareArmorDto();

        ArmorEntity armorEntity = armorMapper.toEntity(armorDto, armorContext);

        compareDtoAndEntity(armorEntity, armorDto);
    }

    @Test
    void whenConvertArmorEntityToArmorDto_thenCorrect() {
        ArmorEntity armorEntity = prepareArmorEntity();

        ArmorDto armorDto = armorMapper.toDto(armorEntity);

        compareDtoAndEntity(armorEntity, armorDto);
    }

    private void compareDtoAndEntity(ArmorEntity armorEntity, ArmorDto armorDto) {
        assertEquals(armorEntity.getId(), armorDto.getId());
        assertEquals(armorEntity.getName(), armorDto.getName());
        assertEquals(armorEntity.getNameTranslation(), armorDto.getNameTranslation());
        assertEquals(armorEntity.getArmorCategory().getId(), armorDto.getArmorCategory().getId());
        assertEquals(armorEntity.getArmorCategory().getName(), armorDto.getArmorCategory().getName());

        for (int i = 0; i < armorEntity.getArmorBodyLocalizations().size(); i++) {
            assertEquals(armorEntity.getArmorBodyLocalizations().get(i).getId(), armorDto.getArmorBodyLocalizations().get(i).getId());
            assertEquals(armorEntity.getArmorBodyLocalizations().get(i).getBodyLocalization().getId(), armorDto.getArmorBodyLocalizations().get(i).getBodyLocalization().getId());
            assertEquals(armorEntity.getArmorBodyLocalizations().get(i).getBodyLocalization().getName(), armorDto.getArmorBodyLocalizations().get(i).getBodyLocalization().getName());
            assertEquals(armorEntity.getArmorBodyLocalizations().get(i).getArmorPoints(), armorDto.getArmorBodyLocalizations().get(i).getArmorPoints());
        }

        for (int i = 0; i < armorEntity.getArmorQualities().size(); i++) {
            assertEquals(armorEntity.getArmorQualities().get(i).getId(), armorDto.getArmorQualities().get(i).getId());
            assertEquals(armorEntity.getArmorQualities().get(i).getName(), armorDto.getArmorQualities().get(i).getName());
        }

        for (int i = 0; i < armorEntity.getArmorPenalties().size(); i++) {
            assertEquals(armorEntity.getArmorPenalties().get(i).getId(), armorDto.getArmorPenalties().get(i).getId());
            assertEquals(armorEntity.getArmorPenalties().get(i).getName(), armorDto.getArmorPenalties().get(i).getName());
        }
    }

    private ArmorEntity prepareArmorEntity() {
        ArmorEntity armorEntity = new ArmorEntity();
        armorEntity.setId(1L);
        armorEntity.setName("TEST");
        armorEntity.setNameTranslation("TEST");

        ArmorCategoryEntity armorCategoryEntity = new ArmorCategoryEntity();
        armorCategoryEntity.setId(2L);
        armorCategoryEntity.setName(ArmorCategoryType.BOILED_LEATHER);
        armorEntity.setArmorCategory(armorCategoryEntity);

        BodyLocalizationEntity bodyLocalizationEntityLeftArm = new BodyLocalizationEntity();
        bodyLocalizationEntityLeftArm.setId(1L);
        bodyLocalizationEntityLeftArm.setName(BodyLocalizationType.LEFT_ARM);
        ArmorBodyLocalizationEntity armorLeftArm = new ArmorBodyLocalizationEntity();
        armorLeftArm.setArmor(armorEntity);
        armorLeftArm.setArmorPoints(1);
        armorLeftArm.setBodyLocalization(bodyLocalizationEntityLeftArm);
        BodyLocalizationEntity bodyLocalizationEntityRightArm = new BodyLocalizationEntity();
        bodyLocalizationEntityRightArm.setId(2L);
        bodyLocalizationEntityRightArm.setName(BodyLocalizationType.RIGHT_ARM);
        ArmorBodyLocalizationEntity armorRightArm = new ArmorBodyLocalizationEntity();
        armorRightArm.setArmor(armorEntity);
        armorRightArm.setArmorPoints(1);
        armorRightArm.setBodyLocalization(bodyLocalizationEntityRightArm);
        armorEntity.setArmorBodyLocalizations(List.of(armorRightArm, armorLeftArm));

        ArmorQualityEntity armorQualityEntity = new ArmorQualityEntity();
        armorQualityEntity.setId(5L);
        armorQualityEntity.setName(ArmorQualityType.DURABLE);
        armorEntity.setArmorQualities(List.of(armorQualityEntity));

        ArmorPenaltyEntity armorPenaltyEntity = new ArmorPenaltyEntity();
        armorPenaltyEntity.setId(2L);
        armorPenaltyEntity.setName(ArmorPenaltyType.MINUS_TEN_STEALTH);
        armorEntity.setArmorPenalties(List.of(armorPenaltyEntity));

        return armorEntity;
    }

    private ArmorDto prepareArmorDto() {
        ArmorDto armorDto = new ArmorDto();
        armorDto.setId(1L);
        armorDto.setName("TEST");
        armorDto.setNameTranslation("TEST");

        BaseDto<ArmorCategoryType, ArmorCategoryEntity> armorCategoryDto = new BaseDto<>();
        armorCategoryDto.setId(2L);
        armorCategoryDto.setName(ArmorCategoryType.BOILED_LEATHER);
        armorDto.setArmorCategory(armorCategoryDto);

        BaseDto<BodyLocalizationType, BodyLocalizationEntity> bodyLocalizationDtoLeftArm = new BaseDto<>();
        bodyLocalizationDtoLeftArm.setId(1L);
        bodyLocalizationDtoLeftArm.setName(BodyLocalizationType.LEFT_ARM);
        ArmorBodyLocalizationDto armorLeftArm = new ArmorBodyLocalizationDto();
        armorLeftArm.setId(1L);
        armorLeftArm.setBodyLocalization(bodyLocalizationDtoLeftArm);
        armorLeftArm.setArmorPoints(1);

        BaseDto<BodyLocalizationType, BodyLocalizationEntity> bodyLocalizationDtoRightArm = new BaseDto<>();
        bodyLocalizationDtoRightArm.setId(2L);
        bodyLocalizationDtoRightArm.setName(BodyLocalizationType.RIGHT_ARM);
        ArmorBodyLocalizationDto armorRightArm = new ArmorBodyLocalizationDto();
        armorRightArm.setId(2L);
        armorRightArm.setBodyLocalization(bodyLocalizationDtoRightArm);
        armorRightArm.setArmorPoints(1);

        armorDto.setArmorBodyLocalizations(List.of(armorRightArm, armorLeftArm));

        BaseDto<ArmorQualityType, ArmorQualityEntity> armorQualityDto = new BaseDto<>();
        armorQualityDto.setId(5L);
        armorQualityDto.setName(ArmorQualityType.DURABLE);
        armorDto.setArmorQualities(List.of(armorQualityDto));

        BaseDto<ArmorPenaltyType, ArmorPenaltyEntity> armorPenaltyDto = new BaseDto<>();
        armorPenaltyDto.setId(2L);
        armorPenaltyDto.setName(ArmorPenaltyType.MINUS_TEN_STEALTH);
        armorDto.setArmorPenalties(List.of(armorPenaltyDto));

        return armorDto;
    }
}