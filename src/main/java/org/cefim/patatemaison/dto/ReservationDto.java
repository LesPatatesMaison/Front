package org.cefim.patatemaison.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.cefim.patatemaison.entity.Bar;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private Bar bar;
    private LocalDateTime dateTime;
    private String date;
    private String time;
    private Integer nbPerson;
}
