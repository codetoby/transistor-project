package org.group14.presentation.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * This class is responsible for the right side bar.
 */
public class RightSideBarController {

    @FXML
    private VBox routesContainer;
    public VBox getRoutesContainer() {
        return routesContainer;
    }
    public void clearRoutes() {
        routesContainer.getChildren().clear();
    }
}
