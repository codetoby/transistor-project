package org.group14.services.routing.bus.routing.dijkstra.v1;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.group14.services.gtfs.stops.Stop;
import org.group14.domain.models.Coordinate;
import org.group14.services.calculator.distance.DistanceCalculator;
import org.group14.services.calculator.distance.Haversine;
import org.group14.services.routing.bus.Item;
import org.group14.services.routing.bus.graph.BusGraph;
import org.group14.services.routing.bus.graph.BusGraphFactory;
import org.group14.services.routing.bus.graph.edges.BusGraphEdge;
import org.group14.services.routing.bus.routing.dijkstra.records.StopTrip;
import org.group14.services.routing.bus.routing.dijkstra.BusPathResults;
import org.group14.services.routing.bus.routing.dijkstra.IDijkstra;
import org.group14.utilities.Logger;

public class BusPathfinder implements IDijkstra {

    private static final DistanceCalculator DISTANCE_CALCULATOR = new Haversine();
    private static final double ACCEPTANCE_DISTANCE = 0.5;
    private static final double MAX_DISTANCE_TO_EXPLORE = 5;

    private final BusGraph busGraph;

    public BusPathfinder(LocalDateTime time) {
        Long start = System.currentTimeMillis();
        this.busGraph = BusGraphFactory.create(time);
        Long end = System.currentTimeMillis();
        Logger.getInstance().log("BusPathfinder::BusPathfinder: Graph created in " + (end - start) + "ms.");
    }

    public BusPathResults dijkstra(Stop origin, Stop destination, LocalDateTime startTime) {
        Map<Stop, LocalDateTime> stopArrivalTimes = new HashMap<>();
        Map<Item, Item> stopPredecessors = new HashMap<>();

        for (Stop stop : busGraph.getStops()) {
            stopArrivalTimes.put(stop, LocalDateTime.MAX);
        }
        stopArrivalTimes.put(origin, startTime);

        PriorityQueue<StopTrip> priorityQueue = new PriorityQueue<>(
            new PathComparator(
                DISTANCE_CALCULATOR, 
                destination.getCoordinate()));

        while (!priorityQueue.isEmpty()) {
            StopTrip currentStopTrip = priorityQueue.poll();

            if (currentStopTrip.stop().equals(destination)) {
                break;
            }

            LocalDateTime currentStopArrivalTime = stopArrivalTimes.get(currentStopTrip.stop());
            List<BusGraphEdge> outgoingEdges = busGraph.getStopEdges(currentStopTrip.stop(), currentStopArrivalTime);
            
            for (BusGraphEdge edge : outgoingEdges) {
                Stop nextStop = edge.getToStop();
                LocalDateTime nextStopArrivalTime = edge.getArrivalTime();

                if (edge.getTripId().equals("walk")) {
                    double walkingTimeInSeconds = edge.getTimeToTravel();
                    nextStopArrivalTime = currentStopArrivalTime.plusSeconds(((int) walkingTimeInSeconds) + 600);
                } 

                if (edge.getTripId() != null && !edge.getTripId().equals(currentStopTrip.tripId())) {
                    nextStopArrivalTime = nextStopArrivalTime.plusMinutes(20);
                }

                if (!stopArrivalTimes.containsKey(nextStop) || nextStopArrivalTime.isBefore(stopArrivalTimes.get(nextStop))) {
                    stopArrivalTimes.put(nextStop, nextStopArrivalTime);
                    stopPredecessors.put(new Item(nextStop), new Item(currentStopTrip.stop(), edge.getTripId(), nextStopArrivalTime));
                    priorityQueue.add(new StopTrip(
                        nextStop,
                        edge.getTripId(),
                        ChronoUnit.MINUTES.between(startTime, nextStopArrivalTime)));
                }
            }
        }


        return new BusPathResults(stopArrivalTimes, stopPredecessors);
        
    }

    public BusPathResults dijkstra(Stop startStop, Coordinate destinationCoordinate, LocalDateTime startTime) {
        try {
            Map<Stop, LocalDateTime> stopArrivalTimes = new HashMap<>();
            Map<Item, Item> stopPredecessors = new HashMap<>();
    
            Stop closestStopToDestination = null;
            double closestDistanceToDestination = Double.MAX_VALUE;
            double initialDistanceToDestination = distanceTo(startStop.getCoordinate(), destinationCoordinate);
    
            stopArrivalTimes.put(startStop, startTime);
            PriorityQueue<StopTrip> priorityQueue = new PriorityQueue<>(new PathComparator(DISTANCE_CALCULATOR, destinationCoordinate));
    
            priorityQueue.add(new StopTrip(startStop, null, 0));
    
            while (!priorityQueue.isEmpty()) {
                StopTrip currentStopTrip = priorityQueue.poll();
                Stop currentStop = currentStopTrip.stop();
                double currentDistanceToDestination = distanceTo(currentStop.getCoordinate(), destinationCoordinate);
    
                if (currentDistanceToDestination > (initialDistanceToDestination * MAX_DISTANCE_TO_EXPLORE)) {
                    continue;
                }
    
                if (currentDistanceToDestination < ACCEPTANCE_DISTANCE) {
                    closestStopToDestination = currentStop;
                    closestDistanceToDestination = currentDistanceToDestination;
                    break;
                }
    
                if (currentDistanceToDestination < closestDistanceToDestination) {
                    closestStopToDestination = currentStop;
                    closestDistanceToDestination = currentDistanceToDestination;
                }
    
                LocalDateTime currentStopArrivalTime = stopArrivalTimes.get(currentStop);
                List<BusGraphEdge> outgoingEdges = busGraph.getStopEdges(currentStop, currentStopArrivalTime);
    
                if (outgoingEdges == null || outgoingEdges.isEmpty()) {
                    continue;
                }

                for (BusGraphEdge edge : outgoingEdges) {
    
                    Stop nextStop = edge.getToStop();
                    LocalDateTime nextStopArrivalTime = edge.getArrivalTime();
    
                    if (nextStop.equals(currentStop)) {
                        Logger.getInstance().error("BusDijkstra::findShortestPath: Stop points to itself!");
                        continue;
                    }

                    // // Skip walking trips on origin bus strop
                    // if (currentStopTrip.tripId() == null && edge.getTripId().equals("walk")) {
                    //     continue;
                    // }
    
                    if (edge.getTripId().equals("walk")) {
                        double walkingTimeInSeconds = edge.getTimeToTravel();
                        nextStopArrivalTime = currentStopArrivalTime.plusSeconds(((int) walkingTimeInSeconds) + 600);
                    } 

                    // check if the trip id is the same
                    if (currentStopTrip.tripId() != null && !currentStopTrip.tripId().equals(edge.getTripId())) {
                        nextStopArrivalTime = nextStopArrivalTime.plusMinutes(20);
                    }
    
                    if (nextStopArrivalTime == null) {
                        Logger.getInstance().error("BusDijkstra::findShortestPath: Arrival time is null!");
                        continue;
                    }
    
                    if (!stopArrivalTimes.containsKey(nextStop) || nextStopArrivalTime.isBefore(stopArrivalTimes.get(nextStop))) {
                        stopArrivalTimes.put(nextStop, nextStopArrivalTime);
                        stopPredecessors.put(new Item(nextStop), new Item(currentStop, edge.getTripId(), nextStopArrivalTime));
                        priorityQueue.add(new StopTrip(
                                nextStop,
                                edge.getTripId(),
                                ChronoUnit.MINUTES.between(startTime, nextStopArrivalTime)));
                    }
                }
            }
    
            return new BusPathResults(stopArrivalTimes, stopPredecessors, closestStopToDestination, startStop, startTime, closestDistanceToDestination);
        } catch (Exception exception) {
            exception.printStackTrace();
            Logger.getInstance().error("BusDijkstra::findShortestPath: " + exception.getMessage());
            return null;
        }
    }

    private static double distanceTo(Coordinate source, Coordinate destination) {
        return DISTANCE_CALCULATOR.calculateDistance(source, destination);
    }
}