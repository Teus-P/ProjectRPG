package com.teus.projectrpg.repository.injury;

import com.teus.projectrpg.entity.injury.InjuryEntity;
import com.teus.projectrpg.type.injury.InjuryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InjuryRepository extends JpaRepository<InjuryEntity, Long> {

    InjuryEntity findInjuryByName(InjuryType injury);
}
