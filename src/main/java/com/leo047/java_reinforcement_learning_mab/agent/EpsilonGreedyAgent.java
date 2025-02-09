package com.leo047.java_reinforcement_learning_mab.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class EpsilonGreedyAgent {

    private static final Logger logger = LoggerFactory.getLogger(EpsilonGreedyAgent.class);

    private final double epsilon = 0.2; // Exploration rate (0.1 = 10% Exploration and 0.9 = 90% Exploitation)
    private final Map<String, Map<String, Double>> userAdRewards; // Stores the cumulative reward for each ad for each user
    private final Map<String, Map<String, Integer>> userAdSelections; // Stores the number of times each ad has been shown to each user
    private final Random rand;

    public EpsilonGreedyAgent() {
        userAdSelections = new HashMap<>();
        userAdRewards = new HashMap<>();
        rand = new Random();
    }

    private void initializeUserAds(String userId) {
        userAdRewards.putIfAbsent(userId, new HashMap<>());
        userAdSelections.putIfAbsent(userId, new HashMap<>());

        Map<String, Double> adRewards = userAdRewards.get(userId);
        Map<String, Integer> adSelections = userAdSelections.get(userId);

        if (adRewards.isEmpty() || adSelections.isEmpty()) {
            for (String adId : List.of("default_ad", "ad_1", "ad_2", "ad_3")) {
                adRewards.put(adId, 0.0);
                adSelections.put(adId, 0);
            }
        }
        userAdRewards.put(userId, adRewards);
        userAdSelections.put(userId, adSelections);
    }

    // Epsilon-Greedy Algorithm
    public String selectAd(String userId) {
        initializeUserAds(userId);
        double randValue = rand.nextDouble();
        logger.info("Random Number: {}", randValue);
        if (randValue < epsilon) {
            return getRandomAd(userId);  // Exploration: randomly select an ad
        } else {
            return getBestAd(userId);  // Exploitation: choose the ad with the highest reward
        }
    }

    // Get the ad with the highest reward (CTR)
    private String getBestAd(String userId) {
        // CTR = Total Clicks(Reward) / Total Impressions(Selections)

        String ad = userAdRewards.get(userId).entrySet().stream()
                .max(Comparator
                        .comparingDouble(e -> e.getValue() /
                                (userAdSelections.get(userId).get(e.getKey()) + 1e-6)))
                // Add 1e-6 to avoid division by zero
                .map(Map.Entry::getKey)
                .orElse("default_ad"); // Default ad if no ad is found
        return ad;
    }

    // Randomly select an ad for exploration
    private String getRandomAd(String user) {
        List<String> ads = new ArrayList<>(userAdRewards.get(user).keySet());
        if (ads.isEmpty()) {
            return "default_ad"; // Show Default ad if no ad is found
        }
        String randomAd = ads.get(rand.nextInt(ads.size()));
        logger.info("Getting Random Ad: {}", randomAd);
        return randomAd;
    }

    // Update the reward for a given ad
    public void updateReward(String userId, String adId, double reward) {
        if (userAdSelections.get(userId) == null) {
            initializeUserAds(userId);
        }

        Map<String, Integer> adSelections = userAdSelections.get(userId);
        Map<String, Double> adRewards = userAdRewards.get(userId);

        adSelections.put(adId, adSelections.getOrDefault(adId, 0) + 1); // Increment the selection count
        adRewards.put(adId, adRewards.getOrDefault(adId, 0.0) + reward);

        userAdSelections.put(userId, adSelections);
        userAdRewards.put(userId, adRewards);
    }

    public Map<String, Map<String, Integer>> getUserAdSelections() {
        return userAdSelections;
    }

    public Map<String, Map<String, Double>> getUserAdRewards() {
        return userAdRewards;
    }
}