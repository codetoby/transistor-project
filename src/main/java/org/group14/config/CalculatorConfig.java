package org.group14.config;

import lombok.*;

/**
 * Configuration class for the calculator.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CalculatorConfig {
    private String method;
}
