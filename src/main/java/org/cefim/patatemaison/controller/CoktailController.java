package org.cefim.patatemaison.controller;

import org.cefim.patatemaison.exception.APIException;
import org.cefim.patatemaison.service.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CoktailController {
    @Autowired
    private CocktailService cocktailService;

    @GetMapping("cocktail/{id}")
    public String getCocktailInfo(Model model, @PathVariable("id") int id, @RequestParam(required = false) Integer bar) throws APIException {
        model.addAttribute("cocktail", cocktailService.getCocktailInfo(id));
        if(bar != null)
        {
            model.addAttribute("previousBar", bar);
        }
        return "cocktailinfo";
    }
}
