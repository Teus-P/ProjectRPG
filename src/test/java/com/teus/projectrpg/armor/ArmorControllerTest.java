package com.teus.projectrpg.armor;

import com.teus.projectrpg.armor.dto.ArmorDto;
import com.teus.projectrpg.armor.service.armor.ArmorService;
import com.teus.projectrpg.armor.service.armorcategory.ArmorCategoryService;
import com.teus.projectrpg.armor.service.armorpenalty.ArmorPenaltyService;
import com.teus.projectrpg.armor.service.armorquality.ArmorQualityService;
import com.teus.projectrpg.armor.type.ArmorCategoryType;
import com.teus.projectrpg.armor.type.ArmorPenaltyType;
import com.teus.projectrpg.armor.type.ArmorQualityType;
import com.teus.projectrpg.base.dto.BaseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ArmorControllerTest {

    @Mock
    private ArmorService armorService;

    @Mock
    private ArmorCategoryService armorCategoryService;

    @Mock
    private ArmorPenaltyService armorPenaltyService;

    @Mock
    private ArmorQualityService armorQualityService;

    @InjectMocks
    private ArmorController armorController;

    @Test
    public void testGetAllArmors_WhenArmorsNotEmpty() {
        // Przygotowanie danych testowych
        List<ArmorDto> armors = new ArrayList<>();
        ArmorDto armorDto1 = new ArmorDto();
        ArmorDto armorDto2 = new ArmorDto();
        armors.add(armorDto1);
        armors.add(armorDto2);

        Mockito.when(armorService.findAll()).thenReturn(armors);

        // Wywołanie metody i sprawdzenie wyniku
        ResponseEntity<List<ArmorDto>> response = armorController.getAllArmors();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(armors, response.getBody());
        Mockito.verify(armorService).findAll();
    }

    @Test
    public void testGetAllArmors_WhenArmorsEmpty() {
        // Przygotowanie danych testowych
        List<ArmorDto> armors = new ArrayList<>();

        Mockito.when(armorService.findAll()).thenReturn(armors);

        // Wywołanie metody i sprawdzenie wyniku
        ResponseEntity<List<ArmorDto>> response = armorController.getAllArmors();

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertNull(response.getBody());
        Mockito.verify(armorService).findAll();
    }

    @Test
    public void testPutArmor() {
        // Przygotowanie danych testowych
        ArmorDto newArmorDto = new ArmorDto();
        ArmorDto savedArmorDto = new ArmorDto();

        Mockito.when(armorService.save(newArmorDto)).thenReturn(savedArmorDto);

        // Wywołanie metody i sprawdzenie wyniku
        ResponseEntity<ArmorDto> response = armorController.putArmor(newArmorDto);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(savedArmorDto, response.getBody());
        Mockito.verify(armorService).save(newArmorDto);
    }

    @Test
    public void testDeleteArmor_WhenArmorExists() {
        // Przygotowanie danych testowych
        Long armorId = 1L;

        // Wywołanie metody i sprawdzenie wyniku
        ResponseEntity<Void> response = armorController.deleteArmor(armorId);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertNull(response.getBody());
        Mockito.verify(armorService).deleteById(armorId);
    }

    @Test
    public void testDeleteArmor_WhenArmorNotFound() {
        // Przygotowanie danych testowych
        Long armorId = 1L;
        Mockito.doThrow(EmptyResultDataAccessException.class).when(armorService).deleteById(armorId);

        // Wywołanie metody i sprawdzenie wyniku
        ResponseEntity<Void> response = armorController.deleteArmor(armorId);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
        Mockito.verify(armorService).deleteById(armorId);
    }

    @Test
    public void testDeleteArmor_WhenExceptionOccurs() {
        // Przygotowanie danych testowych
        Long armorId = 1L;
        Mockito.doThrow(EmptyResultDataAccessException.class).when(armorService).deleteById(armorId);

        // Wywołanie metody i sprawdzenie wyniku
        ResponseEntity<Void> response = armorController.deleteArmor(armorId);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
        Mockito.verify(armorService).deleteById(armorId);
    }

    @Test
    public void testGetAllArmorCategories_WhenArmorCategoriesNotEmpty() {
        // Przygotowanie danych testowych
        List<BaseDto<ArmorCategoryType>> armorCategories = new ArrayList<>();
        BaseDto<ArmorCategoryType> armorCategoryDto1 = new BaseDto<>();
        BaseDto<ArmorCategoryType> armorCategoryDto2 = new BaseDto<>();
        armorCategories.add(armorCategoryDto1);
        armorCategories.add(armorCategoryDto2);

        Mockito.when(armorCategoryService.findAll()).thenReturn(armorCategories);

        // Wywołanie metody i sprawdzenie wyniku
        ResponseEntity<List<BaseDto<ArmorCategoryType>>> response = armorController.getAllArmorCategories();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(armorCategories, response.getBody());
        Mockito.verify(armorCategoryService).findAll();
    }

    @Test
    public void testGetAllArmorCategories_WhenArmorCategoriesEmpty() {
        // Przygotowanie danych testowych
        List<BaseDto<ArmorCategoryType>> armorCategories = new ArrayList<>();

        Mockito.when(armorCategoryService.findAll()).thenReturn(armorCategories);

        // Wywołanie metody i sprawdzenie wyniku
        ResponseEntity<List<BaseDto<ArmorCategoryType>>> response = armorController.getAllArmorCategories();

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertNull(response.getBody());
        Mockito.verify(armorCategoryService).findAll();
    }

    @Test
    public void testGetAllArmorPenalties_WhenArmorPenaltiesNotEmpty() {
        // Przygotowanie danych testowych
        List<BaseDto<ArmorPenaltyType>> armorPenalties = new ArrayList<>();
        BaseDto<ArmorPenaltyType> armorPenaltyDto1 = new BaseDto<>();
        BaseDto<ArmorPenaltyType> armorPenaltyDto2 = new BaseDto<>();
        armorPenalties.add(armorPenaltyDto1);
        armorPenalties.add(armorPenaltyDto2);

        Mockito.when(armorPenaltyService.findAll()).thenReturn(armorPenalties);

        // Wywołanie metody i sprawdzenie wyniku
        ResponseEntity<List<BaseDto<ArmorPenaltyType>>> response = armorController.getAllArmorPenalties();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(armorPenalties, response.getBody());
        Mockito.verify(armorPenaltyService).findAll();
    }

    @Test
    public void testGetAllArmorPenalties_WhenArmorPenaltiesEmpty() {
        // Przygotowanie danych testowych
        List<BaseDto<ArmorPenaltyType>> armorPenalties = new ArrayList<>();

        Mockito.when(armorPenaltyService.findAll()).thenReturn(armorPenalties);

        // Wywołanie metody i sprawdzenie wyniku
        ResponseEntity<List<BaseDto<ArmorPenaltyType>>> response = armorController.getAllArmorPenalties();

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertNull(response.getBody());
        Mockito.verify(armorPenaltyService).findAll();
    }

    @Test
    public void testGetAllArmorQualities_WhenArmorQualitiesNotEmpty() {
        // Przygotowanie danych testowych
        List<BaseDto<ArmorQualityType>> armorQualities = new ArrayList<>();
        BaseDto<ArmorQualityType> armorQualityDto1 = new BaseDto<>();
        BaseDto<ArmorQualityType> armorQualityDto2 = new BaseDto<>();
        armorQualities.add(armorQualityDto1);
        armorQualities.add(armorQualityDto2);

        Mockito.when(armorQualityService.findAll()).thenReturn(armorQualities);

        // Wywołanie metody i sprawdzenie wyniku
        ResponseEntity<List<BaseDto<ArmorQualityType>>> response = armorController.getAllArmorQualities();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(armorQualities, response.getBody());
        Mockito.verify(armorQualityService).findAll();
    }

    @Test
    public void testGetAllArmorQualities_WhenArmorQualitiesEmpty() {
        // Przygotowanie danych testowych
        List<BaseDto<ArmorQualityType>> armorQualities = new ArrayList<>();

        Mockito.when(armorQualityService.findAll()).thenReturn(armorQualities);

        // Wywołanie metody i sprawdzenie wyniku
        ResponseEntity<List<BaseDto<ArmorQualityType>>> response = armorController.getAllArmorQualities();

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertNull(response.getBody());
        Mockito.verify(armorQualityService).findAll();
    }
}