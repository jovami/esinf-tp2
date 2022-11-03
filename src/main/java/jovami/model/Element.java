package jovami.model;

import jovami.trees.AVL;

import java.util.Objects;

public class Element implements Comparable<Element> {

    private String elementCode;
    private String elementType;

    private final AVL<Year> treeYear ;



    public Element(String elementCode, String elementType) {
        this.elementCode = elementCode;
        this.elementType = elementType;

        this.treeYear =  new AVL<>();
    }


    public String getElementCode() {
        return elementCode;
    }

    public String getElementType() {
        return elementType;
    }


    //-----------------------------------------
    //-------------AVL<Year>------------------

    public AVL<Year> getTreeYear() {
        return treeYear;
    }

    public boolean addYear(Year year)
    {
        this.treeYear.insert(year);
        return true;
    }

    public Year getYearByYear(Year year)
    {
        for(Year yr : treeYear.inOrder())
        {
            if(yr.getYear() == year.getYear())
                return year;
        }

        return null;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Element)) return false;
        Element element = (Element) o;
        return elementCode.equals(element.elementCode) && elementType.equals(element.elementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elementCode, elementType);
    }

    @Override
    public String toString() {
        return "Element{" +
                "elementCode='" + elementCode + '\'' +
                ", elementType='" + elementType + '\'' +
                '}';
    }

    @Override
    public int compareTo(Element o) {
        return this.elementCode.compareTo(o.getElementCode());
    }
}
