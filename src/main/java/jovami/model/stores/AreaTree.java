package jovami.model.stores;

import jovami.model.Area;
import jovami.trees.AVL;


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
            if(area.getAreaCode().compareToIgnoreCase("") != 0 )
                if (area.getAreaCode().compareToIgnoreCase(areaCode) == 0)
                    return area;

        }

        return null;
    }

    public Area getAreaByCodeM49(String codeM49)
    {
        for(Area area: tree.inOrder())
        {
            if(area.getCodeM49().compareToIgnoreCase(codeM49) == 0)
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

    /**
     * checks if area is already leaf in the tree
     * @param area
     * @return boolean
     */
    public boolean exists(Area area)
    {

        if(getAreaByAreaName(area.getAreaName()).getAreaName().compareToIgnoreCase(area.getAreaName()) == 0) {
            if(getAreaByAreaName(area.getAreaName()).getAreaCode().compareToIgnoreCase("") == 0
                && getAreaByAreaName(area.getAreaName()).getCodeM49().compareToIgnoreCase("") == 0 )
                return true;
        }

        return false;
        
    }


}