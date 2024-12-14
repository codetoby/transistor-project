package org.group14.presentation.interactive;

import java.util.ArrayList;
import java.util.List;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import org.group14.services.routing.AbstractRoutingResponse;

import javafx.scene.paint.Color;

/**
 * A class that extends the MapView class to add custom map layers.
 */
public class MapViewExtended {

    private static final int ZOOM_DEFAULT = 14;
    private static final MapPoint INITIAL_ZOOM_POINT = new MapPoint(50.8514, 5.6910);
    private static final MapView mapView = createMapView();
    private static final List<MapLayer> customMapLayers = new ArrayList<>();

    private MapViewExtended() {
    }

    private static MapView createMapView() {
        MapView mapView = new MapView();
        mapView.setPrefSize(791, 791);
        mapView.setCenter(INITIAL_ZOOM_POINT);
        mapView.setZoom(ZOOM_DEFAULT);
        return mapView;
    }

    /**
     * Adds a custom map layer to the map view.
     * 
     * @param mapLayer The map layer.
     */
    public static void addCustomMapLayer(MapLayer mapLayer) {
        mapView.addLayer(mapLayer);
        customMapLayers.add(mapLayer);
    }

    public static void removeCustomMapLayer() {
        if (!customMapLayers.isEmpty()) {
            for (MapLayer layer : customMapLayers) {
                mapView.removeLayer(layer);
            }
        }
    }

    public static MapView getMapView() {
        return mapView;
    }

    public static void createCustomMapLayer(AbstractRoutingResponse routingResponse) {
        PoiLayer customMapLayer = new PoiLayer(Color.RED);

        addCustomMapLayer(customMapLayer);
    }
}
