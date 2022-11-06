package jovami.model.stores;

import java.util.Optional;

import jovami.model.Item;
import jovami.trees.AVL;

public class ItemTree {

    private final AVL<Item> tree;

    public ItemTree() {
        this.tree = new AVL<>();
    }

    public AVL<Item> getTree() {
        return tree;
    }

    public void addItem(Item item) {
        this.tree.insert(item);
    }


    public Optional<Item> getItemByItemCode(String itemCode) {
        Item tmp = new Item(itemCode, null, null);

        return this.getItemByItem(tmp);
    }

    public Optional<Item> getItemByItem(Item item) {
        return this.tree.find(item);
    }

    public boolean exists(Item item) {
        return tree.find(item).isPresent();
    }
}
