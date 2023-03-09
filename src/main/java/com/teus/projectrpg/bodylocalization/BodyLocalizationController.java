package com.teus.projectrpg.bodylocalization;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.bodylocalization.service.BodyLocalizationService;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BodyLocalizationController {

    private final BodyLocalizationService bodyLocalizationService;

    @GetMapping("/bodyLocalization")
    public ResponseEntity<List<BaseDto<BodyLocalizationType>>> getAllBodyLocalizations() {
        List<BaseDto<BodyLocalizationType>> bodyLocalizations = bodyLocalizationService.findAll();
        if (bodyLocalizations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bodyLocalizations);
    }
}
