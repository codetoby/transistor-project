package org.group14.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import lombok.Getter;

import lombok.Setter;
import org.group14.services.datasource.DataSourceFactory;
import org.group14.utilities.Logger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * Handler class for the configuration.
 */
@Setter
@Getter
public final class ConfigHandler {

    private static final String CONFIG_PATH_STRING = "src/main/resources/org/group14/config/config.yaml";
    private static final Path CONFIG_PATH = Path.of(CONFIG_PATH_STRING);
    
    private static ConfigHandler configHandler;
    private Config config;

    private ConfigHandler() {
        this.config = loadConfig();       
    }

    public static synchronized ConfigHandler getInstance()  {
        if(configHandler == null) {
            configHandler = new ConfigHandler();
        }
        return configHandler;
    }

    public Config loadConfig()  {
        Constructor constructor = new Constructor(Config.class);
        Yaml yaml = new Yaml(constructor);
        try {
            return yaml.load(new FileInputStream(CONFIG_PATH.toFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void dumpConfig() throws SQLException {
        try {
            dumpConfig(this.config);
            DataSource dataSource = DataSourceFactory.createDataSource();
            try (Connection connection = dataSource.getConnection()) {
                Logger.getInstance().log("Config Dumped Successfully");
            }
        } catch (IllegalArgumentException | IllegalAccessException | IOException e) {
            Logger.getInstance().error("Error Dumping in the Config" + e.getMessage());
        } catch(SQLException e) {
            throw new SQLException();
        }
    }

    public void dumpConfig(Config config) throws IllegalArgumentException, IllegalAccessException, IOException{
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(FlowStyle.BLOCK);
        options.setPrettyFlow(true);        
        Yaml yml = new Yaml(options);
        yml.dump(config, new FileWriter(CONFIG_PATH.toFile()));
    }

    public boolean checkIfDatabaseConfigIsLoaded() {
        return config.getDatabaseConfig().getUrl() == null
                || config.getDatabaseConfig().getDb() == null
                || config.getDatabaseConfig().getUser() == null
                || config.getDatabaseConfig().getPassword() == null
                || config.getDatabaseConfig().getUrl().isEmpty()
                || config.getDatabaseConfig().getDb().isEmpty()
                || config.getDatabaseConfig().getUser().isEmpty()
                || config.getDatabaseConfig().getPassword().isEmpty();
    }

    public boolean checkIfRoutingConfigIsLoaded() {
        return config.getRoutingConfig().getMaxWalkingDistanceMeters() == null
                || config.getRoutingConfig().getMaxWalkingSpeedMetersPerSecond() == null;
    }

    public boolean checkIfConfigLoaded() {
        return !checkIfDatabaseConfigIsLoaded() && !checkIfRoutingConfigIsLoaded();
    }

}

