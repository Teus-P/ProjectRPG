package com.teus.projectrpg.injury.repository;

import com.teus.projectrpg.injury.entity.InjuryEntity;
import com.teus.projectrpg.injury.type.InjuryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InjuryRepository extends JpaRepository<InjuryEntity, Long> {
    InjuryEntity findInjuryByName(InjuryType injury);
}
