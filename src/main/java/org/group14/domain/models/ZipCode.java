package org.group14.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents a zip code.
 */
@AllArgsConstructor
@Getter
@ToString
public class ZipCode {

    private final String code;
    @Setter
    private Coordinate coordinate;

    /**
     * Constructor.
     * @param code The zip code.
     */
    public ZipCode(String code) {
        this.code = code;
    }

    
    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ZipCode other)) {
            return false;
        }
        return this.code.equals(other.code);
    }
}
