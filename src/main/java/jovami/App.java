package jovami;

import jovami.model.stores.AreaTree;
import jovami.model.stores.ItemTree;

/**
 * App
 * Singleton pattern based class,
 * used to obtain the PaisStore
 */
public class App {

    private final AreaTree areaTree;
    private final ItemTree itemTree;


    private App() {
        this.areaTree = new AreaTree();
        this.itemTree = new ItemTree();
    }

    public AreaTree getAreaTree(){
        return this.areaTree;
    }

    public ItemTree getItemTree(){
        return this.itemTree;
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