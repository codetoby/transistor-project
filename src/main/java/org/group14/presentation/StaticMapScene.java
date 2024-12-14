package org.group14.presentation;

import org.group14.App;

import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * This class creates the static map scene.
 */
public class StaticMapScene {

    private static final SubScene mapScene;
    private static final Pane pane;

    /**
     * Create the static map scene.
     */
    static {

        pane = new Pane();
        Image image = new Image(App.class.getResourceAsStream("/org/group14/assets/map.png"));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(810);
        imageView.setFitHeight(693);

        pane.getChildren().add(imageView);

        pane.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));

        mapScene = new SubScene(pane, 810, 693);

    }

    private StaticMapScene() {
    }

    /**
     * Get the scene.
     * 
     * @return SubScene
     */
    public static SubScene getScene() {
        return mapScene;
    }

    /**
     * Get the pane.
     * 
     * @return Pane
     */
    public static Pane getPane() {
        return pane;
    }

}
