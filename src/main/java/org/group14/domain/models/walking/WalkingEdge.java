package org.group14.domain.models.walking;

import org.group14.domain.models.Coordinate;
import org.group14.domain.models.interfaces.IEdge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * This class represents an edge. It contains a coordinate and a weight.
 */
@Getter
@ToString
@AllArgsConstructor
public final class WalkingEdge implements IEdge{
    private int coordinateId;
    private Coordinate coordinate;
    private double weight;
}
