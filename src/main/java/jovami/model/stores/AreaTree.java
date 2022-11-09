package jovami.model.stores;

import java.util.Optional;

import jovami.model.Area;
import jovami.trees.AVL;


public class AreaTree {

    private final AVL<Area> nameTree;
    private final AVL<Area> codeTree;

    public AreaTree(){
        this.nameTree = new AVL<>(Area.cmpName);
        this.codeTree = new AVL<>(Area.cmpCode);
    }

    public AVL<Area> getNameTree() {
        return this.nameTree;
    }

    public AVL<Area> getCodeTree() {
        return this.codeTree;
    }

    public void addArea(Area area) {
        this.nameTree.insert(area);
    }

    public void fillCodeTree() {
        this.nameTree.forEach(codeTree::insert);
    }

    public Optional<Area> getAreaByAreaCode(String areaCode) {
        Area tmp = new Area(areaCode, null, null, 0, 0, null);
        return this.codeTree.find(tmp);
    }

    public Optional<Area> getAreaByAreaName(String areaName) {
        Area tmp = new Area(null, null, areaName, 0, 0, null);
        return this.getAreaByArea(tmp);
    }

    public Optional<Area> getAreaByArea(Area area) {
        return this.nameTree.find(area);
    }

    /**
     * checks if area is already in the tree
     * @param area area
     * @return boolean
     */
    public boolean exists(Area area) {
        return nameTree.find(area).isPresent()
            || codeTree.find(area).isPresent();
    }
}
