package org.group14.presentation.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import lombok.Getter;
import lombok.Setter;
import org.group14.presentation.controller.eventhandler.RouteBlockSelectHandler;
import org.group14.services.routing.bus.routing.database_query.model.StopKey;

/**
 * This class is responsible for the route block.
 */
public class RouteBlockController {

    @Setter
    @Getter
    private StopKey stopKey;

    @FXML 
    public Label originStopNameLabel;

    @FXML
    public Label totalTimeLabel;

    @FXML 
    public Label destinationStopNameLabel;

    @FXML
    public Label originToBusStopLabel;

    @FXML
    public Label busStopToDestinationLabel;

    @FXML
    public Circle wheelChairStatus;

    @FXML
    public Circle bikeAllowedStatus;

    @FXML
    public Label departureTimeLabel;

    @FXML
    private Label routeLabel;

    @FXML
    private Label distanceLabel;

    @FXML
    private Label durationLabel;

    @FXML
    private Button selectButton;

    @Setter
    private RouteBlockSelectHandler selectHandler;

    // Define a method to handle the click event of the select button
    @FXML
    void onSelectButtonClick(ActionEvent event) {
    }

    public void setOriginStopName(String originStopName) {
        originStopNameLabel.setText("From: " + originStopName);
    }

    public void setDestinationStopName(String destinationStopName) {
        destinationStopNameLabel.setText("To: " + destinationStopName);
    }

    public void setOriginToBusStop(double d) {
        originToBusStopLabel.setText("Walk To Stop: " + d + " min");
    }

    public void setBusStopToDestination(double d) {
        busStopToDestinationLabel.setText("Walk From Stop: " + d + " min");
    }

    public void setRoute(String route) {
        routeLabel.setText("Bus: " + route);
    }

    public void setDistance(float distance) {
        distanceLabel.setText("Distance: " + distance + " km");
    }

    public void setDuration(String duration) {
        durationLabel.setText("Duration: " + duration + " min");
    }

    public void setDepartureTimeLabel(String departureTime) {
        this.departureTimeLabel.setText("Departure Time: " + departureTime);
    }

    public void setWheelChairStatus(boolean status) {
        if (status) {
            wheelChairStatus.setFill(Color.GREEN);
        } else {
            wheelChairStatus.setFill(Color.RED);
        }
    }

    public void setBikeAllowedStatus(boolean status) {
        if (status) {
            bikeAllowedStatus.setFill(Color.GREEN);
        } else {
            bikeAllowedStatus.setFill(Color.RED);
        }
    }

    @FXML
    private void handleSelectButtonClick() {
        if (selectHandler != null) {
            selectHandler.onRouteBlockSelect(this);
        }
    }

    public void setTotalTime(String d) {
        totalTimeLabel.setText("Total Time: " + d + " min");
    }

    public void setValues(org.group14.services.routing.bus.routing.database_query.model.PossibleTrip possibleTrip, StopKey stopKey) {
        setRoute(possibleTrip.getTrip().getTripHeadsign());
        setDistance(Float.parseFloat(possibleTrip.getTotalDistance()) / 1000);
        setDepartureTimeLabel(possibleTrip.getDepartureTime());
        setDuration(possibleTrip.getTotalTime());
        setWheelChairStatus(possibleTrip.getTrip().getWheelchairAccessible() != 0);
        setBikeAllowedStatus(possibleTrip.getTrip().getBikesAllowed() != 0);
        setStopKey(stopKey);
        setOriginStopName(possibleTrip.getStartStop().getStopName());
        setDestinationStopName(possibleTrip.getEndStop().getStopName());
        // setOriginToBusStop(possibleTrip.getDistanceFromOriginToBusStop());
        // setBusStopToDestination(possibleTrip.getDistanceFromBusStopToDestination());
        setTotalTime(possibleTrip.getTotalTime());
    }
}