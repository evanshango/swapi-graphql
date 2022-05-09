package com.codewithevans.swapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Planet {
    private String name;
    private String population;
    private String terrain;
    private String climate;
    private String diameter;
    private String gravity;
}
