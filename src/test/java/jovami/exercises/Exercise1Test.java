package jovami.exercises;

import jovami.App;
import jovami.MainTest;
import jovami.model.Area;
import jovami.model.reader.CSVHeader;
import jovami.model.reader.CSVReader;
import jovami.trees.AVL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Exercise1Test {

    Exercise1 exercise1;

    @BeforeEach
    void setup() {
        MainTest.beforeEach();
        exercise1 = new Exercise1();
    }

    @Test
    void run() {
        exercise1.run();
    }

    @Test
    void readAllFiles() {

        exercise1.readAllFiles();

    }

    @Test
    void listFilesForFolder() {
        
        final File folder = new File("src/main/ficheiroscsv");
        exercise1.listFilesForFolder(folder);

        //Saves the list of files in folder ficheiroscsv
        List<File> filenames = exercise1.filenames;

        //TEST TO COMPARE
        List<File> tempFilenames = new LinkedList<>();       
        final File folderx = new File("src/main/ficheiroscsv");

        for (final File fileEntry : folderx.listFiles()) {
                if(fileEntry.getName().contains(".csv"))
                    tempFilenames.add(fileEntry);
            }


        assertTrue(filenames.containsAll(tempFilenames),"List filenames not contains all files present in tempFilenames");
        
    }

    @Test
    void saveInfoAreaCoordinates() throws Exception {
        
        final File folder = new File("src/main/ficheiroscsv");
        exercise1.listFilesForFolder(folder);
        List<File> filenames = exercise1.filenames;
        CSVReader csvReader = new CSVReader(CSVHeader.HEADER_AREACOORDINATES);

        List<String[]> list = new ArrayList<>();

        for (File file : filenames) {
            if(file.getName().contains("AreaCoordinates"))
            {
                File dir = exercise1.fileDirReader(file.getName());   
                list = csvReader.readCSV(dir);
            }
        }
        
        exercise1.saveInfoAreaCoordinates(list);
       
        List<String[]> temp = new ArrayList<>();
        for(String[] ls: list)
        {
            String[] t = new String[4];
                               
            t[0] = ls[0];

            if(ls[1].matches(".*\\s.*") )
                t[1] = ls[1].substring(0, ls[1].indexOf(' '));

            if(ls[2].matches(".*\\s.*") )
            {
                t[2] = ls[2].substring(0, ls[2].indexOf(' '));
                t[1] = ls[1];
            }else{
                t[1] = ls[1];
                t[2] = ls[2];
            }
                
            t[3] = ls[3];
            
            temp.add(t);
            
        }
        

        AVL<Area> tree = App.getInstance().getAreaTree().getTree();
        List<String[]> tempx = new ArrayList<>();
        for(Area area: tree.inOrder())
        {
            String[] t = new String[4];
                               
            t[0] = area.getCountry();
            t[1] = Double.toString(area.getCoords().getLatitude());
            t[2] = Double.toString(area.getCoords().getLongitude());
            t[3] = area.getAreaName();
            
            tempx.add(t);
        }

        for(String[] ls : temp)
        {
            if(tempx.contains(ls))
                assertTrue(true);               
        }

    }

    @Test
    void saveInfoItemCodes() {
    }


    @Test
    void saveInfoShuffle() {
    }

    @Test
    void fileDirReader() {
    }


}