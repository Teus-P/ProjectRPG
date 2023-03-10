package com.teus.projectrpg.condition;

import com.teus.projectrpg.condition.dto.ConditionDto;
import com.teus.projectrpg.condition.service.ConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConditionController {

    private final ConditionService conditionService;

    @GetMapping("/condition")
    public ResponseEntity<List<ConditionDto>> getAllConditions() {
        List<ConditionDto> conditionDtos = conditionService.findAll();
        if (conditionDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(conditionDtos);
    }
}
