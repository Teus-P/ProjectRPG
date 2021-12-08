package com.teus.projectrpg;

import com.teus.projectrpg.entity.Armor;
import com.teus.projectrpg.repository.ArmorRepository;
import com.teus.projectrpg.type.ArmorCategory;
import com.teus.projectrpg.type.BodyLocalization;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class ProjectRpgApplication {

	private ArmorRepository armorRepository;

	public static void main(String[] args) {
		this.saveTestArmor();
		SpringApplication.run(ProjectRpgApplication.class, args);
	}

	private void saveTestArmor() {
		Armor armor = new Armor();
		ArrayList<BodyLocalization> list = new ArrayList<>();
		list.add(BodyLocalization.BODY);

		armor.setName("Skórzana kurta");
		armor.setArmorPoints(1);
		armor.setArmorCategory(ArmorCategory.SOFT_LEATHER);
		armor.setBodyLocalization(list);

		armorRepository.save(armor);
	}
}
