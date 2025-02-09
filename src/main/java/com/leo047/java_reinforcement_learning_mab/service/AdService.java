package com.leo047.java_reinforcement_learning_mab.service;

import com.leo047.java_reinforcement_learning_mab.model.AdContent;
import com.leo047.java_reinforcement_learning_mab.agent.EpsilonGreedyAgent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdService {

    private static final Logger logger = LoggerFactory.getLogger(AdService.class);

    private final EpsilonGreedyAgent mabAgent;
    private final Map<String, Map<String, Double>> userRewards;

    public AdService() {
        this.userRewards = new HashMap<>();
        mabAgent = new EpsilonGreedyAgent();
    }

    public AdContent getAdForUser(String userId) {

        // Initialize user rewards if not present
        userRewards.putIfAbsent(userId, new HashMap<>());

        // Select ad using the Epsilon-Greedy MAB Agent
        String selectedAd = mabAgent.selectAd(userId);

        AdContent ad = getAdContent(selectedAd);

        logger.info("Selected Ad for User: {} is: {}", userId, ad.getAdContent());
        // Return the ad content based on selectedAd (fetch from DB)

        return ad;
    }

    private static AdContent getAdContent(String selectedAd) {
        AdContent ad = new AdContent();

        if (selectedAd == "ad_1") {
            ad.setAdId(selectedAd);
            ad.setAdContent("Product A");
        } else if (selectedAd == "ad_2") {
            ad.setAdId(selectedAd);
            ad.setAdContent("Product B");
        } else if (selectedAd == "ad_3") {
            ad.setAdId(selectedAd);
            ad.setAdContent("Product C");
        } else {
            ad.setAdId("default_ad");
            ad.setAdContent("Default Advertisement");
        }
        return ad;
    }

    // This method would be called when a user clicks on the ad (feedback loop)
    public void updateAdReward(String userId, String adId, double reward) {

        mabAgent.updateReward(userId, adId, reward);

        // Optionally store the reward for each user
        userRewards.putIfAbsent(userId, new HashMap<>());
        userRewards.get(userId).put(adId, reward);
    }

    public Map<String, Map<String, Integer>> getUserAdSelections() {
        return mabAgent.getUserAdSelections();
    }

}