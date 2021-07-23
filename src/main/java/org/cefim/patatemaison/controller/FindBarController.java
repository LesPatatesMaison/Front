package org.cefim.patatemaison.controller;

import lombok.extern.slf4j.Slf4j;
import org.cefim.patatemaison.entity.Bar;
import org.cefim.patatemaison.entity.Cocktail;
import org.cefim.patatemaison.exception.APIException;
import org.cefim.patatemaison.service.BarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class FindBarController {

    @Autowired
    private BarService barService;


    @GetMapping()
    public String getEmpty(Model model, @RequestParam(required = false) String search) throws APIException {
        model.addAttribute("cocktail", new Cocktail());
        if(search == null)
        {
            model.addAttribute("bars", new ArrayList<>());
        }
        else
        {
            List<Bar> bars = barService.getBarsWithCocktailName(search);
            model.addAttribute("bars", bars);
            model.addAttribute("previousCocktailSearch", search);
            if(bars.size() == 0)
            {
                model.addAttribute("message", "Aucun bar ne propose le cocktail souhaité");
            }
        }
        return "findbar";
    }

    @GetMapping("bar/{id}")
    public String getBarInfo(Model model, @PathVariable("id") int id, @RequestParam(required = false) String search) throws APIException {
        model.addAttribute("bar", barService.getBarInfo(id));
        if(search != null) {
            model.addAttribute("previousSearch", search);
        }
        return "barinfo";
    }

    @PostMapping(params="find")
    public String getBarsWithCocktailName(Model model, @ModelAttribute("cocktail") Cocktail cocktail) throws APIException {
        List<Bar> bars = barService.getBarsWithCocktailName(cocktail.getStrDrink());
        model.addAttribute("bars", bars);
        model.addAttribute("previousCocktailSearch", cocktail.getStrDrink());
        if(bars.size() == 0)
        {
            model.addAttribute("message", "Aucun bar ne propose le cocktail souhaité");
        }
        return "findbar";
    }

}
