package com.teus.projectrpg.armor.entity;

import com.teus.projectrpg.bodylocalization.entity.BodyLocalizationEntity;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BodyLocalizationEntityTest {

	@Test
	public void testGetterAndSetter() {
		BodyLocalizationEntity bodyLocalizationEntity = new BodyLocalizationEntity();

		Long id = 1L;
		BodyLocalizationType name = BodyLocalizationType.HEAD;

		bodyLocalizationEntity.setId(id);
		bodyLocalizationEntity.setName(name);

		Assertions.assertEquals(id, bodyLocalizationEntity.getId());
		Assertions.assertEquals(name, bodyLocalizationEntity.getName());
	}

}