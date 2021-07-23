package org.cefim.patatemaison.controller;

import lombok.extern.slf4j.Slf4j;
import org.cefim.patatemaison.entity.Bar;
import org.cefim.patatemaison.entity.Reservation;
import org.cefim.patatemaison.exception.APIException;
import org.cefim.patatemaison.service.CocktailService;
import org.cefim.patatemaison.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

@Controller
@Slf4j
@RequestMapping("reservation")
public class ReservationController {
    @Autowired
    private CocktailService cocktailService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("newreservation")
    public String getNewReservation(
            Model model,
            @RequestParam(value = "barid", required = true) Long barid,
            @RequestParam(value = "barname", required = true) String barname
    ) throws APIException {
        model.addAttribute("reservations", new ArrayList<>());
        model.addAttribute("bar", new Bar(barid, barname));
        return "reservation";
    }

    @GetMapping("{id}")
    public String getReservationInfo(Model model, @PathVariable("id") int id) throws APIException {
        //model.addAttribute("cocktail", cocktailService.getCocktailInfo(id));
        return "reservation";
    }

    @PostMapping(path="savereservation", params="save")
    public String postReservationInBar(
            Model model,
            @RequestParam(value="barId") Long barId,
            @RequestParam(value="barName") String barName,
            @RequestParam(value="dateTime") String dateTime,
            @RequestParam(value="nbPerson") Integer nbPerson
    ) throws APIException {
        model.addAttribute("reservations", new ArrayList<>());
        Reservation reservation = new Reservation();
        reservation.setBar(new Bar(barId, barName));
        reservation.setNbPerson(nbPerson);
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setDateTime(LocalDateTime.parse(dateTime).atZone(ZoneId.systemDefault()));
        reservationService.addReservation(reservation);
        return "redirect:/bar/" + barId;
    }
}
