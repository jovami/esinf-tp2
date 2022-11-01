package jovami.model;

import java.awt.geom.Point2D;

public class Coordinate extends Point2D {

    private double latitude;
    private double longitude;

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    @Override
    public double getX() {
        return latitude;
    }

    @Override
    public double getY() {
        return longitude;
    }

    @Override
    public void setLocation(double x, double y) {
        throw new UnsupportedOperationException("not supported");
    }
}
