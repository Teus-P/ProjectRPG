package com.teus.projectrpg.bodylocalization;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.base.mapper.BaseMapper;
import com.teus.projectrpg.bodylocalization.service.BodyLocalizationService;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BodyLocalizationController {

    private final BodyLocalizationService bodyLocalizationService;
    private final BaseMapper baseMapper;

    @GetMapping("/bodyLocalization")
    List<BaseDto<BodyLocalizationType>> getAllBodyLocalizations() {
        return baseMapper.toDtos(bodyLocalizationService.findAll());
    }
}
