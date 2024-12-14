package org.group14.services.datasource;

import javax.sql.DataSource;

import org.group14.config.ConfigHandler;
import org.group14.config.DatabaseConfig;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSourceFactory {

    private static final int MAXIMUM_POOL_SIZE = 200;
    private static DataSource dataSource;

    public static DataSource createDataSource() {
        if (dataSource == null) {
            DatabaseConfig config = ConfigHandler.getInstance().getConfig().getDatabaseConfig();
            dataSource = createDataSource(config);
        }
        return dataSource;
    }

    private static DataSource createDataSource(DatabaseConfig config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getUrl() + config.getDb());
        hikariConfig.setUsername(config.getUser());
        hikariConfig.setPassword(config.getPassword());
        hikariConfig.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
        
        return new HikariDataSource(hikariConfig);
    }

}