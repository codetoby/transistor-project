package org.group14.services.routing;

import org.group14.domain.models.Route;
import org.group14.domain.models.interfaces.IRouteItem;

public interface IPathReconstructor <T extends IRouteItem>{
    Route<T> reconstructRoute();
}
