package org.group14.services.gtfs.trip;

import java.util.List;

import org.group14.services.datasource.DataSourceFactory;

/**
 * TripService class is responsible for handling the business logic for the Trip
 * class.
 */
public class TripService {

    private final ITripRepository tripRepository;

    public TripService() {
        this.tripRepository = new TripRepository(DataSourceFactory.createDataSource());
    }

    /**
     * Finds a trip by its ID.
     * 
     * @param id The trip ID.
     * @return The trip with the given ID.
     */
    public Trip findById(String id) {
        return tripRepository.getById(id);
    }

    /**
     * Finds all trips.
     * 
     * @return A list of all trips.
     */
    public List<Trip> findAll() {
        return tripRepository.getAll();
    }
}