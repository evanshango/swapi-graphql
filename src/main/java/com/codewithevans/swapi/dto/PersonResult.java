package com.codewithevans.swapi.dto;

import com.codewithevans.swapi.model.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonResult {
    private int count;
    private Integer nextPage;
    private Integer prevPage;
    private List<Person> content;
}
