package jovami.model;

import java.util.Objects;

public class Element {

    private String elementCode;
    private String elementType;

    public Element(String elementCode, String elementType) {
        this.elementCode = elementCode;
        this.elementType = elementType;
    }


    public String getElementCode() {
        return elementCode;
    }

    public String getElementType() {
        return elementType;
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
}
