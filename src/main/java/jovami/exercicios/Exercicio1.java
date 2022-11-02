package jovami.exercicios;

import jovami.App;
import jovami.model.reader.CSVReader;

public class Exercicio1 implements Runnable{

    //public final String FILE_NAME = "FAOSTAT_data_en_9-7-2022_BIG.csv";

    private final App app;

   // private final CSVReader csvReader;

    public Exercicio1() {
        app = App.getInstance();
        //this.csvReader = new CSVReader();
    }

    @Override
    public void run() {
        /*try {
            File dir = fileDirReader();
            saveInfo(csvReader.readCSV(dir));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }




}
