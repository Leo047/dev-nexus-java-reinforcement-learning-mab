package com.leo047.java_reinforcement_learning_mab.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdContent {

    public AdContent(String adId, String adText) {
    }

    public AdContent() {
    }

    private String adId;
    private String adContent;
    private String adText;
    private String adImage;
    private String adUrl;


}
