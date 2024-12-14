package org.group14.services.routing.bus.routing.dijkstra;

import java.time.LocalDateTime;
// import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
// import java.util.Map.Entry;
// import java.util.function.Function;
// import java.util.stream.Collectors;

import org.group14.domain.models.Route;
import org.group14.services.gtfs.stops.Stop;
import org.group14.services.routing.IPathReconstructor;
import org.group14.services.routing.bus.Item;

import lombok.AllArgsConstructor;

/**
 * Class to reconstruct the path from the destination to the source.
 */
@AllArgsConstructor
public class BusPathReconstructor implements IPathReconstructor<Item> {

    private BusPathResults responses;

    public Route<Item> reconstructRoute() {
        List<Item> route = new LinkedList<>();
        Map<Item, Item> previous = responses.getPrevious();
        Map<Stop, LocalDateTime> distance = responses.getDistance();

        if (!previous.containsKey(new Item(responses.getLastStop(), null))) {
            System.out.println("previous does not contain last stop");
            System.out.println("Last stop: " + responses.getLastStop());
            System.out.println("Previous: " + previous);
            System.out.println("No path found");
            return new Route<>(Item.class, route);
        }

        Item current = new Item(responses.getLastStop(), distance.get(responses.getLastStop()));
        Item prev = previous.get(current);
        while (prev != null) {
            route.add(new Item(current.getStop(), prev.getTripId(), current.getTime()));
            current = prev;
            prev = previous.get(current);
        }

        route.add(new Item(current.getStop(), current.getTime()));
        // return minimizeTrips(route);
        return new Route<>(Item.class, route);
    }

    // /**
    //  * Greedy method to merge repeated trips.
    //  * Goes from descending order of frequency.
    //  * @param path The path to be minimized.
    //  * @return Path with possible merged trips
    //  */
    // private static List<Item> minimizeTrips(List<Item> path) {
    //     Function<List<Item>, List<String>> getTripIds = xs -> xs.stream()
    //             .filter(x -> x.getTripId() != null)
    //             .map(x -> x.getTripId())
    //             .toList();

    //     Function<List<String>, List<Entry<String, Long>>> getDuplicates = xs -> frequencyMap(xs).entrySet()
    //             .stream()
    //             .filter(x -> x.getValue() > 1)
    //             .sorted((x1, x2) -> Long.compare(x2.getValue(), x1.getValue()))
    //             .toList();

    //     Function<List<Item>, List<Entry<String, Long>>> getDuplicateTripIds = getTripIds.andThen(getDuplicates);

    //     List<Item> currList = new ArrayList<>(path);
    //     List<Entry<String, Long>> duplicates = getDuplicateTripIds.apply(currList);
    //     while (!duplicates.isEmpty()) {
    //         Entry<String, Long> dup = duplicates.getFirst();
    //         currList = mergeTrip(currList, dup.getKey(), dup.getValue().intValue());
    //         duplicates = getDuplicateTripIds.apply(currList);
    //     }

    //     return currList;
    // }

    // /**
    //  * Removes trips which are in-between of two equal trips. 
    //  * @param path The path.
    //  * @param toMerge The trip surrounding other trips.
    //  * @param count The number of occurences of the trip.
    //  * @return A new minimized path.
    //  */
    // private static List<Item> mergeTrip(List<Item> path, String tripToMerge, int count) {
    //     List<Item> merged = new ArrayList<>();

    //     if (count <= 1) return merged;
    //     int found = 0;
    //     for (int i = 0; i < path.size() - 1; i++) {
    //         if (path.get(i).getTripId().equals(tripToMerge)) {
    //             found++;
    //         }

    //         if (found == count) {
    //             found++;
    //             continue;
    //         }

    //         if (found > 1 && found <= count) {
    //             continue;
    //         }

    //         merged.add(path.get(i));
    //     }

    //     merged.add(path.getLast());

    //     // System.out.println("merged");
    //     // for (Item i: merged) {
    //     //     System.out.println("StopId: " + i.getStopItem().getStopId() + "; TripId: " + i.getTripId());
    //     // }

    //     return merged;
    // }

    // /**
    //  * Generic function to count the number of occurences of each item in a list.
    //  * @param <T> Generic type.
    //  * @param xs List of generic type T.
    //  * @return A map from the frequency of each list element.
    //  */
    // private static <T> Map<T, Long> frequencyMap(List<T> xs) {
    //     return xs.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    // }

     // private static List<Item> removeRedundant(List<Item> path) {
    //     List<Item> filtered = new ArrayList<>();
    //     String lastTrip = path.getFirst().getTripId();
    //     filtered.add(path.getFirst());

    //     for (int i = 1; i < path.size(); i++) {
    //         Item currItem = path.get(i);

    //         if (lastTrip == null) {
    //             lastTrip = currItem.getTripId();
    //             continue;
    //         }

    //         if (currItem.getTripId().equals(lastTrip)) {
                
    //         }

    //     }

    //     return filtered;
    // }
    
}
