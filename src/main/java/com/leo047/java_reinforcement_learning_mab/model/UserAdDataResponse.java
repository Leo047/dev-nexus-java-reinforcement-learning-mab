package com.leo047.java_reinforcement_learning_mab.model;

import lombok.Data;

import java.util.Map;

@Data
public class UserAdDataResponse {
    private Map<String, Integer> userAdSelections;
    private Map<String, Double> userAdRewards;

    public UserAdDataResponse(Map<String, Integer> userAdSelections, Map<String, Double> userAdRewards) {
        this.userAdSelections = userAdSelections;
        this.userAdRewards = userAdRewards;
    }


}