package org.group14.services.routing.bus.routing.database_query;

import java.util.List;

import org.group14.services.gtfs.stops.Stop;
import org.group14.services.routing.bus.routing.database_query.model.PossibleTrip;

public interface IBusRoutingDatabaseRepository {
    List<PossibleTrip> findPossibleTrips(Stop originStop, Stop destStop, String time, String date, int limit);
}
