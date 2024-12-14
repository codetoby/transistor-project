package org.group14.services.routing.bus.graph;

import java.time.LocalDateTime;

public class BusGraphFactory {
    public static BusGraph create(LocalDateTime time) {
        return new BusGraph(time);
    }
}
