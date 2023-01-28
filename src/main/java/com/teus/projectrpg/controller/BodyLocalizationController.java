package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.mapper.base.BaseMapper;
import com.teus.projectrpg.service.bodylocalization.BodyLocalizationService;
import com.teus.projectrpg.type.armor.BodyLocalizationType;
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
