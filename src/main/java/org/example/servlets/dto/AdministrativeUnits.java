package org.example.servlets.dto;

import lombok.AllArgsConstructor;


@AllArgsConstructor

public class AdministrativeUnits {
    private Integer id;
    private String name;
    private String administrativeRegion;
    private Integer population;
    private Double area;

}