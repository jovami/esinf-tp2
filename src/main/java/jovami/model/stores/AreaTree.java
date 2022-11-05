package jovami.model.stores;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import jovami.model.Area;
import jovami.trees.AVL;


public class AreaTree {

    private final AVL<Area> tree ;

    public AreaTree(){
        this.tree = new AVL<>();
    }

    public AVL<Area> getTree() {
        return this.tree;
    }

    public void addArea(Area area) {
         this.tree.insert(area);
    }

    public Optional<Area> getAreaByAreaCode(String areaCode) {
        AtomicReference<Area> ref = new AtomicReference<>();

        this.tree.forEach(area -> {
            // FIXME: change areaCode to integer
            String code = area.getAreaCode();
            if (code != null && code.equals(areaCode))
                ref.setPlain(area);
        });

        return Optional.ofNullable(ref.getPlain());
    }


    public Optional<Area> getAreaByAreaName(String areaName) {
        Area tmp = new Area(null, null, areaName, 0, 0, null);

        return this.getAreaByArea(tmp);
    }

    public Optional<Area> getAreaByArea(Area area) {
        return this.tree.find(area);
    }

    /**
     * checks if area is already in the tree
     * @param area
     * @return boolean
     */
    public boolean exists(Area area) {
        return tree.find(area).isPresent();
    }
}
