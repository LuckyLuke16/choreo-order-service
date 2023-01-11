package com.example.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {

    private String street;

    private String houseNumber;

    private String postcode;

    private String city;
}
