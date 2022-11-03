package jovami.model.stores;

import jovami.model.Item;
import jovami.trees.AVL;

public class ItemTree {

    private final AVL<Item> tree;

    public ItemTree(){this.tree = new AVL<>();}

    public AVL<Item> getTree(){return tree;}

    public boolean addItem(Item item)
    {
        this.tree.insert(item);
        return true;
    }

    
    public Item getItemByItemCode(String itemCode)
    {
        for(Item item: tree.inOrder())
        {
            if(item.getItemCode().compareToIgnoreCase(itemCode) == 0)
                return item;
            else 
                return null;
        }
        
        return null;
    }


    public Item getItemByItemCPC(String itemCPC)
    {
        for(Item item: tree.inOrder())
        {
            if(item.getItemCPC().compareToIgnoreCase(itemCPC) == 0)
                return item;
            else 
                return null;
        }
        
        return null;

    }

    public Item getItemByItem(Item item)
    {

        for(Item it: tree.inOrder())
        {

            if(item.compareTo(it) == 0)
                return item;
        }

        return null;
    }

    public boolean exists(Item item)
    {

        if(getItemByItem(item) != null)
            return true;
        else
            return false;
    }

}