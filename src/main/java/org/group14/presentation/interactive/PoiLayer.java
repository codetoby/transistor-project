package org.group14.presentation.interactive;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.group14.domain.models.Coordinate;
import org.group14.services.routing.RouteUtils;

/**
 * A custom map layer that draws lines between points.
 */
public class PoiLayer extends MapLayer {

    private final ObservableList<MapPoint> points = FXCollections.observableArrayList();
    private final Color color;

    public PoiLayer(Color color) {
        this.color = color;
    }

    /**
     * draws the lines between the points.
     */
    @Override
    protected void layoutLayer() {
        getChildren().clear();
        for (int i = 0; i < points.size() - 1; i++) {
            MapPoint start = points.get(i);
            MapPoint end = points.get(i + 1);
            Point2D startMapPoint = getMapPoint(start.getLatitude(), start.getLongitude());
            Point2D endMapPoint = getMapPoint(end.getLatitude(), end.getLongitude());
            Coordinate c1 = new Coordinate(start.getLatitude(), start.getLongitude());
            Coordinate c2 = new Coordinate(end.getLatitude(), end.getLongitude());
            if (RouteUtils.getDistance(c1, c2) <= 0.005) {
                Line line = new Line(startMapPoint.getX(), startMapPoint.getY(), endMapPoint.getX(),
                        endMapPoint.getY());
                line.setStrokeWidth(5);
                line.setStroke(color);
                getChildren().add(line);
            }
        }
    }

    /**
     * Adds a point to the map.
     * 
     * @param mapPoint The map point.
     */
    public void addPoint(MapPoint mapPoint) {
        points.add(mapPoint);
        this.markDirty();
    }
}
