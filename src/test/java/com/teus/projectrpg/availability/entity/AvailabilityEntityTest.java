package com.teus.projectrpg.availability.entity;

import com.teus.projectrpg.availability.type.AvailabilityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AvailabilityEntityTest {

	@Test
	public void testGetterAndSetter() {
		AvailabilityEntity availabilityEntity = new AvailabilityEntity();

		Long id = 1L;
		AvailabilityType name = AvailabilityType.EXOTIC;

		availabilityEntity.setId(id);
		availabilityEntity.setName(name);

		Assertions.assertEquals(id, availabilityEntity.getId());
		Assertions.assertEquals(name, availabilityEntity.getName());
	}

}