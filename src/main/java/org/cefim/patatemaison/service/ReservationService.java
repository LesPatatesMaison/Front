package org.cefim.patatemaison.service;

import org.cefim.patatemaison.entity.Cocktail;
import org.cefim.patatemaison.entity.Reservation;
import org.cefim.patatemaison.exception.APIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ReservationService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${msreservation.url.user.byId.reservation.recent}")
    private String reservationInfoUrl;

    @Value("${msreservation.url.reservation}")
    private String reservationUrl;

    public List<Reservation> getReservations(int id) throws APIException {
        Reservation[] reservationArray = this.restTemplate.getForObject(String.format(reservationInfoUrl, id), Reservation[].class);
        if (reservationArray == null) {
            //throw new APIException("Pas de reservation trouvée", HttpStatus.NOT_FOUND);
            return new ArrayList<>();
        }
        return Arrays.asList(reservationArray);
    }

    public void addReservation(Reservation reservation) {
        Reservation resa =
                restTemplate.postForObject(reservationUrl, reservation, Reservation.class);
    }
}
