package org.cefim.patatemaison.controller;

import org.cefim.patatemaison.entity.Bar;
import org.cefim.patatemaison.exception.APIException;
import org.cefim.patatemaison.service.BarService;
import org.cefim.patatemaison.service.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CoktailController {
    @Autowired
    private CocktailService cocktailService;

    @Autowired
    private BarService barService;

    @GetMapping("cocktail/{id}")
    public String getCocktailInfo(Model model, @PathVariable("id") int id, @RequestParam(required = false) Integer bar) throws APIException {
        model.addAttribute("cocktail", cocktailService.getCocktailInfo(id));

        List<Bar> bars = barService.getBarsWithCocktailId(id);
        model.addAttribute("bars", bars);
        if(bars.size() == 0)
        {
            model.addAttribute("message", "Aucun bar ne propose le cocktail souhaité");
        }
        if(bar != null)
        {
            model.addAttribute("previousBar", bar);
        }
        return "cocktailinfo";
    }
}
