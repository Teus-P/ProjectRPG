package com.teus.projectrpg.injury;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.injury.service.InjuryService;
import com.teus.projectrpg.injury.type.InjuryType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InjuryController {

    private final InjuryService injuryService;

    @GetMapping("/injury")
    ResponseEntity<List<BaseDto<InjuryType>>> getAllInjuries() {
        List<BaseDto<InjuryType>> injuryDtos = injuryService.findAll();
        if (injuryDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(injuryDtos);
    }
}
