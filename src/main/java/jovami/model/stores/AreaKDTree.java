package jovami.model.stores;

import jovami.model.Area;
import jovami.trees.KDTree;

public class AreaKDTree {
    private final KDTree<Area> areaKdTree;

    public AreaKDTree(){
        this.areaKdTree = new KDTree<>();
    }
    
    public void addNode(Area area, double x, double y){
        //System.out.println(area.toString()+"\n");
        this.areaKdTree.insert(area, x, y);
    }




}
