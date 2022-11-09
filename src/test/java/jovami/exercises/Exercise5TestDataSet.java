package jovami.exercises;

public class Exercise5TestDataSet {

    static final String flagsFile[] = new String[] {
        // "Flag,Description",
        "A,Official figure",
        "E,Estimated value",
        "I,Imputed value",
        "M,Missing value (data cannot exist; not applicable)",
        "T,Unofficial figure",
    };

    static final String itemFile[] = new String[]{
        
        "1058,'21121,Meat of chickens; fresh or chilled",
        "882,'02211,Raw milk of cattle",
        "1765,'F1765,Meat"

    };

    static final String coordsFile[] = new String[] {
        // "country,latitude,longitude,area",
        "UG,1.373333,32.290275,Uganda",
        "BW,-22.328474,24.684866,Botswana",
        "RW,-1.940278,29.873888,Rwanda",
        "TD,15.454166,18.732207,Chad",
        "AN,12.226079,-69.060087,Netherlands Antilles",
        "MD,47.411631,28.369885,Republic of Moldova",
        "XK,42.602636,20.902977,Kosovo",
        "SJ,77.553604,23.670272,Svalbard and Jan Mayen"
    };

    static final String shuffleFile[] = new String[] {
        // "Area Code,Area Code (M49),Area,Item Code,Item Code (CPC),Item,Element Code,Element,Year Code,Year,Unit,Value,Flag",
        "\"226\",\"'800\",\"Uganda\",\"1058\",\"'21121\",\"Meat of chickens, fresh or chilled\",\"5510\",\"Production\",\"2018\",\"2018\",\"tonnes\",\"67001.000000\",\"I\"",
        "\"20\",\"'072\",\"Botswana\",\"1058\",\"'21121\",\"Meat of chickens, fresh or chilled\",\"5510\",\"Production\",\"2018\",\"2018\",\"tonnes\",\"3873.000000\",\"E\"",
        "\"184\",\"'646\",\"Rwanda\",\"1058\",\"'21121\",\"Meat of chickens, fresh or chilled\",\"5510\",\"Production\",\"2018\",\"2018\",\"tonnes\",\"18726.000000\",\"I\"",
        "\"250\",\"'180\",\"Democratic Republic of the Congo\",\"1058\",\"'21121\",\"Meat of chickens, fresh or chilled\",\"5424\",\"Yield/Carcass Weight\",\"1990\",\"1990\",\"0.1g/An\",\"6522.000000\",\"I\"",
        "\"39\",\"'148\",\"Chad\",\"1058\",\"'21121\",\"Meat of chickens, fresh or chilled\",\"5510\",\"Production\",\"2018\",\"2018\",\"tonnes\",\"6522.000000\",\"I\"",
        "\"226\",\"'800\",\"Uganda\",\"882\",\"'02211\",\"Raw milk of cattle\",\"5318\",\"Milk Animals\",\"1993\",\"1993\",\"Head\",\"1343000.000000\",\"E\"",
        "\"146\",\"'498\",\"Republic of Moldova\",\"1765\",\"'F1765\",\"Meat, Total\",\"5510\",\"Production\",\"2008\",\"2008\",,,,"

        };
}
