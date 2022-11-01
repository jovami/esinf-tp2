package jovami.model;

import java.awt.geom.Point2D;
import java.util.Objects;

public class Area {

    private String areaCode;
    private String codeM49;
    private String areaNome;
    private Coordinate coords;
    private String countryName;


    public Area(String areaCode, String codeM49, String areaNome, double latitude, double longitude, String countryName) {
        this.areaCode = areaCode;
        this.codeM49 = codeM49;
        this.areaNome = areaNome;
        this.coords = new Coordinate(latitude,longitude);
        this.countryName = countryName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getCodeM49() {
        return codeM49;
    }

    public String getAreaNome() {
        return areaNome;
    }

    public Coordinate getCoords() {
        return coords;
    }

    public String getCountryName() {
        return countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Area)) return false;
        Area area = (Area) o;
        return areaCode.equals(area.areaCode) && codeM49.equals(area.codeM49) && areaNome.equals(area.areaNome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaCode, codeM49, areaNome);
    }

    @Override
    public String toString() {
        return "Area{" +
                "areaCode='" + areaCode + '\'' +
                ", codeM49='" + codeM49 + '\'' +
                ", areaNome='" + areaNome + '\'' +
                '}';
    }
}
