package org.group14.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Configuration class for the application.
 */
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Config {
    private DatabaseConfig databaseConfig;
    private RoutingConfig routingConfig;
    private CalculatorConfig calculatorConfig;

    public DatabaseConfig getDatabaseConfig() {
        if (databaseConfig == null) {
            databaseConfig = new DatabaseConfig();
        }
        return databaseConfig;
    }

    public RoutingConfig getRoutingConfig() {
        if (routingConfig == null) {
            routingConfig = new RoutingConfig();
        }
        return routingConfig;
    }

    public CalculatorConfig getCalculatorConfig() {
        if (calculatorConfig == null) {
            calculatorConfig = new CalculatorConfig();
        }
        return calculatorConfig;
    }
}
