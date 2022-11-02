package jovami;

/**
 * App
 * Singleton pattern based class,
 * used to obtain the PaisStore
 */
public class App {


    private App() {

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