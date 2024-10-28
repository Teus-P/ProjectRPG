package com.teus.projectrpg.armor.service.armorbodylocalization;

import com.teus.projectrpg.armor.entity.CharacterArmorBodyLocalizationEntity;
import com.teus.projectrpg.armor.repository.ArmorBodyLocalizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArmorBodyLocalizationServiceImpl implements ArmorBodyLocalizationService {

    private final ArmorBodyLocalizationRepository armorBodyLocalizationRepository;

    @Override
    public void save(CharacterArmorBodyLocalizationEntity armorBodyLocalization) {
        armorBodyLocalizationRepository.save(armorBodyLocalization);
    }
}
