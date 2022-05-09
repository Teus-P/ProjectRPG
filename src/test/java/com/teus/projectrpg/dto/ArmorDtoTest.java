package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.armor.*;
import com.teus.projectrpg.type.armor.ArmorCategoryType;
import com.teus.projectrpg.type.armor.ArmorPenaltyType;
import com.teus.projectrpg.type.armor.ArmorQualityType;
import com.teus.projectrpg.type.armor.BodyLocalizationType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArmorDtoTest {

    @Test
    public void whenConvertArmorEntityToArmorDto_thenCorrect() {
        ArmorEntity armorEntity = new ArmorEntity();
        armorEntity.setId(1L);
        armorEntity.setName("TEST");
        armorEntity.setNameTranslation("TEST");

        ArmorCategoryEntity armorCategoryEntity = new ArmorCategoryEntity();
        armorCategoryEntity.setId(1L);
        armorCategoryEntity.setName(ArmorCategoryType.BOILED_LEATHER);
        armorEntity.setArmorCategory(armorCategoryEntity);

        BodyLocalizationEntity bodyLocalizationEntityLeftArm = new BodyLocalizationEntity();
        bodyLocalizationEntityLeftArm.setId(1L);
        bodyLocalizationEntityLeftArm.setName(BodyLocalizationType.LEFT_ARM);
        BodyLocalizationEntity bodyLocalizationEntityRightArm = new BodyLocalizationEntity();
        bodyLocalizationEntityRightArm.setId(2L);
        bodyLocalizationEntityRightArm.setName(BodyLocalizationType.RIGHT_ARM);
        armorEntity.setBodyLocalizations(List.of(bodyLocalizationEntityRightArm, bodyLocalizationEntityLeftArm));

        ArmorQualityEntity armorQualityEntity = new ArmorQualityEntity();
        armorQualityEntity.setId(1L);
        armorQualityEntity.setName(ArmorQualityType.DURABLE);
        armorEntity.setArmorQualities(List.of(armorQualityEntity));

        ArmorPenaltyEntity armorPenaltyEntity = new ArmorPenaltyEntity();
        armorPenaltyEntity.setId(1L);
        armorPenaltyEntity.setName(ArmorPenaltyType.MINUS_TEN_STEALTH);
        armorEntity.setArmorPenalties(List.of(armorPenaltyEntity));

        armorEntity.setArmorPoints(1);

        ArmorDto armorDto = new ArmorDto(armorEntity);

        assertEquals(armorEntity.getId(), armorDto.getId());
        assertEquals(armorEntity.getName(), armorDto.getName());
        assertEquals(armorEntity.getNameTranslation(), armorDto.getNameTranslation());
        assertEquals(armorEntity.getArmorCategory().getId(), armorDto.getArmorCategory().getId());
        assertEquals(armorEntity.getArmorCategory().getName(), armorDto.getArmorCategory().getName());
        assertEquals(armorEntity.getArmorPoints(), armorDto.getArmorPoints());

        for (int i = 0; i < armorEntity.getBodyLocalizations().size(); i++) {
            assertEquals(armorEntity.getBodyLocalizations().get(i).getId(), armorDto.getBodyLocalization().get(i).getId());
            assertEquals(armorEntity.getBodyLocalizations().get(i).getName(), armorDto.getBodyLocalization().get(i).getName());
        }

        for (int i = 0; i < armorEntity.getArmorQualities().size(); i++) {
            assertEquals(armorEntity.getArmorQualities().get(i).getId(), armorDto.getQualities().get(i).getId());
            assertEquals(armorEntity.getArmorQualities().get(i).getName(), armorDto.getQualities().get(i).getName());
        }

        for (int i = 0; i < armorEntity.getArmorPenalties().size(); i++) {
            assertEquals(armorEntity.getArmorPenalties().get(i).getId(), armorDto.getPenalties().get(i).getId());
            assertEquals(armorEntity.getArmorPenalties().get(i).getName(), armorDto.getPenalties().get(i).getName());
        }
    }
}