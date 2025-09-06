package com.ecommerce.user.model;



import lombok.Data;

@Data
public class Address {
    private Long id;
    private String street;
    private String city;
    private String country;
    private String zipcode;
}

