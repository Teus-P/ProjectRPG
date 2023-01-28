package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.ConditionDto;
import com.teus.projectrpg.mapper.ConditionMapper;
import com.teus.projectrpg.service.condition.ConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConditionController {

    private final ConditionService conditionService;
    private final ConditionMapper conditionMapper;

    @GetMapping("/condition")
    List<ConditionDto> getAllConditions() {
        return conditionMapper.toDtos(conditionService.findAll());
    }
}
