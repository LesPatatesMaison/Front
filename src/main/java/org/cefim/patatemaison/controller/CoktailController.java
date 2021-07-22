package org.cefim.patatemaison.controller;

import org.cefim.patatemaison.exception.APIException;
import org.cefim.patatemaison.service.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CoktailController {
    @Autowired
    private CocktailService cocktailService;

    @GetMapping("cocktail/{id}")
    public String getCocktailInfo(Model model, @PathVariable("id") int id) throws APIException {
        model.addAttribute("cocktail", cocktailService.getCocktailInfo(id));
        return "cocktailinfo";
    }
}
