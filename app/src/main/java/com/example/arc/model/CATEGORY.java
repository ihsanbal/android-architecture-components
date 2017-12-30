package com.example.arc.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author ihsan on 12/18/17.
 */

enum CATEGORY {
    @SerializedName("general")
    GENERAL,
    @SerializedName("business")
    BUSINESS,
    @SerializedName("technology")
    TECHNOLOGY,
    @SerializedName("sport")
    SPORT,
    @SerializedName("politics")
    POLITICS,
    @SerializedName("entertainment")
    ENTERTAINMENT,
    @SerializedName("gaming")
    GAMING,
    @SerializedName("health-and-medical")
    HEALTH_MEDICAL,
    @SerializedName("music")
    MUSIC,
    @SerializedName("science-and-nature")
    SCIENCE_NATURE
}
