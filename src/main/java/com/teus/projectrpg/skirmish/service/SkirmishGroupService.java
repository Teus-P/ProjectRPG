package com.teus.projectrpg.skirmish.service;

import com.teus.projectrpg.skirmish.dto.SkirmishGroupDto;

import com.teus.projectrpg.skirmish.entity.SkirmishGroupEntity;
import jakarta.validation.Valid;
import java.util.List;

public interface SkirmishGroupService {

    SkirmishGroupEntity findEntityById(Long id);

    List<SkirmishGroupDto> findAll();

    SkirmishGroupDto saveDto(@Valid SkirmishGroupDto newSkirmishGroupDto);

    void deleteAll();

    void addGroupAdvantagePoint(Long groupId);

    void removeGroupAdvantagePoint(Long groupId);
}
