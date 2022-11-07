package jovami.model;

import jovami.trees.AVL;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public class Item implements Comparable<Item> {

    private String itemCode;
    private String itemCPC;
    private String itemDescription;

    private final AVL<Element> treeCode;
    private final AVL<Element> treeType;

    public static final Comparator<? super Item> cmpCode =
        Comparator.comparing(Item::getItemCode);
    public static final Comparator<? super Item> cmpDesc =
        Comparator.comparing(Item::getItemDescription);

    public Item(String itemCode, String itemCPC, String itemDescription) {
        this.itemCode = itemCode;
        this.itemCPC = itemCPC;
        this.itemDescription = itemDescription;

        this.treeCode = new AVL<>(Element.cmpCode);
        this.treeType = new AVL<>(Element.cmpType);
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public String getItemCPC() {
        return this.itemCPC;
    }

    public String getItemDescription() {
        return this.itemDescription;
    }


    //----------------------------------------
    //-------------AVL<Element>---------------
    public AVL<Element> getTreeCode() {
        return this.treeCode;
    }

    public AVL<Element> getTreeType() {
        return this.treeType;
    }

    public void addElement(Element element) {
        this.treeCode.insert(element);
        this.treeType.insert(element);
    }

    public Optional<Element> getElementByElementCode(String elementCode) {
        Element tmp = new Element(elementCode, null);
        return this.getElementByElement(tmp);
    }

    public Optional<Element> getElementByElementType(String elementType) {
        Element tmp = new Element(null, elementType);
        return this.treeType.find(tmp);
    }

    public Optional<Element> getElementByElement(Element element) {
        return this.treeCode.find(element);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return itemCode.equals(item.itemCode)
            && itemCPC.equals(item.itemCPC)
            && itemDescription.equals(item.itemDescription)
            && treeCode.equals(item.treeCode)
            && treeType.equals(item.treeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCode, itemCPC, itemDescription, treeCode, treeType);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemCode='" + itemCode + '\'' +
                ", itemCPC='" + itemCPC + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", treeCode=" + treeCode +
                ", treeType=" + treeType +
                '}';
    }

    @Override
    public int compareTo(Item o) {
        return this.itemCode.compareTo(o.getItemCode());
    }
}
