package jovami.exercises;

import jovami.App;
import jovami.model.Area;
import jovami.trees.KDTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;

import static jovami.MainTest.*;
import static jovami.exercises.Exercise4DataSet.*;
import static jovami.exercises.Exercise4DataSet.shuffleFile;
import static org.junit.jupiter.api.Assertions.*;

class Exercise4Test {

    App app;
    Exercise4 ex4;

    @BeforeEach
    void setup() {
        resetSingleton();

        loadFlags(flagsFile);
        loadItemCodes(itemFile);
        loadCoords(coordsFile);
        loadShuffle(shuffleFile);

        ex4 = new Exercise4();
        app = App.getInstance();
    }

    @Test
    void getAreas() {
        KDTree<Area> expected = new KDTree<>();
        final String itemCode = "393", elementCode = "5510", year = "1992";
        //expected areas
        Area area1 = new Area("4", "'012", "Algeria", 28.033886, 1.659626, "DZ");
        Area area2 = new Area("255", "'056", "Belgium", 50.503887, 4.469936, "BE");
        Area area3 = new Area("79", "'276", "Germany", 51.165691, 10.451526, "DE");
        //insert in KDTree
        expected.insert(area1, area1.getCoords().getX(), area1.getCoords().getY());
        expected.insert(area2, area2.getCoords().getX(), area2.getCoords().getY());
        expected.insert(area3, area3.getCoords().getX(), area3.getCoords().getY());

        KDTree<Area> actual = ex4.getAreas(itemCode, elementCode, year);
        Iterator itActual = actual.inOrder().iterator();

        expected.inOrder().forEach(i -> {
            Area area = (Area) itActual.next();
            assertEquals(i.getAreaCode(), area.getAreaCode());
        });
    }

    @Test
    void getNearestArea() {
        final String itemCode = "393", elementCode = "5510", year = "1992";
        KDTree<Area> tree = ex4.getAreas(itemCode, elementCode, year);
        Area area1 = new Area("4", "'012", "Algeria", 28.033886, 1.659626, "DZ");
        Area area2 = new Area("255", "'056", "Belgium", 50.503887, 4.469936, "BE");
        Area area3 = new Area("79", "'276", "Germany", 51.165691, 10.451526, "DE");

        final double latitude1 = 35, longitude1 = 5;
        final double latitude2 = 50, longitude2 = 7;
        final double latitude3 = 40, longitude3 = 15;

        int[] testCoordX = new int[]          { +35,  +50, +40 };
        int[] testCoordY = new int[]          {  +5,   +7, +15 };
        int[] nearestAreaCode =  new int[]    {  +4, +255, +79 };

        IntStream.range(0, nearestAreaCode.length)
                .forEach(i -> {
                    assertEquals(String.valueOf(nearestAreaCode[i]), tree.nearestNeighbor(testCoordX[i], testCoordY[i]).getAreaCode());
                });
    }
}