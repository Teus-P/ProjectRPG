package com.teus.projectrpg.services.injury;

import com.teus.projectrpg.entity.injury.InjuryEntity;
import com.teus.projectrpg.type.injury.InjuryType;

import java.util.List;

public interface InjuryService {

    List<InjuryEntity> findAll();

    InjuryEntity findByName(InjuryType injuryType);
}
