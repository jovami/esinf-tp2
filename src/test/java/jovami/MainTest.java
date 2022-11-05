package jovami;

import jovami.exercises.Exercise1;
import jovami.model.reader.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {


    public static void beforeEach() {
        resetSingleton();

        new Exercise1().readAllFiles();

    }

    public static void resetSingleton() {
        try {
            Field instance = App.class.getDeclaredField("singleton");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}