package org.cefim.patatemaison.service;

import org.cefim.patatemaison.entity.Cocktail;
import org.cefim.patatemaison.exception.APIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CocktailService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${msrecherche.url.cocktailinfo}")
    private String cocktailInfoUrl;

    @Value("${msrecherche.url.findcocktails}")
    private String cocktailByNameUrl;

    public Cocktail getCocktailInfo(int id) throws APIException {
        Cocktail cocktail = this.restTemplate.getForObject(String.format("%s/%d", cocktailInfoUrl, id), Cocktail.class);

        if (cocktail == null) {
            throw new APIException("Impossible de trouve le cocktail " + id, HttpStatus.NOT_FOUND);
        }

        return cocktail;
    }

    public List<Cocktail> getCocktailByName(String name) throws APIException {
        Cocktail[] cocktailArray = this.restTemplate.getForObject(String.format("%s%s", cocktailByNameUrl, name), Cocktail[].class);
        if (cocktailArray == null) {
            throw new APIException("Pas de cocktail trouvé", HttpStatus.NOT_FOUND);
        }

        return Arrays.asList(cocktailArray);
    }
}
