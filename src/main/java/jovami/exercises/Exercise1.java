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

public class Exercise1 implements Runnable {

    //public final String FILE_NAME = "Production_Crops_Livestock_E_AreaCoordinates_shuffled.csv";
    //public final String FILE_NAME = "Production_Crops_Livestock_EU_shuffle_small.csv";

    List<File> filenames = new LinkedList<File>();

    private final App app;

    private CSVReader csvReader;

    public Exercise1() {
        app = App.getInstance();
    }

    @Override
    public void run() {
        /*try {
            File dir = fileDirReader();
            this.csvReader = new CSVReader(CSVHeader.HEADER_AREACOORDINATES);
            saveInfoAreaCoordinates(csvReader.readCSV(dir));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /*try {
            File dir = fileDirReader();
            this.csvReader = new CSVReader(CSVHeader.HEADER_SHUFFLE);
            saveInfoShuffle(csvReader.readCSV(dir));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        readAllFiles();
        test();

    }

    private enum ColunasAreaCoordinates {
        COUNTRY(0), LATITUDE(1), LONGITUDE(2), AREANOME(3);

        private final int i;

        ColunasAreaCoordinates(int i) {this.i = i;}

        private int getColuna() {return i;}


    }

    private enum ColunasItemCodes {

        ITEMCODE(0), ITEMCPC(1), ITEMDESCRIPTION(2);

        private final int i;

        ColunasItemCodes(int i) {this.i = i;}

        private int getColuna() {return i;}

    }

    private enum ColunasFlags {
        FLAG(0), DESCRIPTION(1);

        private final int i;

        ColunasFlags(int i) {this.i = i;}

        private int getColuna() {return i;}

    }

    private enum ColunasShuffle {
        AREACODE(0), CODEM49(1), AREANAME(2), ITEMCODE(3), ITEMCPC(4), ITEMDESCRIPTION(5), ELEMENTCODE(6),
        ELEMENTTYPE(7), YEARCODE(8), YEAR(9), UNIT(10), VALUE(11), FLAG(12);

        private final int i;

        ColunasShuffle(int i) {this.i = i;}

        private int getColuna() {return i;}

    }


    public void readAllFiles()
    {
        final File folder = new File("src/main/ficheiroscsv");
        listFilesForFolder(folder);

        for(File f: filenames)
        {
            //System.out.println(""+f.getName());
            if(f.getName().contains("AreaCoordinates"))
            {

                try {
                    File dir = fileDirReader(f.getName());
                    this.csvReader = new CSVReader(CSVHeader.HEADER_AREACOORDINATES);
                    saveInfoAreaCoordinates(csvReader.readCSV(dir));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else if(f.getName().contains("ItemCodes_shuffled"))
            {

                try {
                    File dir = fileDirReader(f.getName());
                    this.csvReader = new CSVReader(CSVHeader.HEADER_ITEMCODES);
                    csvReader.readCSV(dir);
                    saveInfoItemCodes(csvReader.readCSV(dir));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else if(f.getName().contains("Production_Crops_Livestock_FR_GER_IT_PT_SP_shuffle_small"))
            {
                /*else if(f.getName().contains("shuffle_large") || f.getName().contains("shuffle_medium")
                    || f.getName().contains("shuffle_small")) */

                  try {
                    File dir = fileDirReader(f.getName());
                    this.csvReader = new CSVReader(CSVHeader.HEADER_SHUFFLE);
                    csvReader.readCSV(dir);
                    saveInfoShuffle(csvReader.readCSV(dir));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                continue;
            }

        }

    }

    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                if(fileEntry.getName().contains(".csv"))
                    filenames.add(fileEntry);
            }
        }
    }


    public void saveInfoAreaCoordinates(List<String[]> list) {
        String areaCode, codeM49, areaName, country;
        double latitude, longitude;

        for(String[] info: list)
        {
            country = info[ColunasAreaCoordinates.COUNTRY.getColuna()];

            //linha 98 do ficheiro "AreaCoordinates ,na coluna longitude tem dois numeros"
            if(info[ColunasAreaCoordinates.LATITUDE.getColuna()].matches(".*\\s.*") )
                latitude = Double.parseDouble(info[ColunasAreaCoordinates.LATITUDE.getColuna()].
                            substring(0, info[ColunasAreaCoordinates.LATITUDE.getColuna()].indexOf(' ')));

            if(info[ColunasAreaCoordinates.LONGITUDE.getColuna()].matches(".*\\s.*"))
            {
                longitude = Double.parseDouble(info[ColunasAreaCoordinates.LONGITUDE.getColuna()].
                            substring(0, info[ColunasAreaCoordinates.LONGITUDE.getColuna()].indexOf(' ')));

                latitude = Double.parseDouble(info[ColunasAreaCoordinates.LATITUDE.getColuna()]);
            }else{
                latitude = Double.parseDouble(info[ColunasAreaCoordinates.LATITUDE.getColuna()]);
                longitude = Double.parseDouble(info[ColunasAreaCoordinates.LONGITUDE.getColuna()]);
            }


            areaName = info[ColunasAreaCoordinates.AREANOME.getColuna()];

            //System.out.println(""+country + " " + latitude + " " + longitude + " " + areaName);

             saveAreaCoordinates(areaName, latitude, longitude, country);

        }

    }

    private void saveAreaCoordinates(String areaName, double latitude, double longitude, String country) {
        Area area = new Area("","",areaName,latitude,  longitude,  country);
        app.getAreaTree().addArea(area);

        /*Area x = app.getAreaTree().getAreaByAreaName(area.getAreaName());
        System.out.println("AREA NAME = " + x.getAreaName());*/

        /*Area x = app.getAreaTree().getAreaByArea(area);
        System.out.println("AREA NAME = " + x.getAreaName());*/
    }


    public void saveInfoItemCodes(List<String[]> list)
    {
        String itemCode, itemCPC, itemDescription;

        for(String[] info: list)
        {
            itemCode = info[ColunasItemCodes.ITEMCODE.getColuna()];
            itemCPC = info[ColunasItemCodes.ITEMCPC.getColuna()];
            itemDescription = info[ColunasItemCodes.ITEMDESCRIPTION.getColuna()];

            //System.out.println("Item Codes" + itemCode);
            saveItemCodes(itemCode, itemCPC, itemDescription);
        }


    }

    public void saveItemCodes(String itemCode, String itemCPC, String itemDescription)
    {
        Item item = new Item(itemCode, itemCPC, itemDescription);

        app.getItemTree().addItem(item);

        /*Item x = app.getItemTree().getItemByItem(item);
        System.out.println("ITEM" + x.getItemCPC());*/
    }


    public void saveInfoShuffle(List<String[]> list) {
        //"Area Code,Area Code (M49),Area,Item Code,Item Code (CPC),Item,Element Code,Element,Year Code,Year,Unit,Value,Flag";
        String areaCode, codeM49, areaName, itemCode, itemCPC, itemDescription, elementCode, elementType,yearCode, unit, flag;
        int year;
        float value;


        for(String[] info: list)
        {
            areaCode = info[ColunasShuffle.AREACODE.getColuna()];
            codeM49  = info[ColunasShuffle.CODEM49.getColuna()];
            areaName = info[ColunasShuffle.AREANAME.getColuna()];
            itemCode = info[ColunasShuffle.ITEMCODE.getColuna()];
            itemCPC  = info[ColunasShuffle.ITEMCPC.getColuna()];
            itemDescription = info[ColunasShuffle.ITEMDESCRIPTION.getColuna()];
            elementCode = info[ColunasShuffle.ELEMENTCODE.getColuna()];
            elementType = info[ColunasShuffle.ELEMENTTYPE.getColuna()];
            yearCode = info[ColunasShuffle.YEARCODE.getColuna()];
            year = Integer.parseInt(info[ColunasShuffle.YEAR.getColuna()]);
            unit = info[ColunasShuffle.UNIT.getColuna()];
            value = Float.parseFloat(info[ColunasShuffle.VALUE.getColuna()]);
            flag = info[ColunasShuffle.FLAG.getColuna()];

            /*System.out.println(""+areaCode + " " + codeM49 + " " + areaName + " " + itemCode
                                + " " + itemCPC + " " + itemDescription + " " + elementCode + " " + elementType
                                + " " + yearCode + " " + year + " " + unit + " " + value + " " + flag);*/


            saveShuffle(areaCode, codeM49, areaName, itemCode, itemCPC, itemDescription, elementCode, elementType, yearCode, year, unit, value, flag);

        }

    }

    public void saveShuffle(String areaCode, String codeM49, String areaName, String itemCode, String itemCPC, String itemDescription,
                                String elementCode, String elementType, String yearCode, int year, String unit, float value, String flag)
    {

        /*System.out.println(""+areaCode + " " + codeM49 + " " + areaName + " " + itemCode
        + " " + itemCPC + " " + itemDescription + " " + elementCode + " " + elementType
        + " " + yearCode + " " + year + " " + unit + " " + value + " " + flag);*/

            //UPDATE VALUES OF areaCode e codeM49 in area
            Area temp = new Area (areaCode, codeM49, areaName , 0,0,"");

            if(app.getAreaTree().exists(temp))
            {

                app.getAreaTree().getAreaByAreaName(areaName).setAreaCode(areaCode);
                app.getAreaTree().getAreaByAreaName(areaName).setCodeM49(codeM49);
                //System.out.println("xxxxxxxxx  "+app.getAreaTree().getAreaByAreaName(areaName).toString());


            }

            Item item = new Item(itemCode, itemCPC, itemDescription);



            if(app.getItemTree().exists(item))
            {

                    //System.out.println("ITEM="+item.toString());

                    app.getAreaTree().getAreaByAreaCode(areaCode).addItem(item);

                    //System.out.println("area = "+ app.getAreaTree().getAreaByAreaCode(areaCode).toString());
                    //System.out.println();

                    //System.out.println("Item code "+ itemCode);
                    //System.out.println("XXXXXXXXX " + app.getAreaTree().getAreaByAreaCode(areaCode).getItemByItemCode(itemCode));

                    //System.out.println(""+app.getAreaTree().getAreaByAreaCode(areaCode).getTreeItem().toString());
                    Element element = new Element(elementCode,elementType);
                    app.getAreaTree().getAreaByAreaCode(areaCode).getItemByItemCode(itemCode).addElement(element);

                    //System.out.println(""+app.getAreaTree().getAreaByAreaCode(areaCode).getItemByItemCode(itemCode).getTreeElement().toString());
                    Year yea = new Year(yearCode,year);
                    app.getAreaTree().getAreaByAreaCode(areaCode).getItemByItemCode(itemCode)
                        .getElementByElementCode(elementCode).addYear(yea);

                    Value val = new Value( unit,  value,  Flag.valueOf(flag));
                    app.getAreaTree().getAreaByAreaCode(areaCode).getItemByItemCode(itemCode)
                        .getElementByElementCode(elementCode).getYearByYear(yea).addValue(val);

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


    public void test()
    {
        System.out.println("---SIGA TESTAR SIGA TESTAR SIGA TESTAR---");

        Area pt = new Area("174","'620","Portugal",39.399872,-8.224454,"PT");
        Year yea1 = new Year("1981",1981);
        Year yea2 = new Year("1990",1990);

        //System.out.println(""+pt.toString());
        //System.out.println(""+ app.getAreaTree().getTree().toString());
        //System.out.println(""+ app.getItemTree().getTree().toString());
        //System.out.println(""+ app.getAreaTree().getAreaByAreaCode(pt.getAreaCode()));
        //System.out.println(""+ app.getAreaTree().getAreaByAreaCode(pt.getAreaCode()).getTreeItem().toString());

        //deveria dar duas ocorrencias
        System.out.println(""+ app.getAreaTree().getAreaByAreaCode(pt.getAreaCode()).getItemByItemCode("44").getTreeElement().toString());


        /*int count = 0;
        for(Item item: app.getAreaTree().getAreaByAreaCode("174").getTreeItem().inOrder())
        {
            System.out.println("item" + item.toString());
            count ++;
        }
        System.out.println("count="+ count);*/

        for(Item item: app.getAreaTree().getAreaByAreaCode("174").getTreeItem().inOrder())
        {
            for(Element elem: item.getTreeElement().inOrder())
                System.out.println(item.toString() + "  " + elem.toString());
        }



        for(Item item: app.getAreaTree().getAreaByAreaCode(pt.getAreaCode()).getTreeItem().inOrder())
        {

            for(Element elem : item.getTreeElement().inOrder())
            {
                for(Year ye : elem.getTreeYear().inOrder())
                {
                    if(ye.getYear() >= yea1.getYear() && ye.getYear() <= yea2.getYear())
                    {
                         for(Value val : ye.getTreeValue().inOrder())
                         {
                            //System.out.println("" + item.toString());
                            //System.out.println("" + elem.toString());
                         }
                    }
                }
            }
        }


    }

}