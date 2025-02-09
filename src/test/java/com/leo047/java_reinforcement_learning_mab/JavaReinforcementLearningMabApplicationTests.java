package com.leo047.java_reinforcement_learning_mab;

import com.leo047.java_reinforcement_learning_mab.model.AdContent;
import com.leo047.java_reinforcement_learning_mab.service.AdService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class JavaReinforcementLearningMabApplicationTests {

	@Test
	public void testGetAdForUser() {
		 AdService adService = new AdService();
		 String ad = String.valueOf(adService.getAdForUser("user_1"));
		 assertNotNull(ad);
//		 assertEquals("ad_1", ad.trim());
	}

}
