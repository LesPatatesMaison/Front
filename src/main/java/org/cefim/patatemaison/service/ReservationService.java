package org.cefim.patatemaison.service;

import org.cefim.patatemaison.entity.Cocktail;
import org.cefim.patatemaison.entity.Reservation;
import org.cefim.patatemaison.exception.APIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${msreservation.url.reservation}")
    private String reservationInfoUrl;

    public List<Reservation> getReservations() throws APIException {

        return new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {

    }
}
