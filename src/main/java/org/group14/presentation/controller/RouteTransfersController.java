package org.group14.presentation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import org.group14.domain.models.Coordinate;
import org.group14.services.routing.bus.Item;

public class RouteTransfersController {

    @FXML
    public Label originStopNameLabel;

    @FXML
    public Label destinationStopNameLabel;

    @FXML
    public Label arrivalTimeLabel;

    @FXML
    public Label routeLabel;


    public void setOriginStopName(String originStopName) {
        originStopNameLabel.setText("From: " + originStopName);
    }

    public void setDestinationStopName(String destinationStopName) {
        destinationStopNameLabel.setText("To: " + destinationStopName);
    }

    public void setRoute(String route) {
        routeLabel.setText("Transport: " + route);
    }

    public void setArrivalTimeLabel(String arrivalTime) {
        arrivalTimeLabel.setText("Arrival Time: " + arrivalTime);
    }

    public void setValues(Item origin, Item destination) {
        setOriginStopName(origin.getStop().getStopName());
        setDestinationStopName(destination.getStop().getStopName());

        if (destination.getTripId() == null || destination.getTripId().equals("walk")) {
            setRoute("Walk");
        } else {
            setRoute(destination.getHeadSign());
        }

        if (destination.getTime() == null) {
            setArrivalTimeLabel("?");
        } else {
            setArrivalTimeLabel(destination.getTime().toString());
        }

    }

    public void setWalkRoutingInformation(Coordinate origin, Coordinate destination) {
        
    }
}
