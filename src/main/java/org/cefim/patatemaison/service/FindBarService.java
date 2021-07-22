package org.cefim.patatemaison.service;

import org.cefim.patatemaison.entity.Bar;
import org.cefim.patatemaison.exception.APIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class FindBarService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${msrecherche.url.findbars}")
    private String findBarUrl;

    public List<Bar> getBarsWithCocktailName(String name) throws APIException {
        Bar[] barArray = this.restTemplate.getForObject(String.format("%s/%s", findBarUrl, name), Bar[].class);

        if (barArray == null) {
            throw new APIException("Pas de Bar trouvé", HttpStatus.NOT_FOUND);
        }

        return Arrays.asList(barArray);
    }
}
