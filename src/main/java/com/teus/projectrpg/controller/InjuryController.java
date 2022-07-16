package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.injury.InjuryEntity;
import com.teus.projectrpg.services.base.BaseService;
import com.teus.projectrpg.services.injury.InjuryService;
import com.teus.projectrpg.type.injury.InjuryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class InjuryController {

    private final InjuryService injuryService;
    private final BaseService baseService;

    @Autowired
    public InjuryController(InjuryService injuryService, BaseService baseService) {
        this.injuryService = injuryService;
        this.baseService = baseService;
    }

    @GetMapping("/injury")
    List<BaseDto<InjuryType, InjuryEntity>> getAllInjuries() {
        List<BaseDto<InjuryType, InjuryEntity>> injuriesDtos = new ArrayList<>();

        for (InjuryEntity injuryEntity : injuryService.findAll()) {
            injuriesDtos.add(baseService.mapToDto(injuryEntity));
        }

        return injuriesDtos;
    }
}
