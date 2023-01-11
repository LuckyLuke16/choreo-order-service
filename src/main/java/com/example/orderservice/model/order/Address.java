package com.example.orderservice.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String street;

    private String houseNumber;

    private String postcode;

    private String city;
}
