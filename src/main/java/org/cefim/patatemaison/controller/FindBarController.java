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
import java.util.Map;

@Slf4j
@Controller
public class FindBarController {

    @Autowired
    private BarService barService;

    @GetMapping()
    public String home() {
        return "home";
    }

    @GetMapping("cocktailForm")
    public String getCocktailForm(Model model, @RequestParam(required = false) String search) throws APIException {
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

    @GetMapping("nameForm")
    public String getBarNameForm(Model model, @RequestParam(required = false) String search) throws APIException {
        model.addAttribute("bar", new Bar());
        if(search == null)
        {
            model.addAttribute("bars", new ArrayList<>());
        }
        else
        {
            List<Bar> bars = barService.getBarWithName(search);
            model.addAttribute("bars", bars);
            model.addAttribute("previousBarSearch", search);
            if(bars.size() == 0)
            {
                model.addAttribute("message", "Aucun bar correspondant à votre recherche");
            }
        }
        return "findbarbyname";
    }

    @GetMapping("bar/{id}")
    public String getBarInfo(Model model, @PathVariable("id") int id, @RequestParam Map<String, String> allRequestParams) throws APIException {
        model.addAttribute("bar", barService.getBarInfo(id));
        if(allRequestParams.containsKey("previousBarSearch")) {
            model.addAttribute("previousBarSearch", allRequestParams.get("previousBarSearch"));
        }
        else if(allRequestParams.containsKey("previousCocktailSearch")) {
            model.addAttribute("previousCocktailSearch", allRequestParams.get("previousCocktailSearch"));
        }
        else {
            model.addAttribute("noPreviousSearch", true);
        }
        return "barinfo";
    }

    @PostMapping(path="cocktailForm", params="find")
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

    @PostMapping(path="nameForm", params="find")
    public String getBarsWithName(Model model, @ModelAttribute("bar") Bar bar) throws APIException {
        List<Bar> bars = barService.getBarWithName(bar.getName());
        model.addAttribute("bars", bars);
        model.addAttribute("previousBarSearch", bar.getName());
        if(bars.size() == 0)
        {
            model.addAttribute("message", "Aucun bar ne correspond à votre recherche");
        }
        return "findbarbyname";
    }

}
