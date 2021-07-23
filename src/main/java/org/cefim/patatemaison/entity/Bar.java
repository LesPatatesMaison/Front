package org.cefim.patatemaison.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Bar {

    private Long id;

    private String name;

    private String category;

    private String speciality;

    private String phone;

    private String website;

    private String email;

    private String address;

    private String postcode;

    private String city;

    private List<Cocktail> cocktails;

    public Bar (Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
