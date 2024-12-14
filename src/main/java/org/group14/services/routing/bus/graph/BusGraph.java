package org.group14.services.routing.bus.graph;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;
import org.group14.services.gtfs.stop_times.StopTime;
import org.group14.services.gtfs.stop_times.StopTimeService;
import org.group14.services.gtfs.stops.Stop;
import org.group14.domain.models.interfaces.IGraph;
import org.group14.services.calculator.time.TimeCalculator;
import org.group14.services.calculator.time.WalkingTime;
import org.group14.services.calculator.time.decorator.RoundedTimeCalculator;
import org.group14.services.calculator.time.decorator.ToMinuteTimeCalculator;
import org.group14.services.gtfs.stops.StopService;
import org.group14.services.routing.bus.graph.edges.BusGraphEdge;
import org.group14.utilities.gtfs.DateHandler;
import org.group14.utilities.gtfs.TimeHandler;

/**
 * Class uses a graph to represent bus trips between bus stops.
 * Nodes represent bus stops.
 * Edges represent connections between stops with a trip.
 */
@Getter
public final class BusGraph implements IGraph {

    private static final StopTimeService stopTimeService = new StopTimeService();
    private static final StopService parallelStopService = new StopService();
    private static final TimeCalculator timeCalculator = new RoundedTimeCalculator(new ToMinuteTimeCalculator(new WalkingTime()));

    private static Set<Stop> stopSet;
    private static Map<String, Stop> stopMap;
    private Map<Stop, List<BusGraphEdge>> edges;

    static {
        stopSet = parallelStopService
                .findAll()
                .stream()
                .collect(Collectors.toUnmodifiableSet());

        stopMap = stopSet
                .stream()
                .collect(Collectors.toUnmodifiableMap(
                        Stop::getStopId,
                        Function.identity()));
    }

    /**
     * Constructs a new BusGraph object.
     *
     * @param time the time of the day for creating the graph
     */
    public BusGraph(LocalDateTime time) {
        initializeEdges(time);
    }

    private void initializeEdges(LocalDateTime time) {
        edges = stopSet
                .parallelStream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        s -> findEdges(s, time)));
    }

    /**
     * Finds the edges of a node, based on time.
     *
     * @param node     The node (bus stop).
     * @param currTime The current time being considered.
     * @return A list edges given the current position and time.
     */
    private List<BusGraphEdge> findEdges(Stop node, LocalDateTime currTime) {
        List<StopTime> stopTimes = stopTimeService.getStopTimeByStopAndDatetime(
                node.getStopId(),
                DateHandler.getDate(currTime.toLocalDate()),
                currTime,
                100L);

        Map<String, List<StopTime>> stopTimeByTrip = stopTimes
                .stream()
                .collect(Collectors.groupingBy(StopTime::getTripId));

        Map<String, String> nodeStopTime = new HashMap<>();
        for (List<StopTime> st : stopTimeByTrip.values()) {
            StopTime first = st
                    .stream()
                    .filter(s -> s.getStopId().equals(node.getStopId()))
                    .findFirst()
                    .orElse(null);
            if (first == null)
                continue;

            nodeStopTime.put(first.getTripId(), first.getDepartureTime());
        }

        List<BusGraphEdge> nextEdges = new ArrayList<>();

        for (StopTime st : stopTimes.stream().toList()) {
            if (!nodeStopTime.containsKey(st.getTripId()))
                continue;

            Stop toStop = stopMap.get(st.getStopId());
            if (node.equals(toStop))
                continue;

            LocalDateTime arrivalTime = TimeHandler.parseTime(st.getArrivalTime(), currTime.toLocalDate());
            double distance = ChronoUnit.MINUTES.between(currTime, arrivalTime);

            nextEdges.add(
                    new BusGraphEdge(
                            st.getTripId(),
                            node,
                            toStop,
                            Integer.parseInt(st.getStopSequence()),
                            TimeHandler.parseTime(nodeStopTime.get(st.getTripId()), currTime.toLocalDate()),
                            arrivalTime,
                            distance));

        }

        Map<Stop, Double> walkingTransfers = parallelStopService.getClosetsStops(node.getCoordinate(), 0.3, 5);
        for (Map.Entry<Stop, Double> entry : walkingTransfers.entrySet()) {
            Stop stop = entry.getKey();
            if (stop.equals(node))
                continue;
            nextEdges.add(
                    new BusGraphEdge(
                            "walk",
                            node,
                            stop,
                            0,
                            null,
                            null,
                            timeCalculator.calculateTime(entry.getValue() * 1000)));
        }

        return nextEdges;
    }

    public List<BusGraphEdge> getStopEdges(Stop s, LocalDateTime currTime) {
        List<BusGraphEdge> edgeList = edges.getOrDefault(s, null);
        if (edgeList == null)
            return null;

        return edgeList
                .stream()
                .filter(x -> x.getTripId().equals("walk") ||
                        (x.getDepartureTime().isAfter(currTime) || x.getDepartureTime().isEqual(currTime)))
                .toList();
    }

    public int getNodeCount() {
        return edges.keySet().size();
    }

    public int getEdgeCount() {
        return edges.values().stream().map(List::size).reduce(0, Integer::sum);
    }

    public Set<Stop> getStops() {
        return stopSet;
    }
}
