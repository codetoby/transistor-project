package org.group14.services.calculator;

import org.group14.domain.models.ZipCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO class to represent the travel information between the source and
 * destination
 */
@Getter
@AllArgsConstructor
public class TravelInformationDTO {

    private ZipCode origin;
    private ZipCode destination;
    private double walkingTime;
    private double bikeTime;
    private double arialDistance;

}
