package org.group14;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

import org.group14.config.spring.AppConfig;
import org.group14.presentation.StaticMapScene;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * JavaFX App
 */
public class App extends Application {

    private ApplicationContext applicationContext;

    private static Stage stage;
    private static BorderPane pane;
    private static ScrollPane sidebarPlaceholder;

    static {
        pane = new BorderPane();
        sidebarPlaceholder = new ScrollPane();
        sidebarPlaceholder.setFitToWidth(true);
    }

    @SuppressWarnings("exports")
    public static ScrollPane getSidebarPlaceholder() {
        return sidebarPlaceholder;
    }

    /**
     * Start the application
     *
     * @param stage
     * @throws IOException
     */
    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws IOException {

        try {
            setStage(stage);

            pane.setCenter(StaticMapScene.getScene());
            FXMLLoader loader = loadFXML("primary");
            loader.setControllerFactory(applicationContext::getBean);
            pane.setLeft(loader.load());
            pane.setRight(sidebarPlaceholder);
            pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    
            Scene scene = new Scene(pane, 1500, 768);
    
            stage.setTitle("Transistor");
            stage.setScene(scene);
            stage.getIcons().add(new Image(App.class.getResource("/org/group14/assets/icon.jpg").toString()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
       

    }

    private static void setStage(Stage stage) {
        App.stage = stage;
    }

    /**
     * Load the FXML file
     * 
     * @param fxml
     * @return Parent
     * @throws IOException
     */
    private static FXMLLoader loadFXML(String fxml) throws IOException {
        return new FXMLLoader(App.class.getResource("fxml/" + fxml + ".fxml"));
    }

    /**
     * Change Main Scene
     * 
     * @param subScene
     */
    public static void changeScene(@SuppressWarnings("exports") SubScene subScene) {
        Scene scene = stage.getScene();
        pane = (BorderPane) scene.getRoot();
        pane.setCenter(subScene);
    }

    /**
     * Stops the javafx application and all the threads created by it.
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void init() throws Exception {
        applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    public static void main(String[] args) {
        launch();
    }

}

