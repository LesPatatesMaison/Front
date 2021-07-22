package org.cefim.patatemaison.controller;

import lombok.extern.slf4j.Slf4j;
import org.cefim.patatemaison.entity.Cocktail;
import org.cefim.patatemaison.exception.APIException;
import org.cefim.patatemaison.service.BarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@Controller
public class FindBarController {

    @Autowired
    private BarService barService;


    @GetMapping()
    public String getEmpty(Model model) {
        model.addAttribute("cocktail", new Cocktail());
        model.addAttribute("bars", new ArrayList<>());
        return "findbar";
    }

    @GetMapping("bar/{id}")
    public String getBarInfo(Model model, @PathVariable("id") int id) throws APIException {
        model.addAttribute("bar", barService.getBarInfo(id));
        return "barinfo";
    }

    @PostMapping(params="find")
    public String getBarsWithCocktailName(Model model, @ModelAttribute("cocktail") Cocktail cocktail) throws APIException {
        model.addAttribute("bars", barService.getBarsWithCocktailName(cocktail.getStrDrink()));
        return "findbar";
    }

}
