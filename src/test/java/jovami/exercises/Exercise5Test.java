package jovami.exercises;

import java.util.stream.IntStream;


import static jovami.MainTest.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jovami.App;
import jovami.model.Area;
import jovami.model.Element;
import jovami.model.Item;
import jovami.trees.KDTree;
import static jovami.exercises.Exercise5TestDataSet.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Exercise5Test {
    App app;
    Exercise5 ex5;
    
    @BeforeEach
    void setUp() {
        resetSingleton();

        loadFlags(flagsFile);
        loadCoords(coordsFile);
        loadShuffle(shuffleFile);
        loadItemCodes(itemFile);

        ex5 = new Exercise5();
        app = App.getInstance();




        // Item itemMillet = new Item("79", "'0118","Millet");
        // Item itemCamels = new Item("1808", "'F1808","Meat; Poultry");

        // Element element = new Element("5312", "Area harvested");

        // Area pt = new Area("174","'620","Portugal",39.399872,-8.224454,"PT");
        // pt.addItem(new Item(itemCamels.getItemCode(),itemCamels.getItemCPC(),itemCamels.getItemDescription()));
        // pt.getItemByItemCode(itemCamels.getItemCode()).get().addElement(new Element(element.getElementCode(),element.getElementType()));




        // pt.addItem(new Item(itemMillet.getItemCode(),itemMillet.getItemCPC(),itemMillet.getItemDescription()));
        




        // areaTree.insert(pt, pt.getCoords().getX(),pt.getCoords().getY());

        // Area fr = new Area("174","'620","Portugal",46.227638,2.213749,"PT");
        // areaTree.insert(fr, fr.getCoords().getX(),fr.getCoords().getY());

        // Area it = new Area("174","'620","Portugal",41.87194,12.56738,"PT");
        // areaTree.insert(it, it.getCoords().getX(),it.getCoords().getY());

        // Area es = new Area("203","'724","Espanha",40.463667,-3.74922,"ES");
        // areaTree.insert(es, es.getCoords().getX(),es.getCoords().getY());



        // areaTree = new KDTree<>();
        // IntStream.range(0, values.length)
        //          .forEach(i -> areaTree.insert(pt, coordx[i], coordy[i]));
    }
    

    @Test
    void checkResultes(){
        
        String itemCode = "1058";
        String elementCode = "5510";
        String yearCode = "2018";
        
        double latitudeInicial = -30;
        double latitudeFinal = 30;

        double longitudeInicial = 10;
        double longitudeFinal = 40;

        Item itemDesired=app.getItemTree().getItemByItemCode(itemCode).get();
        double sum = ex5.getRangeSum(elementCode, yearCode, latitudeInicial, latitudeFinal, longitudeInicial, longitudeFinal, itemDesired);

        double expected = 96122;
        assertEquals(sum,expected);
    }


    @Test
    void yearNotExists(){
        
        String itemCode = "1058";
        String elementCode = "5510";
        String yearCode = "1999";
        
        double latitudeInicial = -30;
        double latitudeFinal = 30;

        double longitudeInicial = 10;
        double longitudeFinal = 40;

        Item itemDesired=app.getItemTree().getItemByItemCode(itemCode).get();
        assertEquals(0,ex5.getRangeSum(elementCode, yearCode, latitudeInicial, latitudeFinal, longitudeInicial, longitudeFinal, itemDesired));
    }
    
    @Test
    void elementNotExists(){
        
        String itemCode = "1058";
        String elementCode = "0";
        String yearCode = "2018";
        
        double latitudeInicial = -30;
        double latitudeFinal = 30;

        double longitudeInicial = 10;
        double longitudeFinal = 40;
        
        Item itemDesired=app.getItemTree().getItemByItemCode(itemCode).get();
        assertEquals(0,ex5.getRangeSum(elementCode, yearCode, latitudeInicial, latitudeFinal, longitudeInicial, longitudeFinal, itemDesired));
    }
    
    
    @Test
    void ItemNotInRange(){
        
        String itemCode = "882";
        String elementCode = "5318";
        String yearCode = "1993";
        
        double latitudeInicial = -30;
        double latitudeFinal = 30;

        double longitudeInicial = 10;
        double longitudeFinal = 40;
        
        Item itemDesired=app.getItemTree().getItemByItemCode(itemCode).get();
        assertEquals(1343000,ex5.getRangeSum(elementCode, yearCode, latitudeInicial, latitudeFinal, longitudeInicial, longitudeFinal, itemDesired));
    }
    


    @Test
    void coordChanged(){
        
        String itemCode = "1058";
        String elementCode = "5510";
        String yearCode = "2018";
        
        double latitudeInicial = 30;
        double latitudeFinal = -30;

        double longitudeInicial = 40;
        double longitudeFinal = -10;

        Item itemDesired=app.getItemTree().getItemByItemCode(itemCode).get();
        double sum = ex5.getRangeSum(elementCode, yearCode, latitudeInicial, latitudeFinal, longitudeInicial, longitudeFinal, itemDesired);

        double expected = 96122;
        assertEquals(sum,expected);
    }
    

    @Test
    void valueNull(){
        
        String itemCode = "1765";
        String elementCode = "5510";
        String yearCode = "1988";
        
        double latitudeInicial = 40;
        double latitudeFinal = 50;

        double longitudeInicial = 20;
        double longitudeFinal = 30;

        Item itemDesired=app.getItemTree().getItemByItemCode(itemCode).get();
        double sum = ex5.getRangeSum(elementCode, yearCode, latitudeInicial, latitudeFinal, longitudeInicial, longitudeFinal, itemDesired);

        double expected = 0;
        assertEquals(sum,expected);
    }
}
