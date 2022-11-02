package jovami.model.stores;

import jovami.model.Area;
import jovami.model.Item;
import jovami.trees.AVL;

import java.util.HashMap;
import java.util.Map;

public class AreaTree {

    private final AVL<Area> tree ;

    public AreaTree(){
        this.tree = new AVL<>();
    }

    public AVL<Area> getTree(){return tree;}

    public boolean addArea(Area area)
    {

         this.tree.insert(area);
         return true;
    }

    public Area getAreaByAreaCode(String areaCode)
    {
        for(Area area: tree.inOrder())
        {
            if(area.getAreaCode().compareToIgnoreCase(areaCode) == 0)
                return area;
            else
                return null;
        }

        return null;
    }


    public Area getAreaByAreaName(String areaName)
    {
        for(Area area: tree.inOrder())
        {
            if(area.getAreaName().compareToIgnoreCase(areaName) == 0)
                return area;
        }

        return null;
    }

    public Area getAreaByArea(Area area)
    {
        for(Area a: tree.inOrder())
        {
            if(area.compareTo(a) == 0)
                return area;
        }

        return null;
    }


}
