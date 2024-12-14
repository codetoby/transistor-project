package org.group14.presentation.controller.eventhandler;

import org.group14.presentation.controller.RouteBlockController;

@FunctionalInterface
public interface RouteBlockSelectHandler {
    void onRouteBlockSelect(RouteBlockController controller);
}
