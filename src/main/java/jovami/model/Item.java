package jovami.model;

import jovami.trees.AVL;

import java.util.Objects;
import java.util.Optional;

public class Item implements Comparable<Item> {

    private String itemCode;
    private String itemCPC;
    private String itemDescription;
    private final AVL<Element> treeElement;

    public Item(String itemCode, String itemCPC, String itemDescription) {
        this.itemCode = itemCode;
        this.itemCPC = itemCPC;
        this.itemDescription = itemDescription;

        this.treeElement = new AVL<>();
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemCPC() {
        return itemCPC;
    }

    public String getItemDescription() {
        return itemDescription;
    }


    //----------------------------------------
    //-------------AVL<Element>---------------
    public AVL<Element> getTreeElement() {
        return treeElement;
    }

    public void addElement(Element element) {
        this.treeElement.insert(element);
    }

    public Optional<Element> getElementByElementCode(String elementCode) {
        Element tmp = new Element(elementCode, null);

        return this.getElementByElement(tmp);
    }

    public Optional<Element> getElementByElement(Element element) {
        return this.treeElement.find(element);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return itemCode.equals(item.itemCode)
            && itemCPC.equals(item.itemCPC)
            && itemDescription.equals(item.itemDescription)
            && treeElement.equals(item.treeElement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCode, itemCPC, itemDescription, treeElement);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemCode='" + itemCode + '\'' +
                ", itemCPC='" + itemCPC + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", treeElement=" + treeElement +
                '}';
    }

    @Override
    public int compareTo(Item o) {
        return this.itemCode.compareTo(o.getItemCode());
    }
}
