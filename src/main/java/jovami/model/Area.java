package jovami.model;

import java.awt.geom.Point2D;
import java.util.Objects;

public class Area implements Comparable<Area> {

    private String areaCode;
    private String codeM49;
    private String areaName;
    private Coordinate coords;
    private String country;


    public Area(String areaCode, String codeM49, String areaName, double latitude, double longitude, String country) {
        this.areaCode = areaCode;
        this.codeM49 = codeM49;
        this.areaName = areaName;
        this.coords = new Coordinate(latitude,longitude);
        this.country = country;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public void setCodeM49(String codeM49) {
        this.codeM49 = codeM49;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getCodeM49() {
        return codeM49;
    }

    public String getAreaName() {
        return areaName;
    }

    public Coordinate getCoords() {
        return coords;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Area)) return false;
        Area area = (Area) o;
        return areaCode.equals(area.areaCode) && codeM49.equals(area.codeM49) && areaName.equals(area.areaName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaCode, codeM49, areaName);
    }

    @Override
    public String toString() {
        return "Area{" +
                "areaCode='" + areaCode + '\'' +
                ", codeM49='" + codeM49 + '\'' +
                ", areaName='" + areaName + '\'' +
                '}';
    }


    //TO:DO
    @Override
    public int compareTo(Area o) {
        return this.areaName.compareTo(o.getAreaName());
    }
}
