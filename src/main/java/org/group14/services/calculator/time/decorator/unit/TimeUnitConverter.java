package org.group14.services.calculator.time.decorator.unit;

import org.group14.services.calculator.time.TimeCalculator;
import org.group14.services.calculator.time.decorator.ToHourTimeCalculator;
import org.group14.services.calculator.time.decorator.ToMinuteTimeCalculator;

public class TimeUnitConverter {
    public static double convertToSeconds(double time, TimeUnit unit) {
        return switch (unit) {
            case MINUTES -> time * 60;
            case HOURS -> time * 3600;
            default -> time;
        };
    }

    public static double convertFromSeconds(double timeInSeconds, TimeUnit unit) {
        return switch (unit) {
            case MINUTES -> timeInSeconds / 60;
            case HOURS -> timeInSeconds / 3600;
            default -> timeInSeconds;
        };
    }

    public static TimeCalculator getConverter(TimeUnit unit, TimeCalculator timeCalculator) {
        return switch (unit) {
            case MINUTES -> new ToMinuteTimeCalculator(timeCalculator);
            case HOURS -> new ToHourTimeCalculator(timeCalculator);
            default -> timeCalculator;
        };
    }
}
