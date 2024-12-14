package org.group14.presentation.interactive;

import javafx.scene.SubScene;
import javafx.scene.layout.Pane;

/**
 * The scene that contains the interactive map.
 */
public class InteractiveMapScene {

    private static final Pane pane = new Pane();
    private static final SubScene mapScene = new SubScene(pane, 764, 791);

    static {
        pane.getChildren().add(MapViewExtended.getMapView());
    }

    private InteractiveMapScene() {
    }

    public static SubScene getScene() {
        return mapScene;
    }
}
