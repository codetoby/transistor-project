package org.group14.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * This class represents a point.
 * @param x The x coordinate of the point.
 * @param y The y coordinate of the point.
 */
@Getter
@AllArgsConstructor
@ToString
public final class Point {
    private double x;
    private double y;
}
