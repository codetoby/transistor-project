package org.group14.config.spring;

import org.group14.presentation.controller.PrimaryController;
import org.group14.services.calculator.Measurer;
import org.group14.services.calculator.TravelInformationCalculator;
import org.group14.services.calculator.distance.DistanceCalculator;
import org.group14.services.calculator.distance.Haversine;
import org.group14.services.calculator.time.TimeCalculator;
import org.group14.services.calculator.time.TimeCalculatorFactory;
import org.group14.services.calculator.time.WalkingTime;
import org.group14.services.routing.RouteService;
import org.group14.services.zipcode.IZipCodeService;
import org.group14.services.zipcode.database.DatabaseZipCodeService;
import org.group14.services.zipcode.ZipCodeController;
import org.springframework.context.annotation.Bean;

public class AppConfig {

    @Bean
    public DistanceCalculator distanceCalculator() {
        return new Haversine();
    }

    @Bean
    public TimeCalculator timeCalculator() {
        return TimeCalculatorFactory.createCalculator(new WalkingTime());
    }

    @Bean
    public IZipCodeService zipCodeService() {
        return new DatabaseZipCodeService();
    }

    @Bean
    public RouteService routingService() throws Exception {
        return RouteService.getInstance();
    }

    @Bean
    public ZipCodeController zipCodeController() {
        return new ZipCodeController(zipCodeService());
    }

    @Bean
    public PrimaryController primaryController() throws Exception {
        return new PrimaryController(routingService(), zipCodeController(), travelInformationCalculator());
    }

    @Bean
    public TravelInformationCalculator travelInformationCalculator() {
        return new TravelInformationCalculator(new Measurer(distanceCalculator(), timeCalculator()));
    }
}
