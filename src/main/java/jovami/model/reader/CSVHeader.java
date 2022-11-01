package jovami.model.reader;

public enum CSVHeader {
    HEADER_AREACOORDINATES(4, ",") {
        @Override
        public String toString() {
            return "country,latitude,longitude,area";
        }
    },
    HEADER_FLAGS(2 , ",") {
        @Override
        public String toString() {
            return "Flag,Description";
        }
    },
    HEADER_SHUFFLE(13 , ",") {
        @Override
        public String toString() {
            return "Area Code,Area Code (M49),Area,Item Code,Item Code (CPC),Item,Element Code,Element,Year Code,Year,Unit,Value,Flag";
        }
    };



    private final int columns;

    private final String delimiter;

    public int getColumnCount()
    {
        return this.columns;
    }

    public String getDelimiter()
    {
        return this.delimiter;
    }

    CSVHeader(int col, String delim)
    {
        this.columns = col;
        this.delimiter = delim;
    }
}
