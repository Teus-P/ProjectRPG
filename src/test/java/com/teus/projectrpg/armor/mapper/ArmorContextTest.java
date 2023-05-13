package com.teus.projectrpg.armor.mapper;

import com.teus.projectrpg.armor.entity.ArmorBodyLocalizationEntity;
import com.teus.projectrpg.armor.entity.ArmorEntity;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArmorContextTest {

	@Test
	public void testSetArmorBodyLocalizations() {
		ArmorEntity armorEntity = new ArmorEntity();
		armorEntity.setArmorBodyLocalizations(new ArrayList<>());

		ArmorContext armorContext = new ArmorContext();

		List<ArmorBodyLocalizationEntity> armorBodyLocalizations = armorContext.setArmorBodyLocalizations(armorEntity);

		Assertions.assertNotNull(armorBodyLocalizations);
		Assertions.assertEquals(armorEntity.getArmorBodyLocalizations(), armorBodyLocalizations);

		for (ArmorBodyLocalizationEntity localizationEntity : armorBodyLocalizations) {
			Assertions.assertEquals(armorEntity, localizationEntity.getArmor());
		}
	}

	@Test
	public void testSetArmorParameters() {
		ArmorEntity armorEntity = new ArmorEntity();
		List<ArmorBodyLocalizationEntity> armorBodyLocalizations = new ArrayList<>();

		ArmorBodyLocalizationEntity armorBodyLocalization1 = new ArmorBodyLocalizationEntity();
		armorBodyLocalizations.add(armorBodyLocalization1);

		ArmorBodyLocalizationEntity armorBodyLocalization2 = new ArmorBodyLocalizationEntity();
		armorBodyLocalizations.add(armorBodyLocalization2);

		armorEntity.setArmorBodyLocalizations(armorBodyLocalizations);

		ArmorContext armorContext = new ArmorContext();

		armorContext.setArmorParameters(armorEntity);

		List<ArmorBodyLocalizationEntity> updatedArmorBodyLocalizations = armorEntity.getArmorBodyLocalizations();
		Assertions.assertNotNull(updatedArmorBodyLocalizations);
		Assertions.assertFalse(updatedArmorBodyLocalizations.isEmpty());

		for (ArmorBodyLocalizationEntity armorBodyLocalization : updatedArmorBodyLocalizations) {
			Assertions.assertEquals(armorEntity, armorBodyLocalization.getArmor());
		}
	}

}