package org.group14.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * This class represents a resolution.
 * @param width The width of the resolution.
 * @param height The height of the resolution.
 */
@Getter
@AllArgsConstructor
@ToString
public class Resolution {
    private int width;
    private int height;
}
