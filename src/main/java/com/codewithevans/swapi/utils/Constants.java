package com.codewithevans.swapi.utils;

import com.codewithevans.swapi.model.Person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constants {

    public static Integer getValueFromUrl(String url, String pattern, String splitter){
        Matcher matcher = Pattern.compile(pattern).matcher(url);
        return matcher.find() ? Integer.parseInt(matcher.group().split(splitter)[1]) : 1;
    }

    public static Person mapResponseToPerson(Person person, int id) {
        person.setId(id);
        return person;
    }
}
