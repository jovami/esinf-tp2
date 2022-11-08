package jovami.exercises;

import jovami.App;
import jovami.model.*;
import jovami.model.reader.CSVHeader;
import jovami.model.reader.CSVReader;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Exercise1 implements Runnable {

    List<File> filenames = new LinkedList<>();
    private final App app;
    private CSVReader csvReader;


    public Exercise1() {
        app = App.getInstance();
    }


    @Override
    public void run() {

        readAllFiles();
        
    }


    private enum ColunasAreaCoordinates {
        COUNTRY(0), LATITUDE(1), LONGITUDE(2), AREANOME(3);

        private final int i;

        ColunasAreaCoordinates(int i) {
            this.i = i;
        }

        private int getColuna() {
            return i;
        }
    }


    private enum ColunasItemCodes {

        ITEMCODE(0), ITEMCPC(1), ITEMDESCRIPTION(2);

        private final int i;

        ColunasItemCodes(int i) {
            this.i = i;
        }

        private int getColuna() {
            return i;
        }
    }


    private enum ColunasFlags {

        FLAGCODE(0), DESCRIPTION(1);


        private final int i;

        ColunasFlags(int i) {
            this.i = i;
        }

        private int getColuna() {
            return i;
        }

    }


    private enum ColunasShuffle {
        AREACODE(0), CODEM49(1), AREANAME(2), ITEMCODE(3), ITEMCPC(4), ITEMDESCRIPTION(5), ELEMENTCODE(6),
        ELEMENTTYPE(7), YEARCODE(8), YEAR(9), UNIT(10), VALUE(11), FLAGTYPE(12);

        private final int i;

        ColunasShuffle(int i) {
            this.i = i;
        }

        private int getColuna() {
            return i;
        }


    }


    public void readAllFiles() {
        final File folder = new File("src/main/ficheiroscsv");
        listFilesForFolder(folder);

        for (File f : filenames) {
            String name = f.getName();
            //System.out.println(""+f.getName());
            if (name.contains("Production_Crops_Livestock_E_Flags")) {
                try {
                    File dir = fileDirReader(name);
                    this.csvReader = new CSVReader(CSVHeader.HEADER_FLAGS);
                    saveInfoFlags(csvReader.readCSV(dir));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (name.contains("AreaCoordinates")) {
                try {
                    File dir = fileDirReader(name);
                    this.csvReader = new CSVReader(CSVHeader.HEADER_AREACOORDINATES);
                    saveInfoAreaCoordinates(csvReader.readCSV(dir));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (name.contains("ItemCodes_shuffled")) {
                try {
                    File dir = fileDirReader(name);
                    this.csvReader = new CSVReader(CSVHeader.HEADER_ITEMCODES);
                    csvReader.readCSV(dir);
                    saveInfoItemCodes(csvReader.readCSV(dir));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (File f : filenames) {
            String name = f.getName();
            if (name.contains("Production_Crops_Livestock_World_shuffle_small")) {
                /*else if(f.getName().contains("shuffle_large") || f.getName().contains("shuffle_medium")
                    || f.getName().contains("shuffle_small")) */
                //"Production_Crops_Livestock_World_shuffle_small"
                //"Production_Crops_Livestock_FR_GER_IT_PT_SP_shuffle_small"
                try {
                    File dir = fileDirReader(name);
                    this.csvReader = new CSVReader(CSVHeader.HEADER_SHUFFLE);
                    csvReader.readCSV(dir);
                    saveInfoShuffle(csvReader.readCSV(dir));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                if (fileEntry.getName().contains(".csv"))
                    filenames.add(fileEntry);
            }
        }
    }

    public void saveInfoFlags(List<String[]> list) {
        char code;
        String name;


        var store = app.flagStore();                                            //O(1)
        

        for (String[] info : list) {                                            //O(n * inside)
            code = info[ColunasFlags.FLAGCODE.getColuna()].charAt(0);    //O(1)  
            name = info[ColunasFlags.DESCRIPTION.getColuna()];                 //O(1)
             store.add(code, name);                                            //O(1)
                                                                    //Worst-case time complexity: O(n)    
        }
    }

    public void saveInfoAreaCoordinates(List<String[]> list) {
        // String areaCode, codeM49;
        String areaName, country;
        double latitude, longitude;


        for(String[] info: list)                                                                                                   //O(n * inside)
        {
            country = info[ColunasAreaCoordinates.COUNTRY.getColuna()];                                                            //O(1)                                                   

            //linha 98 do ficheiro "AreaCoordinates ,na coluna longitude tem dois numeros"
            if(info[ColunasAreaCoordinates.LATITUDE.getColuna()].matches(".*\\s.*") )                                        //O(1)
                latitude = Double.parseDouble(info[ColunasAreaCoordinates.LATITUDE.getColuna()].                                   //O(1)  
                            substring(0, info[ColunasAreaCoordinates.LATITUDE.getColuna()].indexOf(' ')));

            if(info[ColunasAreaCoordinates.LONGITUDE.getColuna()].matches(".*\\s.*"))                                       //O(1)
            {
                longitude = Double.parseDouble(info[ColunasAreaCoordinates.LONGITUDE.getColuna()].                                //O(1)
                            substring(0, info[ColunasAreaCoordinates.LONGITUDE.getColuna()].indexOf(' ')));

                latitude = Double.parseDouble(info[ColunasAreaCoordinates.LATITUDE.getColuna()]);                                 //O(1)                  
            }else{
                latitude = Double.parseDouble(info[ColunasAreaCoordinates.LATITUDE.getColuna()]);                                 //O(1)
                longitude = Double.parseDouble(info[ColunasAreaCoordinates.LONGITUDE.getColuna()]);                               //O(1)

            }

            areaName = info[ColunasAreaCoordinates.AREANOME.getColuna()];                                                         //O(1)


            saveAreaCoordinates(areaName, latitude, longitude, country);                                                          //O(1)

        }                                                                                                              //Worst-case time complexity: O(n)  

    }


    private void saveAreaCoordinates(String areaName, double latitude, double longitude, String country) {
        Area area = new Area("", "", areaName, latitude, longitude, country);
        app.getAreaTree().addArea(area);
        app.getKDAreaTree().addNode(area, latitude, longitude);
    }


    public void saveInfoItemCodes(List<String[]> list) {
        String itemCode, itemCPC, itemDescription;


        for(String[] info: list)                                                                                        //O(n * inside)
        {
            itemCode = info[ColunasItemCodes.ITEMCODE.getColuna()];                                                     //O(1)           
            itemCPC = info[ColunasItemCodes.ITEMCPC.getColuna()];                                                       //O(1)       
            itemDescription = info[ColunasItemCodes.ITEMDESCRIPTION.getColuna()];                                       //O(1)


            saveItemCodes(itemCode, itemCPC, itemDescription);                                                          //O(1)        
                                                                                                            //Worst-case time complexity: O(n)
        }
    }


    private void saveItemCodes(String itemCode, String itemCPC, String itemDescription) {
        Item item = new Item(itemCode, itemCPC, itemDescription);

        app.getItemTree().addItem(item);
    }


    public void saveInfoShuffle(List<String[]> list) {
        //"Area Code,Area Code (M49),Area,Item Code,Item Code (CPC),Item,Element Code,Element,Year Code,Year,Unit,Value,Flag";
        String areaCode, codeM49, areaName, itemCode, itemCPC, itemDescription, elementCode, elementType, yearCode, unit;
        char flag;
        int year;
        float value;



        for(String[] info: list)                                                                            //O(n * inside)
        {
            areaCode = info[ColunasShuffle.AREACODE.getColuna()];                                           //O(1)
            codeM49  = info[ColunasShuffle.CODEM49.getColuna()];                                            //O(1)
            areaName = info[ColunasShuffle.AREANAME.getColuna()];                                           //O(1)
            itemCode = info[ColunasShuffle.ITEMCODE.getColuna()];                                           //O(1)
            itemCPC  = info[ColunasShuffle.ITEMCPC.getColuna()];                                            //O(1)
            itemDescription = info[ColunasShuffle.ITEMDESCRIPTION.getColuna()];                             //O(1)
            elementCode = info[ColunasShuffle.ELEMENTCODE.getColuna()];                                     //O(1)
            elementType = info[ColunasShuffle.ELEMENTTYPE.getColuna()];                                     //O(1)
            yearCode = info[ColunasShuffle.YEARCODE.getColuna()];                                           //O(1)
            year = Integer.parseInt(info[ColunasShuffle.YEAR.getColuna()]);                                 //O(1)
            unit = info[ColunasShuffle.UNIT.getColuna()];                                                   //O(1)
            value = Float.parseFloat(info[ColunasShuffle.VALUE.getColuna()]);                               //O(1)

            flag = info[ColunasShuffle.FLAGTYPE.getColuna()].charAt(0);                               //O(1)

            saveShuffle(areaCode, codeM49, areaName, itemCode, itemCPC, itemDescription, elementCode, elementType, yearCode, year, unit, value, flag);
            //O (nLog n)
            
        }
        app.getAreaTree().fillCodeTree();                                                                   //O(n)

        // Worst-case time complexity: O((n * n )* n * log n) => O(n^3 log n)
    }


    private void saveShuffle(String areaCode, String codeM49, String areaName, String itemCode, String itemCPC, String itemDescription,
                             String elementCode, String elementType, String yearCode, int year, String unit, float value, char flag) {
        var flagStore = app.flagStore();


        //Given files had some innacuraccy
        switch (areaName) {
            case "China, Hong Kong SAR" -> areaName = "Hong Kong";
            case "China, Taiwan Province of" -> areaName = "Taiwan";
            //case "China, mainland" -> areaName = "China";
            case "China, Macao SAR" -> areaName = "Macau";
            //case "Belgium-Luxembourg" -> areaName = "Luxembourg";
        }

        Optional<Area> tmp = app.getAreaTree().getAreaByAreaName(areaName);

        // UPDATE VALUES OF areaCode e codeM49 in area
        if (tmp.isPresent()) {                                                                                  //O(n)
            Area area = tmp.get();                                                                              //O(log n)   
            area.setAreaCode(areaCode);                                                                         //O(1) 
            area.setCodeM49(codeM49);                                                                           //O(1)


            var item = new Item(itemCode, itemCPC, itemDescription);                                            //O(1)   
            var yea = new Year(yearCode, year, new Value(unit, value, flagStore.get(flag).orElseThrow()));      //O(log n)   
            var elem = new Element(elementCode, elementType);                                                   //O(1)    

            Optional<Item> iOpt = area.getItembyItem(item);                                                     //O(log n)
            if (iOpt.isEmpty())                                                                                 //O(1)     
                area.addItem(item);                                                                             //O(1) 
            else
                item = iOpt.get();                                                                              //O(log n) 

            Optional<Element> eOpt = item.getElementByElement(elem);                                            //O(log n)
            if (eOpt.isEmpty())                                                                                 //O(1)
                item.addElement(elem);                                                                          //O(1)    
            else
                elem = eOpt.get();                                                                              //O(log n)

            if (elem.getYearByYear(yea).isEmpty())                                                              //O(log n)
                elem.addYear(yea);                     
        }                                                                                  // Worst-case time complexity: O(n * log n)           
        
    }


    private File getFileFromResource(String fileName) throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("error: file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }
    }

    public File fileDirReader(String fileName) throws Exception {
        File dir = getFileFromResource(fileName);
        if (dir.isFile() && dir.canRead())
            return dir;
        throw new Exception("erro: o ficheiro nao existe");
    }

}
