package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.condition.ConditionEntity;
import com.teus.projectrpg.services.base.BaseService;
import com.teus.projectrpg.services.condition.ConditionService;
import com.teus.projectrpg.type.condition.ConditionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ConditionController {

    private final ConditionService conditionService;
    private final BaseService baseService;

    @Autowired
    public ConditionController(ConditionService conditionService, BaseService baseService) {
        this.conditionService = conditionService;
        this.baseService = baseService;
    }

    @GetMapping("/condition")
    List<BaseDto<ConditionType, ConditionEntity>> getAllConditions() {
        List<BaseDto<ConditionType, ConditionEntity>> conditionsDtos = new ArrayList<>();

        for (ConditionEntity conditionEntity : conditionService.findAll()) {
            conditionsDtos.add(baseService.mapToDto(conditionEntity));
        }

        return conditionsDtos;
    }
}
