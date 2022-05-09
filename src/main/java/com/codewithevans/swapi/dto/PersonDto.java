package com.codewithevans.swapi.dto;

import com.codewithevans.swapi.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDto {
    private int id;
    private String name;
    private String birthYear;
    private String eyeColor;
    private List<Film> films = new ArrayList<>();
    private String gender;
    private String hairColor;
    private String height;
    private Planet planet;
    private String mass;
    private String skinColor;
    private String created;
    private String edited;
    private List<Specie> species = new ArrayList<>();
    private List<Starship> starships = new ArrayList<>();
    private List<Vehicle> vehicles;
}
