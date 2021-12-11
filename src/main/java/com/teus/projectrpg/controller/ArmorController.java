package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.ArmorDto;
import com.teus.projectrpg.entity.ArmorEntity;
import com.teus.projectrpg.entity.ArmorPenaltyEntity;
import com.teus.projectrpg.entity.BodyLocalizationEntity;
import com.teus.projectrpg.repository.ArmorRepository;
import com.teus.projectrpg.services.ArmorPenalty.ArmorPenaltyService;
import com.teus.projectrpg.services.BodyLocalization.BodyLocalizationService;
import com.teus.projectrpg.type.ArmorPenaltyType;
import com.teus.projectrpg.type.BodyLocalizationType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ArmorController {

    private final ArmorRepository armorRepository;
    private final BodyLocalizationService bodyLocalizationService;
    private final ArmorPenaltyService armorPenaltyService;

    public ArmorController(ArmorRepository armorRepository, BodyLocalizationService bodyLocalizationService, ArmorPenaltyService armorPenaltyService) {
        this.armorRepository = armorRepository;
        this.bodyLocalizationService = bodyLocalizationService;
        this.armorPenaltyService = armorPenaltyService;
    }

    @GetMapping("/armor")
    List<ArmorEntity> getAll() {
        return armorRepository.findAll();
    }

    @PostMapping("/armor")
    ArmorEntity createNewArmor(@RequestBody ArmorDto newArmor) {
        ArmorEntity armorEntity = new ArmorEntity();
        armorEntity.setId(0L);
        armorEntity.setName(newArmor.getName());
        armorEntity.setArmorCategory(newArmor.getArmorCategory());
        armorEntity.setArmorPoints(newArmor.getArmorPoints());

        ArrayList<BodyLocalizationEntity> bodyLocalizationEntities = new ArrayList<>();
        for(BodyLocalizationType bodyLocalization: newArmor.getBodyLocalization()) {
            bodyLocalizationEntities.add(bodyLocalizationService.findByType(bodyLocalization));
        }
        armorEntity.setBodyLocalizationEntities(bodyLocalizationEntities);

        ArrayList<ArmorPenaltyEntity> armorPenaltyEntities = new ArrayList<>();
        for(ArmorPenaltyType armorPenalty: newArmor.getPenalties()) {
            armorPenaltyEntities.add(armorPenaltyService.findByType(armorPenalty));
        }
        armorEntity.setArmorPenalties(armorPenaltyEntities);

        return armorRepository.save(armorEntity);
    }

    @DeleteMapping("/armor/{id}")
    void deleteArmor(@PathVariable Long id) {
        armorRepository.deleteById(id);
    }
}
