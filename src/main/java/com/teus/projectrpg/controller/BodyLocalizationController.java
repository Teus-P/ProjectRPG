package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.armor.BodyLocalizationEntity;
import com.teus.projectrpg.services.bodylocalization.BodyLocalizationService;
import com.teus.projectrpg.type.armor.BodyLocalizationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BodyLocalizationController {

    private final BodyLocalizationService bodyLocalizationService;

    @Autowired
    public BodyLocalizationController(BodyLocalizationService bodyLocalizationService) {
        this.bodyLocalizationService = bodyLocalizationService;
    }

    @GetMapping("/bodyLocalization")
    List<BaseDto<BodyLocalizationType, BodyLocalizationEntity>> getAllBodyLocalizations() {
        List<BaseDto<BodyLocalizationType, BodyLocalizationEntity>> bodyLocalizationsDtos = new ArrayList<>();

        for(BodyLocalizationEntity bodyLocalization : bodyLocalizationService.findAll()) {
            bodyLocalizationsDtos.add(new BaseDto<>(bodyLocalization));
        }

        return bodyLocalizationsDtos;
    }
}
