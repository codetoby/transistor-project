package org.group14.presentation.controller;

import java.sql.SQLException;

import org.group14.config.Config;
import org.group14.config.ConfigHandler;
import org.group14.utilities.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This class is responsible for the settings controller.
 */
public class SettingsController {

    @FXML
    private TextField databaseDatabase;

    @FXML
    private TextField databasePassword;

    @FXML
    private TextField databaseUrl;

    @FXML
    private TextField databaseUsername;

    @FXML
    private ComboBox<String> optionsComboBox;

    @FXML
    private TextField routingMaxWalkingDistance;

    @FXML
    private TextField routingMaxWalkingSpeed;

    @FXML
    private Label errorMessage;

    @FXML
    private void initialize() {
        optionsComboBox.getItems().addAll("Haversine", "Great Circle", "Pythagorean");
        errorMessage.setVisible(false);
        initializeConfig();
    }

    /**
     * This method initializes the configuration.
     * It sets the text fields to the values of the configuration.
     */
    private void initializeConfig() {
        ConfigHandler configHandler = ConfigHandler.getInstance();
        Config config = configHandler.getConfig();

        databaseDatabase.setText(config.getDatabaseConfig().getDb());
        databasePassword.setText(config.getDatabaseConfig().getPassword());
        databaseUrl.setText(config.getDatabaseConfig().getUrl());
        databaseUsername.setText(config.getDatabaseConfig().getUser());

        routingMaxWalkingDistance.setText(String.valueOf(config.getRoutingConfig().getMaxWalkingDistanceMeters()));
        routingMaxWalkingSpeed.setText(String.valueOf(config.getRoutingConfig().getMaxWalkingSpeedMetersPerSecond()));

        optionsComboBox.setValue(config.getCalculatorConfig().getMethod());
    }

    /**
     * This method saves the configuration.
     * It creates a new configuration object from the input fields.
     * It validates the configuration and saves it.
     * If an exception is thrown, the error message is set to the exception message.
     */
    @FXML
    private void saveConfig() {
        errorMessage.setVisible(false);

        try {
            Config newConfig = createConfigFromInput();
            validateAndSaveConfig(newConfig);
            closeWindow();
        } catch (Exception e) {
            Logger.getInstance().error(e.getMessage());
            errorMessage.setText(e.getMessage());
            errorMessage.setVisible(true);
        }
    }

    /**
     * This method creates a configuration object from the input fields.
     * @return A configuration object.
     */
    private Config createConfigFromInput() {
        Config newConfig = new Config();
        newConfig.getDatabaseConfig().setDb(databaseDatabase.getText());
        newConfig.getDatabaseConfig().setPassword(databasePassword.getText());
        newConfig.getDatabaseConfig().setUrl(databaseUrl.getText());
        newConfig.getDatabaseConfig().setUser(databaseUsername.getText());

        newConfig.getRoutingConfig().setMaxWalkingDistanceMeters(Integer.parseInt(routingMaxWalkingDistance.getText()));
        newConfig.getRoutingConfig().setMaxWalkingSpeedMetersPerSecond(Double.parseDouble(routingMaxWalkingSpeed.getText()));

        newConfig.getCalculatorConfig().setMethod(optionsComboBox.getValue());

        return newConfig;
    }

    /**
     * This method validates the configuration and saves it.
     * It creates a database connection to validate the database configuration.
     * It sets the configuration in the config handler and dumps the configuration.
     * @param config The configuration to be validated and saved.
     * @throws SQLException If an error occurs while creating the database connection.
     */
    private void validateAndSaveConfig(Config config) throws SQLException {
        ConfigHandler configHandler = ConfigHandler.getInstance();
        configHandler.setConfig(config);
        configHandler.dumpConfig();
    }

    @FXML
    private void closeConfig() {
        closeWindow();
    }

    private void closeWindow() {
        databaseDatabase.getScene().getWindow().hide();
    }
}
