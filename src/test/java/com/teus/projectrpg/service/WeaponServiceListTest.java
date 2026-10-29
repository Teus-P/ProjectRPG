package com.teus.projectrpg.service;

import com.teus.projectrpg.weapon.dto.WeaponDto;
import com.teus.projectrpg.weapon.entity.WeaponEntity;
import com.teus.projectrpg.weapon.mapper.WeaponMapper;
import com.teus.projectrpg.weapon.repository.WeaponRepository;
import com.teus.projectrpg.weapon.service.weapon.WeaponServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class WeaponServiceListTest {

    @Test
    void findAll_hidesDamagedShieldVariants() {
        WeaponEntity normal = new WeaponEntity();
        normal.setName("SHIELD");
        normal.setIsDamagedVariant(false);
        WeaponEntity legacy = new WeaponEntity();
        legacy.setName("DAGGER");
        WeaponEntity variant = new WeaponEntity();
        variant.setName("SHIELD_SHIELD_1");
        variant.setIsDamagedVariant(true);

        WeaponRepository repository = Mockito.mock(WeaponRepository.class);
        Mockito.when(repository.findAll()).thenReturn(List.of(normal, legacy, variant));
        WeaponMapper mapper = Mockito.mock(WeaponMapper.class);
        Mockito.when(mapper.toDtos(Mockito.anyList())).thenReturn(List.<WeaponDto>of());

        new WeaponServiceImpl(repository, mapper).findAll();

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<WeaponEntity>> shown = ArgumentCaptor.forClass(List.class);
        Mockito.verify(mapper).toDtos(shown.capture());
        assertEquals(List.of(normal, legacy), shown.getValue());
        assertFalse(shown.getValue().contains(variant));
    }
}
