package com.codewithevans.swapi.dto;

import com.codewithevans.swapi.model.Person;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonResult {
    private int count;
    private Integer nextPage;
    private Integer prevPage;
    private List<Person> content = new ArrayList<>();
}
