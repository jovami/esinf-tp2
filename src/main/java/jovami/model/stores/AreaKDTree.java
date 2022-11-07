package jovami.model.stores;

import jovami.model.Area;
import jovami.trees.KDTree;

public class AreaKDTree {
    private final KDTree<Area> areaKdTree;

    public AreaKDTree(){
        this.areaKdTree = new KDTree<>();
    }
    public void addNode(Area area, double latitude, double longitude){
        this.areaKdTree.insert(area, latitude, longitude);
    }

    public KDTree<Area> getKDtree(){
        return this.areaKdTree;
    }

}
