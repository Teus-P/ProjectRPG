package com.teus.projectrpg.armor.entity;

import com.teus.projectrpg.availability.entity.AvailabilityEntity;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArmorEntityTest {

	@Test
	public void testGetterAndSetter() {
		ArmorEntity armorEntity = new ArmorEntity();

		Long id = 1L;
		String name = "Plate Armor";
		String nameTranslation = "Zbroja płytowa";
		ArmorCategoryEntity armorCategory = new ArmorCategoryEntity();
		List<ArmorBodyLocalizationEntity> armorBodyLocalizations = new ArrayList<>();
		List<ArmorPenaltyEntity> armorPenalties = new ArrayList<>();
		List<ArmorQualityEntity> armorQualities = new ArrayList<>();
		String price = "100 ZK";
		String encumbrance = "4";
		AvailabilityEntity availability = new AvailabilityEntity();
		Boolean isBaseArmor = true;

		armorEntity.setId(id);
		armorEntity.setName(name);
		armorEntity.setNameTranslation(nameTranslation);
		armorEntity.setArmorCategory(armorCategory);
		armorEntity.setArmorBodyLocalizations(armorBodyLocalizations);
		armorEntity.setArmorPenalties(armorPenalties);
		armorEntity.setArmorQualities(armorQualities);
		armorEntity.setPrice(price);
		armorEntity.setEncumbrance(encumbrance);
		armorEntity.setAvailability(availability);
		armorEntity.setIsBaseArmor(isBaseArmor);

		Assertions.assertEquals(id, armorEntity.getId());
		Assertions.assertEquals(name, armorEntity.getName());
		Assertions.assertEquals(nameTranslation, armorEntity.getNameTranslation());
		Assertions.assertEquals(armorCategory, armorEntity.getArmorCategory());
		Assertions.assertEquals(armorBodyLocalizations, armorEntity.getArmorBodyLocalizations());
		Assertions.assertEquals(armorPenalties, armorEntity.getArmorPenalties());
		Assertions.assertEquals(armorQualities, armorEntity.getArmorQualities());
		Assertions.assertEquals(price, armorEntity.getPrice());
		Assertions.assertEquals(encumbrance, armorEntity.getEncumbrance());
		Assertions.assertEquals(availability, armorEntity.getAvailability());
		Assertions.assertEquals(isBaseArmor, armorEntity.getIsBaseArmor());
	}

}