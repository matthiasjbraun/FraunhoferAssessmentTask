package database;

import jakarta.persistence.Embeddable;

@Embeddable
public class Geo {
    private double lat;
    private double lng;
    public Geo(){}

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
