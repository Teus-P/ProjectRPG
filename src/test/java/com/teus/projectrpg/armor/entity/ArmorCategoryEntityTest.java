package com.teus.projectrpg.armor.entity;

import com.teus.projectrpg.armor.type.ArmorCategoryType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArmorCategoryEntityTest {

	@Test
	public void testGetterAndSetter() {
		ArmorCategoryEntity armorCategoryEntity = new ArmorCategoryEntity();

		Long id = 1L;
		ArmorCategoryType name = ArmorCategoryType.PLATE;

		armorCategoryEntity.setId(id);
		armorCategoryEntity.setName(name);

		Assertions.assertEquals(id, armorCategoryEntity.getId());
		Assertions.assertEquals(name, armorCategoryEntity.getName());
	}

}