package org.cefim.patatemaison.service;

import org.cefim.patatemaison.entity.Bar;
import org.cefim.patatemaison.entity.Cocktail;
import org.cefim.patatemaison.exception.APIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class CocktailService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${msrecherche.url.cocktailinfo}")
    private String cocktailInfoUrl;

    public Cocktail getCocktailInfo(int id) throws APIException {
        Cocktail cocktail = this.restTemplate.getForObject(String.format("%s/%d", cocktailInfoUrl, id), Cocktail.class);

        if (cocktail == null) {
            throw new APIException("Impossible de trouve le cocktail " + id, HttpStatus.NOT_FOUND);
        }

        /*Bar[] bars = this.restTemplate.getForObject(String.format("%s/%d", cocktailInfoUrl, id), Bar[].class);
        if(bars != null) {
            cocktail.setBars(Arrays.asList(bars));
        }*/

        return cocktail;
    }
}
