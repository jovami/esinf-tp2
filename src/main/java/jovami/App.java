package jovami;

import jovami.model.stores.AreaTree;

/**
 * App
 * Singleton pattern based class,
 * used to obtain the PaisStore
 */
public class App {

    private final AreaTree areaTree;


    private App() {
        this.areaTree = new AreaTree();
    }

    public AreaTree getAreaTree(){
        return this.areaTree;
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