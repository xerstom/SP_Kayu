package com.boulec.kayu.services.off;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OFFProduct {
    @JsonProperty("id")
    public long barCode;
    @JsonProperty("generic_name_fr")
    public String name;
    @JsonProperty("energy_100g")
    float energy;
    @JsonProperty("saturated-fat_100g")
    float saturatedFat;
    @JsonProperty("sugars_100g")
    float sugars;
    @JsonProperty("salt_100g")
    float salt;
    @JsonProperty("fiber_100g")
    float fiber;
    @JsonProperty("proteins_100g")
    float proteins;

}
