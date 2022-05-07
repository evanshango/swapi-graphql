package com.codewithevans.swapi.resolver;

import com.codewithevans.swapi.dto.PersonResult;
import com.codewithevans.swapi.dto.ResponseDto;
import com.codewithevans.swapi.model.Person;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonResolver implements GraphQLQueryResolver {

    @Value("${app.swapi-base-url}")
    private String swapiBaseUrl;

    private final RestTemplate restTemplate;

    public PersonResult getPersons(int page, String name) {
        String url = swapiBaseUrl + "/people/";
        UriComponentsBuilder builder;

        if (!name.isEmpty()) {
            builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("search", name);
        } else {
            builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("page", page);
        }

        ResponseEntity<ResponseDto> response = restTemplate.getForEntity(builder.toUriString(), ResponseDto.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null)
            throw new ResourceAccessException("An error occurred while trying to fetch persons");

        ResponseDto responseDto = response.getBody();

        Integer prevPage = responseDto.getPrevious() != null ? getPage(responseDto.getPrevious()) : null;
        Integer nextPage = responseDto.getNext() != null ? getPage(responseDto.getNext()) : null;

        return PersonResult.builder().count(responseDto.getCount()).prevPage(prevPage).nextPage(nextPage).content(
                responseDto.getResults().stream().map(this::mapResponseToPerson).collect(Collectors.toList())
        ).build();
    }

    public Person getPerson(int personId) {
        return new Person();
    }

    private Person mapResponseToPerson(Person person) {
        person.setId(getPersonId(person.getUrl()));
        return person;
    }

    private Integer getPage(String url) {
        final String PAGE_PATTERN = "(page=\\d*)";
        Matcher matcher = Pattern.compile(PAGE_PATTERN).matcher(url);
        return matcher.find() ? Integer.parseInt(matcher.group().split("=")[1]) : 1;
    }

    private Integer getPersonId(String url) {
        final String ID_PATTERN = "(people/\\d*)";
        Matcher matcher = Pattern.compile(ID_PATTERN).matcher(url);
        return matcher.find() ? Integer.parseInt(matcher.group().split("/")[1]) : 1;
    }
}
