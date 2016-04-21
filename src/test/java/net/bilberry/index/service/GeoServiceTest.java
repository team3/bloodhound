package net.bilberry.index.service;

import com.google.code.geocoder.model.LatLng;
import net.bilberry.index.model.Location;
import org.junit.Test;
import org.springframework.data.geo.Point;

import java.util.Optional;

import static org.junit.Assert.*;

public class GeoServiceTest {

    @Test
    public void testFindLocationFor() throws Exception {
        Optional<LatLng> location = GeoService.findLocationFor("Astana");
        System.out.println(location);
    }

    @Test
    public void testFindAddressFor() throws Exception {
        Optional<String> addressFor = GeoService.findAddressFor(new Point(50.4501,30.5234));

        System.out.println(addressFor);
    }

    @Test
    public void testFindCountryFor() throws Exception {
        Optional<String> country = GeoService.findCountryFor(Location.KIEV.getLocation());
        System.out.println("country");
    }
}