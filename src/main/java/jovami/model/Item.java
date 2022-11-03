package jovami.model;

import jovami.trees.AVL;

import java.util.Objects;

public class Item implements Comparable<Item> {

    private String itemCode;
    private String itemCPC;
    private String itemDescription;



    private final AVL<Element> treeElement ;

    public Item(String itemCode, String itemCPC, String itemDescription) {
        this.itemCode = itemCode;
        this.itemCPC = itemCPC;
        this.itemDescription = itemDescription;

        this.treeElement =  new AVL<>();

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

    public boolean addElement(Element element)
    {
        this.treeElement.insert(element);
        return true;
    }

    public Element getElementByElementCode(String elementCode)
    {
        for(Element element: treeElement.inOrder())
        {
            if(element.getElementCode().compareToIgnoreCase(elementCode) == 0)
                return element;
        }

        return null;
    }






    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return itemCode.equals(item.itemCode) && itemCPC.equals(item.itemCPC) && itemDescription.equals(item.itemDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCode, itemCPC, itemDescription);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemCode='" + itemCode + '\'' +
                ", itemCPC='" + itemCPC + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                '}';
    }

    @Override
    public int compareTo(Item o) {
        return this.itemCode.compareTo(o.getItemCode());
    }
}
