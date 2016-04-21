package net.bilberry.index.service;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.*;
import com.google.common.base.Joiner;
import org.springframework.data.geo.Point;
import org.springframework.data.solr.core.geo.GeoConverters;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class GeoService {
    public static Optional<LatLng> findLocationFor(String address) {
        final Geocoder geocoder = new Geocoder();
        GeocoderRequest request = new GeocoderRequestBuilder().setAddress(address).setLanguage("en").getGeocoderRequest();
        LatLng location = null;
        try {
            GeocodeResponse geocodeResponse = geocoder.geocode(request);
            List<GeocoderResult> results = geocodeResponse.getResults();
            if (!results.isEmpty()) location = results.get(0).getGeometry().getLocation();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(location);
    }

    public static Optional<String> findAddressFor(Point location) {
        final Geocoder geoCoder = new Geocoder();
        LatLng latLng = new LatLng(new BigDecimal(location.getX()), new BigDecimal(location.getY()));

        GeocoderRequest request = new GeocoderRequestBuilder()
                .setLocation(latLng).setLanguage("en").getGeocoderRequest();

        String address = null;
        try {
            GeocodeResponse geocodeResponse = geoCoder.geocode(request);
            if (!geocodeResponse.getResults().isEmpty()) {
                Stream<String> stringStream = geocodeResponse.getResults().get(0).getAddressComponents().stream()
                        .filter(a -> a.getTypes().contains("country") || a.getTypes().contains("locality"))
                        .map(GeocoderAddressComponent::getShortName);

                address = Joiner.on(",").join(stringStream.toArray());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(address);
    }

    public static Optional<String> findCountryFor(Point location) {
        final Geocoder geoCoder = new Geocoder();
        LatLng latLng = new LatLng(new BigDecimal(location.getX()), new BigDecimal(location.getY()));

        GeocoderRequest request = new GeocoderRequestBuilder()
                .setLocation(latLng).setLanguage("en").getGeocoderRequest();

        String address = null;
        try {
            GeocodeResponse geocodeResponse = geoCoder.geocode(request);
            if (!geocodeResponse.getResults().isEmpty()) {
                Stream<String> stringStream = geocodeResponse.getResults().get(0).getAddressComponents().stream()
                        .filter(a -> a.getTypes().contains("country"))
                        .map(GeocoderAddressComponent::getLongName);

                address = Joiner.on(",").join(stringStream.toArray());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(address);
    }
}
