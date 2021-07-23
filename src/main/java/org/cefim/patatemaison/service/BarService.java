package org.cefim.patatemaison.service;

import org.cefim.patatemaison.entity.Bar;
import org.cefim.patatemaison.entity.Cocktail;
import org.cefim.patatemaison.exception.APIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BarService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${msrecherche.url.findbars}")
    private String findBarUrl;

    @Value("${msrecherche.url.findbarscocktailid}")
    private String findBarCocktailIdUrl;

    @Value("${msrecherche.url.barinfo}")
    private String barInfoUrl;

    @Value("${msrecherche.url.barinfococktails}")
    private String barInfoCocktailsUrl;

    public List<Bar> getBarsWithCocktailName(String name) throws APIException {
        System.out.println(String.format("%s%s", findBarUrl, name));
        Bar[] barArray = this.restTemplate.getForObject(String.format("%s%s", findBarUrl, name), Bar[].class);

        if (barArray == null) {
            throw new APIException("Pas de Bar trouvé", HttpStatus.NOT_FOUND);
        }

        return Arrays.asList(barArray);
    }

    public List<Bar> getBarsWithCocktailId(int cocktailId) throws APIException {
        Bar[] barArray = this.restTemplate.getForObject(String.format("%s%d", findBarCocktailIdUrl, cocktailId), Bar[].class);

        if (barArray == null) {
            throw new APIException("Pas de Bar trouvé", HttpStatus.NOT_FOUND);
        }

        return Arrays.asList(barArray);
    }

    public Bar getBarInfo(int id) throws APIException {
        Bar bar = this.restTemplate.getForObject(String.format("%s/%d", barInfoUrl, id), Bar.class);

        if (bar == null) {
            throw new APIException("Impossible de trouve le bar " + id, HttpStatus.NOT_FOUND);
        }

        Cocktail[] cocktails = this.restTemplate.getForObject(String.format("%s/%d", barInfoCocktailsUrl, id), Cocktail[].class);
        if(cocktails != null) {
            bar.setCocktails(Arrays.asList(cocktails));
        }

        return bar;
    }
}
