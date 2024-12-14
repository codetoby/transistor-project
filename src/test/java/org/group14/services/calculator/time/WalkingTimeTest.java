package org.group14.services.calculator.time;

import org.group14.services.calculator.time.decorator.RoundedTimeCalculator;
import org.group14.services.calculator.time.decorator.ToHourTimeCalculator;
import org.group14.services.calculator.time.decorator.ToMinuteTimeCalculator;
import org.group14.services.calculator.time.decorator.ToSecondsTimeCalculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalkingTimeTest {

    @Test
    public void testCalculateTime() {
        RoundedTimeCalculator walkingTime = new RoundedTimeCalculator(new ToMinuteTimeCalculator(new WalkingTime()));
        double distance = 1000;
        double actual = walkingTime.calculateTime(distance);
        assertEquals(11.11, actual);
    }

    @Test
    public void testCalculateTime2() {
        RoundedTimeCalculator walkingTime = new RoundedTimeCalculator(new ToHourTimeCalculator(new WalkingTime()));
        double distance = 1000;
        double actual = walkingTime.calculateTime(distance);
        assertEquals(0.19, actual);
    }

    @Test
    public void testCalculateTime3() {
        RoundedTimeCalculator walkingTime = new RoundedTimeCalculator(new ToSecondsTimeCalculator(new WalkingTime()));
        double distance = 1000;
        double actual = walkingTime.calculateTime(distance);
        assertEquals(666.67, actual);
    }

    @Test
    public void testCalculateTime4() {
        RoundedTimeCalculator walkingTime = new RoundedTimeCalculator(new WalkingTime());
        double distance = 1000;
        double actual = walkingTime.calculateTime(distance);
        assertEquals(666.67, actual);
    }
}
