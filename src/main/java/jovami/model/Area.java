package jovami.model;

import jovami.trees.AVL;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public class Area implements Comparable<Area> {

    private String areaCode;
    private String codeM49;
    private String areaName;
    private Coordinate coords;
    private String country;

    private final AVL<Item> treeCode;
    private final AVL<Item> treeDesc;

    public static final Comparator<? super Area> cmpName =
        Comparator.comparing(Area::getAreaName);

    public static final Comparator<? super Area> cmpCode =
        Comparator.comparing(Area::getAreaCode);

    public Area(String areaCode, String codeM49, String areaName,
                double latitude, double longitude, String country)
    {
        this.areaCode = areaCode;
        this.codeM49 = codeM49;
        this.areaName = areaName;
        this.coords = new Coordinate(latitude,longitude);
        this.country = country;

        this.treeCode = new AVL<>(Item.cmpCode);
        this.treeDesc = new AVL<>(Item.cmpDesc);
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public void setCodeM49(String codeM49) {
        this.codeM49 = codeM49;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public String getCodeM49() {
        return this.codeM49;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public Coordinate getCoords() {
        return new Coordinate(this.coords);
    }

    public String getCountry() {
        return this.country;
    }

    //----------------------------------------
    //-------------AVL<Item>------------------

    public AVL<Item> getTreeCode() {
        return this.treeCode;
    }

    public AVL<Item> getTreeDesc() {
        return this.treeDesc;
    }

    public void addItem(Item item) {
        this.treeCode.insert(item);
        this.treeDesc.insert(item);
    }

    public Optional<Item> getItemByItemCode(String itemCode) {
        Item tmp = new Item(itemCode, null, null);
        return this.getItembyItem(tmp);
    }

    public Optional<Item> getItemByItemDescription(String desc) {
        Item tmp = new Item(null, null, desc);
        return this.treeDesc.find(tmp);
    }

    public Optional<Item> getItembyItem(Item item) {
        return this.treeCode.find(item);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Area)) return false;
        Area area = (Area) o;
        return areaCode.equals(area.areaCode)
            && codeM49.equals(area.codeM49)
            && areaName.equals(area.areaName)
            && coords.equals(area.coords)
            && country.equals(area.country)
            && treeCode.equals(area.treeCode)
            && treeDesc.equals(area.treeDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaCode, codeM49, areaName, coords, country, treeCode, treeDesc);
    }

    @Override
    public String toString() {
        return "Area{" +
                "areaCode='" + areaCode + '\'' +
                ", codeM49='" + codeM49 + '\'' +
                ", areaName='" + areaName + '\'' +
                ", longitude= " + coords.getLongitude() +
                ", latitude= " + coords.getLatitude() +
                ", country='" + country + '\'' +
                ", treeCode=\n" + treeCode +
                ", treeDesc=\n" + treeDesc +
                '}';
    }

    @Override
    public int compareTo(Area o) {
        return this.areaName.compareTo(o.areaName);
    }
}
