package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.injury.InjuryEntity;
import com.teus.projectrpg.service.base.BaseMapper;
import com.teus.projectrpg.service.injury.InjuryService;
import com.teus.projectrpg.type.injury.InjuryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class InjuryController {

    private final InjuryService injuryService;
    private final BaseMapper baseMapper;

    @Autowired
    public InjuryController(InjuryService injuryService, BaseMapper baseMapper) {
        this.injuryService = injuryService;
        this.baseMapper = baseMapper;
    }

    @GetMapping("/injury")
    List<BaseDto<InjuryType, InjuryEntity>> getAllInjuries() {
        List<BaseDto<InjuryType, InjuryEntity>> injuriesDtos = new ArrayList<>();

        for (InjuryEntity injuryEntity : injuryService.findAll()) {
            injuriesDtos.add(baseMapper.mapToBaseDto(injuryEntity));
        }

        return injuriesDtos;
    }
}
