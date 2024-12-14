package org.group14.services.calculator.time.decorator;

import lombok.Setter;

import org.group14.services.calculator.time.TimeCalculator;
import org.group14.utilities.Round;

@Setter
public class RoundedTimeCalculator extends TimeCalculator {

    private TimeCalculator timeCalculator;

    public RoundedTimeCalculator(TimeCalculator timeCalculator) {
        this.timeCalculator = timeCalculator;
    }

    public double calculateTime(double distance) {
        return Round.toSecondDecimal(timeCalculator.calculateTime(distance));
    }
}
