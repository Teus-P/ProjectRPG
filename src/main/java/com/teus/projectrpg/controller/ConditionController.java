package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.ConditionDto;
import com.teus.projectrpg.entity.condition.ConditionEntity;
import com.teus.projectrpg.service.condition.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ConditionController {

    private final ConditionService conditionService;

    @Autowired
    public ConditionController(ConditionService conditionService) {
        this.conditionService = conditionService;
    }

    @GetMapping("/condition")
    List<ConditionDto> getAllConditions() {
        List<ConditionDto> conditionsDtos = new ArrayList<>();

        for (ConditionEntity conditionEntity : conditionService.findAll()) {
            conditionsDtos.add(new ConditionDto(conditionEntity));
        }

        return conditionsDtos;
    }
}
