package com.teus.projectrpg.injury.service;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.injury.entity.InjuryEntity;
import com.teus.projectrpg.injury.type.InjuryType;

import java.util.List;

public interface InjuryService {

    List<BaseDto<InjuryType>> findAll();

    InjuryEntity findByName(InjuryType injuryType);
}
