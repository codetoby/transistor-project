package org.group14.utilities;

import java.text.DecimalFormat;

/**
 * This class is responsible for rounding numbers.
 */
public class Round {

    private Round(){
    }

    /**
     * Round a double to the third decimal.
     * @return double
     */
    public static double toThirdDecimal(double value) {
        DecimalFormat df = new DecimalFormat("#.###");
        String rounded = df.format(value);
        rounded = rounded.replace(',', '.');
        return Double.parseDouble(rounded);
    }

    /**
     * Round a double to the second decimal.
     * @return double
     */
    public static double toSecondDecimal(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        String rounded = df.format(value);
        rounded = rounded.replace(',', '.');
        return Double.parseDouble(rounded);
    }

}
