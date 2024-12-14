package org.group14.config;

import lombok.*;

/**
 * Configuration class for the routing.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoutingConfig {
    private Integer maxWalkingDistanceMeters;
    private Double maxWalkingSpeedMetersPerSecond;
}
