package org.group14.services.calculator.time.decorator;

import org.group14.services.calculator.time.TimeCalculator;
import org.group14.services.calculator.time.decorator.unit.TimeUnit;

public class ToSecondsTimeCalculator extends ConverterTimeCalculator {

    public ToSecondsTimeCalculator(TimeCalculator timeCalculator) {
        super(timeCalculator, TimeUnit.SECONDS);
    }

    @Override
    public double calculateTime(double distance) {
        return timeCalculator.calculateTime(distance);
    }
    
}
