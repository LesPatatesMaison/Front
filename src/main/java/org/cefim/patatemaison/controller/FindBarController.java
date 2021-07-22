package org.cefim.patatemaison.controller;

import lombok.extern.slf4j.Slf4j;
import org.cefim.patatemaison.entity.Cocktail;
import org.cefim.patatemaison.exception.APIException;
import org.cefim.patatemaison.service.FindBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@Controller
@RequestMapping("/findbar")
public class FindBarController {

    @Autowired
    private FindBarService findBarService;


    @GetMapping()
    public String getEmpty(Model model) {
        model.addAttribute("cocktail", new Cocktail());
        model.addAttribute("bars", new ArrayList<>());
        return "findbar";
    }

    @PostMapping(params="find")
    public String getBarsWithCocktailName(Model model, @ModelAttribute("cocktail") Cocktail cocktail) throws APIException {
        model.addAttribute("bars", findBarService.getBarsWithCocktailName(cocktail.getStrDrink()));
        return "findbar";
    }

}
