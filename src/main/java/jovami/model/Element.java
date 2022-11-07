package jovami.model;

import jovami.trees.AVL;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public class Element implements Comparable<Element> {

    private String elementCode;
    private String elementType;

    private final AVL<Year> treeYear;

    public static final Comparator<? super Element> cmpCode =
        Comparator.comparing(Element::getElementCode);

    public static final Comparator<? super Element> cmpType =
        Comparator.comparing(Element::getElementType);

    public Element(String elementCode, String elementType) {
        this.elementCode = elementCode;
        this.elementType = elementType;

        this.treeYear = new AVL<>();
    }

    public String getElementCode() {
        return this.elementCode;
    }

    public String getElementType() {
        return this.elementType;
    }


    //-----------------------------------------
    //-------------AVL<Year>------------------

    public AVL<Year> getTreeYear() {
        return this.treeYear;
    }

    public void addYear(Year year) {
        this.treeYear.insert(year);
    }

    public Optional<Year> getYearByYearCode(String yearCode) {
        Optional<Year> ret;
        try {
            ret = this.getYearByYear(Integer.parseInt(yearCode));
        } catch (NumberFormatException e) {
            ret = Optional.empty();
        }

        return ret;
    }

    public Optional<Year> getYearByYear(int year) {
        Year tmp = new Year(null, year);
        return this.getYearByYear(tmp);
    }

    public Optional<Year> getYearByYear(Year year)
    {
        return this.treeYear.find(year);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Element)) return false;
        Element element = (Element) o;
        return elementCode.equals(element.elementCode) &&
            elementType.equals(element.elementType) &&
            treeYear.equals(element.treeYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elementCode, elementType, treeYear);
    }

    @Override
    public String toString() {
        return "Element{" +
                "elementCode='" + elementCode + '\'' +
                ", elementType='" + elementType + '\'' +
                ", treeYear=" + treeYear +
                '}';
    }

    @Override
    public int compareTo(Element o) {
        return this.elementCode.compareTo(o.getElementCode());
    }
}
