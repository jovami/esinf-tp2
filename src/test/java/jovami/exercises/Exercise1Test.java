package jovami.exercises;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jovami.App;
import jovami.MainTest;
import jovami.model.Area;
import jovami.model.Element;
import jovami.model.Item;
import jovami.model.Year;
import jovami.model.reader.CSVHeader;
import jovami.model.reader.CSVReader;
import jovami.trees.AVL;

class Exercise1Test {

    Exercise1 exercise1;

    @BeforeEach
    void setup() {
        MainTest.beforeEach();
        exercise1 = new Exercise1();
    }

    @Test
    void run() {exercise1.run();}

    @Test
    void readAllFiles() {exercise1.readAllFiles();}


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
    void saveInfoItemCodes() throws Exception {
        final File folder = new File("src/main/ficheiroscsv");
        exercise1.listFilesForFolder(folder);
        List<File> filenames = exercise1.filenames;
        CSVReader csvReader = new CSVReader(CSVHeader.HEADER_ITEMCODES);

        List<String[]> list = new ArrayList<>();

        for (File file : filenames) {
            if(file.getName().contains("ItemCodes_shuffled"))
            {
                File dir = exercise1.fileDirReader(file.getName());
                list = csvReader.readCSV(dir);
            }
        }

        exercise1.saveInfoItemCodes(list);


        AVL<Item> tree = App.getInstance().getItemTree().getTree();
        List<String[]> tempx = new ArrayList<>();
        for(Item item: tree.inOrder())
        {
            String[] t = new String[3];

            t[0] = item.getItemCode();
            t[1] = item.getItemCPC();
            t[2] = item.getItemDescription();
            tempx.add(t);
        }

        int count = list.size();

        int countx=0;
        for(String[] arrayLine : list)
        {
                for(String[] arrLine : tempx)
                {
                    if (arrayLine[0].compareToIgnoreCase(arrLine[0]) == 0
                        && arrayLine[1].compareToIgnoreCase(arrLine[1]) == 0
                            && arrayLine[2].compareToIgnoreCase(arrLine[2]) == 0)
                        countx++;
                }
        }

        assertEquals(count, countx);

    }


    @Test
    void saveInfoShuffle() throws Exception {
        final File folder = new File("src/main/ficheiroscsv");
        exercise1.listFilesForFolder(folder);
        List<File> filenames = exercise1.filenames;
        CSVReader csvReader = new CSVReader(CSVHeader.HEADER_SHUFFLE);

        List<String[]> list = new ArrayList<>();

        for (File file : filenames) {
            if(file.getName().contains("Production_Crops_Livestock_FR_GER_IT_PT_SP_shuffle_small"))
            {
                File dir = exercise1.fileDirReader(file.getName());   
                list = csvReader.readCSV(dir);
            }
        }

        exercise1.saveInfoShuffle(list);

        
        int count = list.size();

        AVL<Area> treeArea = App.getInstance().getAreaTree().getNameTree();
        List<String[]> tempx = new ArrayList<>();
        for(Area area: treeArea.inOrder())
        {           
            for(Item item: area.getTreeCode().inOrder())
            {
                for(Element element :  item.getTreeCode().inOrder())
                {
                    for(Year year : element.getTreeYear().inOrder())
                    {
                        String[] temp = new String[13];
                        
                        temp[0] = area.getAreaCode();
                        temp[1] = area.getCodeM49();
                        temp[2] = area.getAreaName();
                        temp[3] = item.getItemCode();   
                        temp[4] = item.getItemCPC();
                        temp[5] = item.getItemDescription();
                        temp[6] = element.getElementCode();
                        temp[7] = element.getElementType();
                        temp[8] = year.getYearCode();
                        temp[9] = String.valueOf(year.getYear());
                        temp[10] = year.getValue().getUnit();
                        temp[11] = String.valueOf(year.getValue().getValue());
                        temp[12] = Character.toString(year.getValue().getFlag().code());                         
                        tempx.add(temp);
                    }                  
                }               
            }
        }

        
        int countx=0;
        for(String[] arrayLine : list)
        {
                for(String[] arrLine : tempx)
                {
                    if (arrayLine[0].compareToIgnoreCase(arrLine[0]) == 0
                        && arrayLine[1].compareToIgnoreCase(arrLine[1]) == 0
                        && arrayLine[2].compareToIgnoreCase(arrLine[2]) == 0
                        && arrayLine[3].compareToIgnoreCase(arrLine[3]) == 0
                        && arrayLine[4].compareToIgnoreCase(arrLine[4]) == 0
                        && arrayLine[5].compareToIgnoreCase(arrLine[5]) == 0
                        && arrayLine[6].compareToIgnoreCase(arrLine[6]) == 0
                        && arrayLine[7].compareToIgnoreCase(arrLine[7]) == 0
                        && arrayLine[8].compareToIgnoreCase(arrLine[8]) == 0
                        && arrayLine[9].compareToIgnoreCase(arrLine[9]) == 0
                        && arrayLine[10].compareToIgnoreCase(arrLine[10]) == 0
                        && arrayLine[12].compareToIgnoreCase(arrLine[12]) == 0)
                        countx++;
                }
        }


        assertEquals(count, countx);
    }

    @Test
    void fileDirReader() throws Exception{

        final File folder = new File("src/main/ficheiroscsv");
        exercise1.listFilesForFolder(folder);
        List<File> filenames = exercise1.filenames;

       
        File dir ;
        File expected = new File("Production_Crops_Livestock_FR_GER_IT_PT_SP_shuffle_small.csv");

        for (File file : filenames)
        {
            if(file.getName().contains("Production_Crops_Livestock_FR_GER_IT_PT_SP_shuffle_small"))
            {
                dir = exercise1.fileDirReader(file.getName());  
                assertTrue(dir.getName().contains(expected.getName()));
                
            }
        }

    }

    @Test
    void fileDirReader2() throws Exception{

    
        Exception exception = assertThrows(Exception.class, () -> {

            File dir = exercise1.fileDirReader("X"); 
        });
    
        String expectedMessage = "error: file not found! X";
        String actualMessage = exception.getMessage();
    
        
        assertTrue(actualMessage.contains(expectedMessage));
    

    }



    

}
