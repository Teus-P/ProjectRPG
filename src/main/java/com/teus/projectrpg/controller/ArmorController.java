package com.teus.projectrpg.controller;

import com.teus.projectrpg.entity.Armor;
import com.teus.projectrpg.repository.ArmorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArmorController {

    private final ArmorRepository armorRepository;

    public ArmorController(ArmorRepository armorRepository) {
        this.armorRepository = armorRepository;
    }

    @GetMapping("/armor")
    List<Armor> getAll() {
        return armorRepository.findAll();
    }

    @PostMapping("/armor")
    Armor createNewArmor(@RequestBody Armor newArmor) {
        return armorRepository.save(newArmor);
    }
}
