package org.group14.services.routing.bus.routing.dijkstra;

import java.time.LocalDateTime;
import java.util.Map;

import org.group14.services.gtfs.stops.Stop;
import org.group14.services.routing.bus.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BusPathResults {

    private Map<Stop, LocalDateTime> distance;
    private Map<Item, Item> previous;
    private Stop lastStop;
    private Stop firstStop;
    private LocalDateTime departueTime;
    private double closestDistance;

    public BusPathResults(Map<Stop, LocalDateTime> distance, Map<Item, Item> previous) {
        this.distance = distance;
        this.previous = previous;
    }
    
}
