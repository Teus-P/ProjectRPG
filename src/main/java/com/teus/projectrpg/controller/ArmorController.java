package com.teus.projectrpg.controller;

import com.teus.projectrpg.controller.exception.ElementNotFoundException;
import com.teus.projectrpg.controller.exception.FieldCannotBeNullException;
import com.teus.projectrpg.dto.ArmorDto;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.services.armorservices.armor.ArmorService;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ArmorController {

    private final ArmorService armorService;

    @Autowired
    public ArmorController(ArmorService armorService) {
        this.armorService = armorService;
    }

    @GetMapping("/armor")
    List<ArmorDto> getAllArmors() {
        List<ArmorDto> armorDtos = new ArrayList<>();

        for (ArmorEntity armorEntity : armorService.findAll()) {
            armorDtos.add(armorService.mapToDto(armorEntity));
        }

        return armorDtos;
    }

    @PostMapping("/armor")
    ArmorEntity createNewArmor(@RequestBody ArmorDto newArmor) {
        ArmorEntity armorEntity = armorService.mapToEntity(newArmor);

        try {
            return armorService.save(armorEntity);
        } catch (DataIntegrityViolationException e) {
            throw new FieldCannotBeNullException((PropertyValueException) e.getCause());
        }
    }

    @DeleteMapping("/armor/{id}")
    void deleteArmor(@PathVariable Long id) {
        try {
            armorService.deleteById(id);
        } catch (Exception ex) {
            throw new ElementNotFoundException(id);
        }
    }
}
