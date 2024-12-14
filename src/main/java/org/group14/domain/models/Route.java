package org.group14.domain.models;

import java.util.ArrayList;
import java.util.List;

import org.group14.domain.models.interfaces.IRouteItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * This class represents a route.
 * @param <T> The type of information contain.
 */
@AllArgsConstructor
@Getter
@ToString
public class Route <T extends IRouteItem> {

    private final Class<T> routeItemType;
    private final List<T> listOfRouteItem;

    /**
     * Constructor.
     * @param routeItemType The type of information contain.
     */
    public Route(Class<T> routeItemType) {
        this.listOfRouteItem = new ArrayList<>();
        this.routeItemType = routeItemType;
    }

}
