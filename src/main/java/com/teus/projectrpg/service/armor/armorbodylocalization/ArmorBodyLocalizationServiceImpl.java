package com.teus.projectrpg.service.armor.armorbodylocalization;

import com.teus.projectrpg.entity.armor.ArmorBodyLocalizationEntity;
import com.teus.projectrpg.repository.armor.ArmorBodyLocalizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArmorBodyLocalizationServiceImpl implements ArmorBodyLocalizationService {

    private final ArmorBodyLocalizationRepository armorBodyLocalizationRepository;

    @Override
    public void save(ArmorBodyLocalizationEntity armorBodyLocalization) {
        armorBodyLocalizationRepository.save(armorBodyLocalization);
    }
}
