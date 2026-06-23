package com.teus.projectrpg.skirmish.service;

import com.teus.projectrpg.skirmish.dto.SkirmishGroupDto;

import jakarta.validation.Valid;
import java.util.List;

public interface SkirmishGroupService {

    List<SkirmishGroupDto> findAll();

    SkirmishGroupDto save(@Valid SkirmishGroupDto newSkirmishGroupDto);

    void deleteAll();

    void addGroupAdvantagePoint(Long groupId);

    void removeGroupAdvantagePoint(Long groupId);
}
