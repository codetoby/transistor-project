package org.group14.presentation.controller;

import java.io.IOException;
import java.util.List;

import javafx.scene.control.ScrollPane;
import org.group14.App;
import org.group14.config.ConfigHandler;
import org.group14.domain.models.Coordinate;
import org.group14.domain.models.Point;
import org.group14.domain.models.ZipCode;
import org.group14.presentation.StaticMapScene;
import org.group14.presentation.interactive.InteractiveMapScene;
import org.group14.services.calculator.TravelInformationCalculator;
import org.group14.services.calculator.TravelInformationDTO;
import org.group14.services.routing.AbstractRoutingResponse;
import org.group14.services.routing.RouteService;
import org.group14.services.routing.bus.routing.responses.AbstractBusRoutingResponse;
import org.group14.services.routing.bus.BusRoutingEnum;
import org.group14.services.routing.bus.Item;
import org.group14.services.routing.bus.routing.responses.BusTransferRoutingResponse;
import org.group14.services.routing.bus.routing.responses.DirectBusRoutingResponse;
import org.group14.services.routing.bus.routing.responses.RouteItem;
import org.group14.services.routing.walk.WalkingRoutingResponse;
import org.group14.services.zipcode.ZipCodeController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.group14.utilities.Logger;

/**
 * This class contains the Primary Controller.
 */
public class PrimaryController {

    @FXML
    private TextField destinationCode;

    @FXML
    private Label destinationLabel;

    @FXML
    private TextField originCode;

    @FXML
    private Label statusLabel;

    @FXML
    private Label timeBikeLabel;

    @FXML
    private Label timeWalkLabel;

    @FXML
    private Label actualDistance;

    @FXML
    private Pane map;

    @FXML
    private Text errorMessage;

    private RouteService routeService;
    private TravelInformationCalculator travelInfoService;
    private ZipCodeController zipCodeController;

    private RightSideBarController rightSideBarController;

    public PrimaryController(RouteService routingService, ZipCodeController zipCodeController, TravelInformationCalculator travelInfoService) {
        this.routeService = routingService;
        this.zipCodeController = zipCodeController;
		this.travelInfoService = travelInfoService;
    }

    @FXML
    public void initialize() {
        errorMessage.setVisible(false);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/group14/fxml/rightSideBar.fxml"));
            Parent rightSideBar = loader.load();
            rightSideBarController = loader.getController();

            App.getSidebarPlaceholder().setContent(rightSideBar);

            App.getSidebarPlaceholder().setFitToHeight(true);
            App.getSidebarPlaceholder().setFitToWidth(true);
            App.getSidebarPlaceholder().setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        } catch (IOException e) {
            displayErrorMessage("Failed to load right side bar.");
        }
    }

    @FXML
    void calculate(MouseEvent event) throws IOException {
        removeObjectsFromScreen();
        errorMessage.setVisible(false);

        if (!validateConfiguration())
            return;

        try {
            TravelInformationDTO travelInfo = calculateTravelInformation();
            AbstractRoutingResponse routingResponse = fetchRoutingResponse(travelInfo);
            displayRoutingResponse(routingResponse);
            displayRoutingInformation(routingResponse);
        } catch (Exception e) {
            displayErrorMessage(e.getMessage());
        }
    }

    private boolean validateConfiguration() {
        ConfigHandler configHandler = ConfigHandler.getInstance();
        if (!configHandler.checkIfConfigLoaded()) {
            displayErrorMessage("Please configure the application first.");
            return false;
        }
        return true;
    }

    private TravelInformationDTO calculateTravelInformation() throws Exception {
        ZipCode origin = zipCodeController.getZipCode(originCode.getText());
        ZipCode destination = zipCodeController.getZipCode(destinationCode.getText());
        TravelInformationDTO travelInfo = travelInfoService.getTravelInformation(origin, destination);
        setTravelInformation(travelInfo, destination);
        return travelInfo;
    }

    private AbstractRoutingResponse fetchRoutingResponse(TravelInformationDTO travelInfo) throws Exception {
        return routeService.getRoute(
                travelInfo.getOrigin().getCoordinate(),
                travelInfo.getDestination().getCoordinate(),
                BusRoutingEnum.DIJKSTRA_V1);
    }

    private void removeObjectsFromScreen() {
    
        errorMessage.setVisible(false);
        StaticMapScene.getPane().getChildren().removeIf(e -> e instanceof Circle || e instanceof Line);
        rightSideBarController.getRoutesContainer().getChildren().clear();
    }

    private void displayRoutingResponse(AbstractRoutingResponse routingResponse) {

        if (routingResponse instanceof WalkingRoutingResponse) {
            displayWalkingRouteResponse();
            return;
        } else if (routingResponse instanceof AbstractBusRoutingResponse) {
            if (routingResponse instanceof DirectBusRoutingResponse) {
                displayDirectBusRoutingResponse((DirectBusRoutingResponse) routingResponse);
                return;
            } else if (routingResponse instanceof BusTransferRoutingResponse) {
                displayBusTransferRoutingResponse((BusTransferRoutingResponse) routingResponse);
                return;
            }
        }
        Logger.getInstance().log(
                "PrimaryController::displayRoutingResponse Could not read parse RoutingResponse because RoutingResponse: "
                        + routingResponse);

    }

    private void displayRoutingInformation(AbstractRoutingResponse routingResponse) throws IOException {
 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/group14/fxml/routeTransfers.fxml"));

        if (routingResponse instanceof WalkingRoutingResponse) {
           displayWalkingRouteInformation(loader, (WalkingRoutingResponse) routingResponse);
        } else if (routingResponse instanceof BusTransferRoutingResponse) {
            displayBusTransferRoutingInformation((BusTransferRoutingResponse) routingResponse);
        }
        
    }

    private void displayBusTransferRoutingInformation(BusTransferRoutingResponse routingResponse) throws IOException {
        List<Item> items = routingResponse.getItems();
        for (int i = 0; i < items.size() - 1; i++) {
            Item item = items.get(i);
            Item nexItem = items.get(i + 1);
    
            FXMLLoader itemLoader = new FXMLLoader(getClass().getResource("/org/group14/fxml/routeTransfers.fxml"));
            VBox routeInformations = itemLoader.load();
    
            RouteTransfersController controller = itemLoader.getController();
            controller.setValues(item, nexItem);
    
            rightSideBarController.getRoutesContainer().getChildren().add(routeInformations);
        }
    }
    

    private void displayWalkingRouteInformation(FXMLLoader loader, WalkingRoutingResponse routingResponse) throws IOException {
        VBox routeInformations = loader.load();
        RouteTransfersController controller = loader.getController();

        controller.setWalkRoutingInformation(routingResponse.getOrigin(), routingResponse.getDestination());
        rightSideBarController.getRoutesContainer().getChildren().add(routeInformations);
    }


    private void displayBusTransferRoutingResponse(BusTransferRoutingResponse routingResponse) {
        displayListOfCoordinates(routingResponse.getWalkPathFromOriginCoordinateToOriginStop(), Color.GREEN);
        displayListOfCoordinates(routingResponse.getWalkPathFromDestinationStopToDestinationCoordinate(), Color.BLUE);
        for (RouteItem routeItem : routingResponse.getRoutes()) {
            System.out.println(routeItem);
            displayListOfCoordinates(routeItem.coordinates(), routeItem.routeColor());
        }
    }

    private void displayDirectBusRoutingResponse(DirectBusRoutingResponse routingResponse) {
        displayListOfCoordinates(routingResponse.getWalkPathFromOriginCoordinateToOriginStop(), Color.GREEN);
        displayListOfCoordinates(routingResponse.getWalkPathFromDestinationStopToDestinationCoordinate());
        displayListOfCoordinates(routingResponse.getRoute().getListOfRouteItem(), Color.BLUE);
    }

    private void displayWalkingRouteResponse() {

    }

    private void displayListOfCoordinates(List<Coordinate> coordinates) {
        displayListOfCoordinates(coordinates, Color.BLACK);
    }

    private void displayListOfCoordinates(List<Coordinate> coordinates, Color color) {
        for (int i = 0; i < coordinates.size() - 1; i++) {
            displayLine(coordinates.get(i).toPoint(), coordinates.get(i + 1).toPoint(), color, 2);
        }
    }

    private void setTravelInformation(TravelInformationDTO travelInformationDTO, ZipCode destination) {
        timeBikeLabel.setText(travelInformationDTO.getBikeTime() + " min");
        timeWalkLabel.setText(travelInformationDTO.getWalkingTime() + " min");
        statusLabel.setText(travelInformationDTO.getArialDistance() + " km");
        destinationLabel.setText(destination.getCode());
    }

    private void displayErrorMessage(String message) {
        errorMessage.setVisible(true);
        errorMessage.setText(message);
    }

    private void displayLine(Point origin, Point destination, Color color, int width) {
        Line line = new Line(origin.getX(), origin.getY(), destination.getX(), destination.getY());
        line.setStroke(color.deriveColor(1, 1, 1, 0.5));
        line.setStrokeWidth(width);
        StaticMapScene.getPane().getChildren().add(line);
    }

    public void changeSubScene(SubScene scene) {
        App.changeScene(scene);
    }

    private boolean isStaticMapShown = true;

    @FXML
    public void switchScene(MouseEvent event) {
        if (isStaticMapShown) {
            changeSubScene(InteractiveMapScene.getScene());
        } else {
            changeSubScene(StaticMapScene.getScene());
        }
        isStaticMapShown = !isStaticMapShown;
    }

    @FXML
    private void handleOpenConfigPopup(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/group14/fxml/settings.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Configuration Popup");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            Image icon = new Image(getClass().getResourceAsStream("/org/group14/assets/settings.png"));
            stage.getIcons().add(icon);
            stage.showAndWait();
        } catch (IOException e) {
            Logger.getInstance().error("PrimaryController::handleOpenConfigPopup: " + e.getMessage());
        }
    }

}
