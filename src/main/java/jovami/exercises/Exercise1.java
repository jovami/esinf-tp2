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
        test();
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

        var store = app.flagStore();

        for (String[] info : list) {
            code = info[ColunasFlags.FLAGCODE.getColuna()].charAt(0);
            name = info[ColunasFlags.DESCRIPTION.getColuna()];
            store.add(code, name);

        }
    }

    public void saveInfoAreaCoordinates(List<String[]> list) {
        // String areaCode, codeM49;
        String areaName, country;
        double latitude, longitude;

        for (String[] info : list) {
            country = info[ColunasAreaCoordinates.COUNTRY.getColuna()];

            //linha 98 do ficheiro "AreaCoordinates ,na coluna longitude tem dois numeros"
            if (info[ColunasAreaCoordinates.LATITUDE.getColuna()].matches(".*\\s.*"))
                latitude = Double.parseDouble(info[ColunasAreaCoordinates.LATITUDE.getColuna()].
                        substring(0, info[ColunasAreaCoordinates.LATITUDE.getColuna()].indexOf(' ')));

            if (info[ColunasAreaCoordinates.LONGITUDE.getColuna()].matches(".*\\s.*")) {
                longitude = Double.parseDouble(info[ColunasAreaCoordinates.LONGITUDE.getColuna()].
                        substring(0, info[ColunasAreaCoordinates.LONGITUDE.getColuna()].indexOf(' ')));

                latitude = Double.parseDouble(info[ColunasAreaCoordinates.LATITUDE.getColuna()]);
            } else {
                latitude = Double.parseDouble(info[ColunasAreaCoordinates.LATITUDE.getColuna()]);
                longitude = Double.parseDouble(info[ColunasAreaCoordinates.LONGITUDE.getColuna()]);
            }

            areaName = info[ColunasAreaCoordinates.AREANOME.getColuna()];


            saveAreaCoordinates(areaName, latitude, longitude, country);

        }

    }


    private void saveAreaCoordinates(String areaName, double latitude, double longitude, String country) {
        Area area = new Area("", "", areaName, latitude, longitude, country);
        app.getAreaTree().addArea(area);
        app.getKDAreaTree().addNode(area, latitude, longitude);
    }


    public void saveInfoItemCodes(List<String[]> list) {
        String itemCode, itemCPC, itemDescription;

        for (String[] info : list) {
            itemCode = info[ColunasItemCodes.ITEMCODE.getColuna()];
            itemCPC = info[ColunasItemCodes.ITEMCPC.getColuna()];
            itemDescription = info[ColunasItemCodes.ITEMDESCRIPTION.getColuna()];

            saveItemCodes(itemCode, itemCPC, itemDescription);

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


        for (String[] info : list) {
            areaCode = info[ColunasShuffle.AREACODE.getColuna()];
            codeM49 = info[ColunasShuffle.CODEM49.getColuna()];
            areaName = info[ColunasShuffle.AREANAME.getColuna()];
            itemCode = info[ColunasShuffle.ITEMCODE.getColuna()];
            itemCPC = info[ColunasShuffle.ITEMCPC.getColuna()];
            itemDescription = info[ColunasShuffle.ITEMDESCRIPTION.getColuna()];
            elementCode = info[ColunasShuffle.ELEMENTCODE.getColuna()];
            elementType = info[ColunasShuffle.ELEMENTTYPE.getColuna()];
            yearCode = info[ColunasShuffle.YEARCODE.getColuna()];
            year = Integer.parseInt(info[ColunasShuffle.YEAR.getColuna()]);
            unit = info[ColunasShuffle.UNIT.getColuna()];
            value = Float.parseFloat(info[ColunasShuffle.VALUE.getColuna()]);

            flag = info[ColunasShuffle.FLAGTYPE.getColuna()].charAt(0);

            saveShuffle(areaCode, codeM49, areaName, itemCode, itemCPC, itemDescription, elementCode, elementType, yearCode, year, unit, value, flag);
        }
    }


    private void saveShuffle(String areaCode, String codeM49, String areaName, String itemCode, String itemCPC, String itemDescription,
                             String elementCode, String elementType, String yearCode, int year, String unit, float value, char flag) {
        var flagStore = app.flagStore();

        switch (areaName) {
            case "China, Hong Kong SAR" -> areaName = "Hong Kong";
            case "China, Taiwan Province of" -> areaName = "Taiwan";
            //case "China, mainland" -> areaName = "China";
            case "China, Macao SAR" -> areaName = "Macau";
            //case "Belgium-Luxembourg" -> areaName = "Luxembourg";
        }

        Optional<Area> tmp = app.getAreaTree().getAreaByAreaName(areaName);

        // UPDATE VALUES OF areaCode e codeM49 in area
        if (tmp.isPresent()) {
            Area area = tmp.get();
            area.setAreaCode(areaCode);
            area.setCodeM49(codeM49);


            var item = new Item(itemCode, itemCPC, itemDescription);
            var yea = new Year(yearCode, year, new Value(unit, value, flagStore.get(flag).orElseThrow()));
            var elem = new Element(elementCode, elementType);

            Optional<Item> iOpt = area.getItembyItem(item);
            if (iOpt.isEmpty())
                area.addItem(item);
            else
                item = iOpt.get();

            Optional<Element> eOpt = item.getElementByElement(elem);
            if (eOpt.isEmpty())
                item.addElement(elem);
            else
                elem = eOpt.get();

            if (elem.getYearByYear(yea).isEmpty())
                elem.addYear(yea);
        }
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


    public void test() {
        System.out.println("---SIGA TESTAR SIGA TESTAR SIGA TESTAR---");

        Area pt = new Area("174", "'620", "Portugal", 39.399872, -8.224454, "PT");
        Year yea1 = new Year("1981", 1981);
        Year yea2 = new Year("1990", 1990);

        int count = 0;

        var tmp = app.getAreaTree().getAreaByAreaCode("174");

        if (tmp.isPresent()) {
            var aTmp = tmp.get();

            for (Item item : aTmp.getTreeItem().inOrder()) {
                System.out.println("" + item.toString());
                for (Element elem : item.getTreeElement().inOrder()) {
                    //System.out.println(""+ elem.toString());
                    //System.out.println(item.toString() + "  " + elem.toString());
                    count++;
                }
            }
            System.out.println("count=" + count);
        }

         /*for(Area ar: app.getAreaTree().getTree().inOrder())
        {

            System.out.println("AreaCode= " + ar.getAreaCode() + " CodeM49=" + ar.getCodeM49()
                                + " AreaName= " + ar.getAreaName() + " Latitude= " + ar.getCoords().getLatitude()
                                + " Longitude= " + ar.getCoords().getLongitude() + " Country= " +
                                ar.getCountry());
        }*/


    }
}
