package com.codewithevans.swapi.resolver;

import com.codewithevans.swapi.dto.PersonDto;
import com.codewithevans.swapi.dto.PersonResult;
import com.codewithevans.swapi.dto.ResponseDto;
import com.codewithevans.swapi.model.Person;
import com.codewithevans.swapi.model.Planet;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.codewithevans.swapi.utils.Constants.getValueFromUrl;
import static com.codewithevans.swapi.utils.Constants.mapResponseToPerson;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonListResolver implements GraphQLQueryResolver {

    @Value("${app.swapi-base-url}")
    private String swapiBaseUrl;

    private final RestTemplate restTemplate;
    private final ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public PersonDto person(int personId) {
        String url = swapiBaseUrl + "/people/" + personId;

        try {
            ResponseEntity<Person> response = restTemplate.getForEntity(url, Person.class);
            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null)
                return new PersonDto();
            Person person = personResponse(response.getBody());
            return fetchPlanet(person.getPlanet(), person).get();
        } catch (Exception ex) {
            log.info("An error occurred while trying to fetch person");
            return new PersonDto();
        }
    }

    public PersonResult persons(int page, String name) {
        String url = swapiBaseUrl + "/people/";
        UriComponentsBuilder builder = !name.isEmpty()
                ? fromHttpUrl(url).queryParam("search", name)
                : fromHttpUrl(url).queryParam("page", page);

        try {
            ResponseEntity<ResponseDto> response = restTemplate.getForEntity(builder.toUriString(), ResponseDto.class);

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null)
                return new PersonResult();

            ResponseDto responseDto = response.getBody();

            Integer prevPage = responseDto.getPrevious() != null ? getPage(responseDto.getPrevious()) : null;
            Integer nextPage = responseDto.getNext() != null ? getPage(responseDto.getNext()) : null;

            return PersonResult.builder().count(responseDto.getCount()).prevPage(prevPage).nextPage(nextPage)
                    .content(responseDto.getResults().stream().map(this::personResponse)
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception ex) {
            log.info("An error occurred while fetching Persons from the api {}", ex.getMessage());
            return new PersonResult();
        }
    }

    private CompletableFuture<PersonDto> fetchPlanet(String homeWorld, Person person) {
        return CompletableFuture.supplyAsync(() -> {
            Planet planet;
            try {
                ResponseEntity<Planet> response = restTemplate.getForEntity(homeWorld, Planet.class);
                if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null)
                    planet = new Planet();
                else
                    planet = response.getBody();
            } catch (Exception e) {
                log.info("An error occurred while fetching planet for person with id {}", person.getId());
                planet = new Planet();
            }
            return PersonDto.builder().id(person.getId()).name(person.getName()).birthYear(person.getBirthYear())
                    .eyeColor(person.getEyeColor()).gender(person.getGender()).hairColor(person.getHairColor())
                    .height(person.getHeight()).mass(person.getMass()).skinColor(person.getSkinColor()).planet(planet)
                    .created(person.getCreated()).edited(person.getEdited()).vehicles(person.getVehicles())
                    .films(person.getFilms()).build();
        }, executorService);
    }

    private Person personResponse(Person res) {
        return mapResponseToPerson(res, getValueFromUrl(res.getUrl(), "(people/\\d*)", "/"));
    }

    private Integer getPage(String url) {
        return getValueFromUrl(url, "(page=\\d*)", "=");
    }
}
