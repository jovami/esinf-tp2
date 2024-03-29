package jovami;

import jovami.model.stores.AreaKDTree;
import jovami.model.stores.AreaTree;
import jovami.model.stores.FlagStore;
import jovami.model.stores.ItemTree;

/**
 * App
 * Singleton pattern based class,
 * used to obtain the PaisStore
 */
public class App {

    private final AreaTree areaTree;
    private final ItemTree itemTree;
    private final FlagStore flagStore;
    private final AreaKDTree areaKDTree;


    private App() {
        this.areaTree = new AreaTree();
        this.itemTree = new ItemTree();
        this.flagStore = new FlagStore();
        this.areaKDTree = new AreaKDTree();
    }

    public AreaTree getAreaTree(){
        return this.areaTree;
    }

    public AreaKDTree getKDAreaTree(){
        return this.areaKDTree;
    }

    public ItemTree getItemTree(){
        return this.itemTree;
    }

    public FlagStore flagStore() {
        return this.flagStore;
    }


    /* singleton pattern */
    private static App singleton = null;
    public static App getInstance() {
        if (singleton == null) {
            synchronized(App.class) {
                singleton = new App();
            }
        }
        return singleton;
    }
}
