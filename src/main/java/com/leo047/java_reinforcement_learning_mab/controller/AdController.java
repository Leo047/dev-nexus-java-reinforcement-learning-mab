package com.leo047.java_reinforcement_learning_mab.controller;

import com.leo047.java_reinforcement_learning_mab.model.AdContent;
import com.leo047.java_reinforcement_learning_mab.service.AdService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/api/v1/ads")
public class AdController {

    private static final Logger logger = LoggerFactory.getLogger(AdController.class);

    private final AdService adService;

    @Autowired
    public AdController(AdService adService) {
        this.adService = adService;
    }

    @GetMapping("/getAd")
    @ResponseBody
    public AdContent getPersonalizedAd(@RequestParam("userId") String userId) {
        return adService.getAdForUser(userId);
    }

    @PostMapping("/reward")
    @ResponseBody
    public void updateReward(@RequestParam("userId") String userId,
                             @RequestParam("adId") String adId) {
        adService.updateAdReward(userId, adId, 1.0); // Reward = 1.0 for Click
    }

    @GetMapping("/performance")
    public String getUserAdData(Model model) {
        model.addAttribute("userAdSelections", adService.getUserAdSelections());
        return "useraddata";
    }
}
