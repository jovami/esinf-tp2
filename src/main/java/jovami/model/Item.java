package jovami.model;

import java.util.Objects;

public class Item {

    private String itemCode;
    private String itemCPC;
    private String itemDescription;

    public Item(String itemCode, String itemCPC, String itemDescription) {
        this.itemCode = itemCode;
        this.itemCPC = itemCPC;
        this.itemDescription = itemDescription;
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
}
