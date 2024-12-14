package org.group14.services.calculator.time;

import org.group14.services.calculator.time.decorator.RoundedTimeCalculator;
import org.group14.services.calculator.time.decorator.ToHourTimeCalculator;
import org.group14.services.calculator.time.decorator.ToMinuteTimeCalculator;

public class TimeCalculatorFactory {

    public static TimeCalculator createCalculator(TimeCalculator timeCalculator) {
        return new RoundedTimeCalculator(
                new ToHourTimeCalculator(
                        timeCalculator));
    }

    public static TimeCalculator createCalculator(
            boolean roundTime,
            boolean convertToHours,
            boolean convertToMinutes,
            TimeCalculator baseCalculator) {

        TimeCalculator calculator = baseCalculator;

        if (convertToMinutes) {
            calculator = new ToMinuteTimeCalculator(calculator);
        } else if (convertToHours) {
            calculator = new ToHourTimeCalculator(calculator);
        }

        if (roundTime) {
            calculator = new RoundedTimeCalculator(calculator);
        }

        return calculator;
    }

}