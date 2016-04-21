package net.bilberry.index.model;

import org.springframework.data.geo.Point;

public enum Location {

    KIEV(50.450091, 30.523415);

    private double lat;
    private double lng;

    Location(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Point getLocation() {
        return new Point(this.lat, this.lng);
    }
}
