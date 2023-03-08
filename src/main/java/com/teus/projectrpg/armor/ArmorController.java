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
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArmorController {

    private final ArmorService armorService;
    private final ArmorCategoryService armorCategoryService;
    private final ArmorPenaltyService armorPenaltyService;
    private final ArmorQualityService armorQualityService;

    @GetMapping("/armor")
    public ResponseEntity<List<ArmorDto>> getAllArmors() {
        List<ArmorDto> armors = armorService.findAll();
        if (armors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(armors);
    }

    @PutMapping("/armor")
    public ResponseEntity<ArmorDto> putArmor(@Valid @RequestBody ArmorDto newArmor) {
        return ResponseEntity.ok(armorService.save(newArmor));
    }

    @DeleteMapping("/armor/{id}")
    public ResponseEntity<Void> deleteArmor(@PathVariable Long id) {
        try {
            armorService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/armorCategory")
    public ResponseEntity<List<BaseDto<ArmorCategoryType>>> getAllArmorCategories() {
        List<BaseDto<ArmorCategoryType>> armorCategories = armorCategoryService.findAll();
        if (armorCategories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(armorCategories);
    }

    @GetMapping("/armorPenalty")
    public ResponseEntity<List<BaseDto<ArmorPenaltyType>>> getAllArmorPenalties() {
        List<BaseDto<ArmorPenaltyType>> armorPenalties = armorPenaltyService.findAll();
        if (armorPenalties.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(armorPenalties);
    }

    @GetMapping("/armorQuality")
    public ResponseEntity<List<BaseDto<ArmorQualityType>>> getAllArmorQualities() {
        List<BaseDto<ArmorQualityType>> armorQualities = armorQualityService.findAll();
        if (armorQualities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(armorQualities);
    }
}
