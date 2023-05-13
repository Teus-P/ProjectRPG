package com.teus.projectrpg.armor.entity;

import com.teus.projectrpg.armor.type.ArmorPenaltyType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArmorPenaltyEntityTest {

	@Test
	public void testGetterAndSetter() {
		ArmorPenaltyEntity armorPenaltyEntity = new ArmorPenaltyEntity();

		Long id = 1L;
		ArmorPenaltyType name = ArmorPenaltyType.MINUS_TEN_PERCEPTION;

		armorPenaltyEntity.setId(id);
		armorPenaltyEntity.setName(name);

		Assertions.assertEquals(id, armorPenaltyEntity.getId());
		Assertions.assertEquals(name, armorPenaltyEntity.getName());
	}

}