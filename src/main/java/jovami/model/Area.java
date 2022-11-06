package jovami.model;

import jovami.trees.AVL;

import java.util.Objects;
import java.util.Optional;

public class Area implements Comparable<Area> {

    private String areaCode;
    private String codeM49;
    private String areaName;
    private Coordinate coords;
    private String country;
    private final AVL<Item> treeItem;


    public Area(String areaCode, String codeM49, String areaName,
                double latitude, double longitude, String country)
    {
        this.areaCode = areaCode;
        this.codeM49 = codeM49;
        this.areaName = areaName;
        this.coords = new Coordinate(latitude,longitude);
        this.country = country;
        this.treeItem = new AVL<>();
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
        return new Coordinate(this.coords);
    }

    public String getCountry() {
        return country;
    }

    //----------------------------------------
    //-------------AVL<Item>------------------

    public AVL<Item> getTreeItem() {
        return treeItem;
    }

    public void addItem(Item item) {
        this.treeItem.insert(item);
    }

    // FIXME: change itemCode to int
    public Optional<Item> getItemByItemCode(String itemCode) {
        Item tmp = new Item(itemCode, null, null);

        return this.getItembyItem(tmp);
    }

    public Optional<Item> getItembyItem(Item item) {
        return this.treeItem.find(item);
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
            && treeItem.equals(area.treeItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaCode, codeM49, areaName, coords, country, treeItem);
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
                ", treeItem=\n" + treeItem +
                '}';
    }

    //TO:DO
    @Override
    public int compareTo(Area o) {
        return this.areaName.compareTo(o.areaName);
    }
}
