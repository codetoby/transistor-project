package org.group14.domain.models.interfaces;

import org.group14.services.gtfs.stops.Stop;
import org.group14.domain.models.Coordinate;
import org.group14.services.routing.bus.Item;

/**
 * Interface for the route item.
 */
public sealed interface IRouteItem permits Coordinate, Item, Stop { 
}
