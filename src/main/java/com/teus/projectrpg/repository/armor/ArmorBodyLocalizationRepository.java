package com.teus.projectrpg.repository.armor;

import com.teus.projectrpg.entity.armor.ArmorBodyLocalizationEntity;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArmorBodyLocalizationRepository extends JpaRepository<ArmorBodyLocalizationEntity, Long> {

    public List<ArmorBodyLocalizationEntity> findArmorBodyLocalizationEntitiesByArmor(ArmorEntity armorEntity);
}
