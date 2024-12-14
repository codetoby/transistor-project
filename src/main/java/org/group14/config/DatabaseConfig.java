package org.group14.config;

import lombok.*;

/**
 * Configuration class for the database.
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseConfig {
    private String url;
    private String user;
    private String password;
    private String db;
}
