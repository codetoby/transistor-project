package org.group14.services.calculator.time.decorator;

import org.group14.services.calculator.time.TimeCalculator;
import org.group14.services.calculator.time.decorator.unit.TimeUnit;

public abstract class ConverterTimeCalculator extends TimeCalculator{

    protected TimeCalculator timeCalculator;
    protected TimeUnit unit;

    public ConverterTimeCalculator(TimeCalculator timeCalculator, TimeUnit unit) {
        this.timeCalculator = timeCalculator;
        this.unit = unit;
    }

    public abstract double calculateTime(double distance);

    public TimeUnit getUnit() {
        return unit;
    }
    
}
