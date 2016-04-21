package net.bilberry.index.model;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.geo.Point;
import org.springframework.data.solr.core.geo.GeoConverters;

public class LocationContainer {
    @Field("location")
    private org.springframework.data.geo.Point location;

    private String address;

    public String getStringLocation() {
        return GeoConverters.Point2DToStringConverter.INSTANCE.convert(this.location);
    }

    public void setStringLocation(final String location) {
        this.location = GeoConverters.StringToPointConverter.INSTANCE.convert(location);
    }

    public void setLocation(final Point point) {
        this.location = point;
    }

    public Point getLocation() {return this.location;}

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }
}
