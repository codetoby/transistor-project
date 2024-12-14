package org.group14.services.routing.bus.routing.database_query.model;

import org.group14.services.gtfs.stops.Stop;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * This class represents a stop key.
 * A stop key is a pair of stops, which is hashable.
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class StopKey {
    private final Stop originStop;
    private final Stop destinationStop;
}
