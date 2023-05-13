package com.teus.projectrpg.armor.dto;

import com.teus.projectrpg.armor.type.ArmorCategoryType;
import com.teus.projectrpg.armor.type.ArmorPenaltyType;
import com.teus.projectrpg.armor.type.ArmorQualityType;
import com.teus.projectrpg.availability.type.AvailabilityType;
import com.teus.projectrpg.base.dto.BaseDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArmorDtoTest {

	@Test
	public void testGetterAndSetter() {
		ArmorDto armorDto = new ArmorDto();

		Long id = 1L;
		String name = "Armor Name";
		String nameTranslation = "Armor Name";
		BaseDto<ArmorCategoryType> armorCategory = new BaseDto<>();
		armorCategory.setId(1L);
		armorCategory.setName(ArmorCategoryType.SOFT_LEATHER);
		armorCategory.setNameTranslation("ARMOR");
		List<ArmorBodyLocalizationDto> armorBodyLocalizations = new ArrayList<>();
		ArmorBodyLocalizationDto armorBodyLocalizationDto = new ArmorBodyLocalizationDto();
		armorBodyLocalizations.add(armorBodyLocalizationDto);
		List<BaseDto<ArmorPenaltyType>> armorPenalties = new ArrayList<>();
		BaseDto<ArmorPenaltyType> armorPenaltyDto = new BaseDto<>();
		armorPenaltyDto.setId(1L);
		armorPenaltyDto.setName(ArmorPenaltyType.MINUS_TEN_PERCEPTION);
		armorPenaltyDto.setNameTranslation("Minus ten perception");
		armorPenalties.add(armorPenaltyDto);
		List<BaseDto<ArmorQualityType>> armorQualities = new ArrayList<>();
		BaseDto<ArmorQualityType> armorQualityDto = new BaseDto<>();
		armorQualityDto.setId(1L);
		armorQualityDto.setName(ArmorQualityType.FLEXIBLE);
		armorQualityDto.setNameTranslation("Flexible");
		armorQualities.add(armorQualityDto);
		String price = "1 ZK";
		String encumbrance = "1";
		BaseDto<AvailabilityType> availability = new BaseDto<>();
		availability.setId(1L);
		availability.setName(AvailabilityType.COMMON);
		availability.setNameTranslation("Common");
		Boolean isBaseArmor = true;

		armorDto.setId(id);
		armorDto.setName(name);
		armorDto.setNameTranslation(nameTranslation);
		armorDto.setArmorCategory(armorCategory);
		armorDto.setArmorBodyLocalizations(armorBodyLocalizations);
		armorDto.setArmorPenalties(armorPenalties);
		armorDto.setArmorQualities(armorQualities);
		armorDto.setPrice(price);
		armorDto.setEncumbrance(encumbrance);
		armorDto.setAvailability(availability);
		armorDto.setIsBaseArmor(isBaseArmor);

		Assertions.assertEquals(id, armorDto.getId());
		Assertions.assertEquals(name, armorDto.getName());
		Assertions.assertEquals(nameTranslation, armorDto.getNameTranslation());
		Assertions.assertEquals(armorCategory, armorDto.getArmorCategory());
		Assertions.assertEquals(armorBodyLocalizations, armorDto.getArmorBodyLocalizations());
		Assertions.assertEquals(armorPenalties, armorDto.getArmorPenalties());
		Assertions.assertEquals(armorQualities, armorDto.getArmorQualities());
		Assertions.assertEquals(price, armorDto.getPrice());
		Assertions.assertEquals(encumbrance, armorDto.getEncumbrance());
		Assertions.assertEquals(availability, armorDto.getAvailability());
		Assertions.assertEquals(isBaseArmor, armorDto.getIsBaseArmor());
	}

}