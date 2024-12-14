package org.group14.services.calculator.time.decorator;

import org.group14.services.calculator.time.TimeCalculator;
import org.group14.services.calculator.time.decorator.unit.TimeUnit;
import org.group14.services.calculator.time.decorator.unit.TimeUnitConverter;

public class ToMinuteTimeCalculator extends ConverterTimeCalculator {

    public ToMinuteTimeCalculator(TimeCalculator timeCalculator) {
        super(timeCalculator, TimeUnit.MINUTES);
    }

    @Override
    public double calculateTime(double distance) {
        return TimeUnitConverter.convertFromSeconds(timeCalculator.calculateTime(distance), unit);
    }
    
}
