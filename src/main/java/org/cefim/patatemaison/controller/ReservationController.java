package org.cefim.patatemaison.controller;

import lombok.extern.slf4j.Slf4j;
import org.cefim.patatemaison.dto.ReservationDto;
import org.cefim.patatemaison.entity.Bar;
import org.cefim.patatemaison.entity.Reservation;
import org.cefim.patatemaison.entity.User;
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
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("list/{id}")
    public String getReservationInfo(Model model, @PathVariable("id") int userId) throws APIException {
        List<Reservation> reservations = reservationService.getReservations(userId);

        List<ReservationDto> reservationsDto = reservations.stream().map(resa -> {
            return new ReservationDto(
                    resa.getId(),
                    resa.getBar(),
                    resa.getDateTime().toLocalDateTime(),
                    String.format(
                            "%02d/%02d/%04d",
                            resa.getDateTime().toLocalDateTime().getDayOfMonth(),
                            resa.getDateTime().toLocalDateTime().getMonthValue(),
                            resa.getDateTime().toLocalDateTime().getYear()
                    ),
                    resa.getDateTime().toLocalDateTime().plusHours(2).getHour() + ":" + resa.getDateTime().toLocalDateTime().getMinute(),
                    resa.getNbPerson()
            );
        }).sorted().collect(Collectors.toList());

        model.addAttribute("reservations", reservationsDto);
        return "reservation-list";
    }

    @PostMapping(path="savereservation", params="save")
    public String postReservationInBar(
            Model model,
            @RequestParam(value="barId") Long barId,
            @RequestParam(value="barName") String barName,
            @RequestParam(value="userId") Long userId,
            @RequestParam(value="dateTime") String dateTime,
            @RequestParam(value="nbPerson") Integer nbPerson
    ) throws APIException {
        model.addAttribute("reservations", new ArrayList<>());

        User user = new User();
        user.setId(userId);

        Reservation reservation = new Reservation();
        reservation.setBar(new Bar(barId, barName));
        reservation.setNbPerson(nbPerson);
        reservation.setUser(user);
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setDateTime(LocalDateTime.parse(dateTime).atZone(ZoneId.systemDefault()));
        reservationService.addReservation(reservation);
        return "redirect:/reservation/list/" + userId;
    }
}
