package lv.ctco.Entities;


import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Coordinate {

    @Column(name = "ORIGIN")
    private double latitude;
    @Column(name = "DESTINATION")
    private double longitude;

    public Double distance() {

        return null;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
