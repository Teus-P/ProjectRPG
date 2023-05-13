package com.teus.projectrpg.armor.service.armorquality;

import com.teus.projectrpg.armor.entity.ArmorQualityEntity;
import com.teus.projectrpg.armor.repository.ArmorQualityRepository;
import com.teus.projectrpg.armor.type.ArmorQualityType;
import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.base.mapper.BaseMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArmorQualityServiceImplTest {

	@Mock
	private ArmorQualityRepository armorQualityRepository;

	@Mock
	private BaseMapper baseMapper;

	@InjectMocks
	private ArmorQualityServiceImpl armorQualityService;

	@Test
	public void testFindAll() {
		List<ArmorQualityEntity> armorQualityEntities = new ArrayList<>();
		ArmorQualityEntity armorQualityEntity1 = new ArmorQualityEntity();
		ArmorQualityEntity armorQualityEntity2 = new ArmorQualityEntity();
		armorQualityEntities.add(armorQualityEntity1);
		armorQualityEntities.add(armorQualityEntity2);

		List<BaseDto<ArmorQualityType>> expectedDtoList = new ArrayList<>();
		BaseDto<ArmorQualityType> baseDto1 = new BaseDto<>();
		BaseDto<ArmorQualityType> baseDto2 = new BaseDto<>();
		expectedDtoList.add(baseDto1);
		expectedDtoList.add(baseDto2);

		Mockito.when(armorQualityRepository.findAll()).thenReturn(armorQualityEntities);
		Mockito.when(baseMapper.toDtos(armorQualityEntities)).thenReturn(expectedDtoList);

		List<BaseDto<ArmorQualityType>> result = armorQualityService.findAll();

		Assertions.assertEquals(expectedDtoList, result);
		Mockito.verify(armorQualityRepository).findAll();
		Mockito.verify(baseMapper).toDtos(armorQualityEntities);
	}

	@Test
	public void testSave() {
		ArmorQualityEntity armorQualityEntity = new ArmorQualityEntity();

		armorQualityService.save(armorQualityEntity);

		Mockito.verify(armorQualityRepository).save(armorQualityEntity);
	}

}