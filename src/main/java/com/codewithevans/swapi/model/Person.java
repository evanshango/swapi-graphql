package com.codewithevans.swapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {
    private int id;
    @JsonProperty("birth_year")
    private String birthYear;
    @JsonProperty("eye_color")
    private String eyeColor;
    private List<Film> films;
    private String gender;
    @JsonProperty("hair_color")
    private String hairColor;
    private String height;
    @JsonProperty("homeworld")
    private String homeWorld;
    private String mass;
    private String name;
    @JsonProperty("skin_color")
    private String skinColor;
    private String created;
    private String edited;
    private List<Specie> species;
    private List<Starship> starships;
    private String url;
    private List<Vehicle> vehicles;
}
