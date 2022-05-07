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
public class ResponseDto {
    private int count;
    private String next;
    private String previous;
    private List<Person> results;
}
