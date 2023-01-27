package com.teus.projectrpg.service.armorservices.armorbodylocalization;

import com.teus.projectrpg.entity.armor.ArmorBodyLocalizationEntity;
import com.teus.projectrpg.repository.armor.ArmorBodyLocalizationRepository;
import org.springframework.stereotype.Service;

@Service
public class ArmorBodyLocalizationServiceImpl implements ArmorBodyLocalizationService {

    private final ArmorBodyLocalizationRepository armorBodyLocalizationRepository;

    public ArmorBodyLocalizationServiceImpl(ArmorBodyLocalizationRepository armorBodyLocalizationRepository) {
        this.armorBodyLocalizationRepository = armorBodyLocalizationRepository;
    }

    @Override
    public void save(ArmorBodyLocalizationEntity armorBodyLocalization) {
        armorBodyLocalizationRepository.save(armorBodyLocalization);
    }
}
