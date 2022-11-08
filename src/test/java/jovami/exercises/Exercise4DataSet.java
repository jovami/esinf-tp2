package jovami.exercises;

public class Exercise4DataSet {

    static final String[] flagsFile = new String[]{
            "A,Official figure",
            "E,Estimated value",
            "I,Imputed value",
            "M,Missing value (data cannot exist; not applicable)",
            "T,Unofficial figure",
    };

    static final String coordsFile[] = new String[]{
            "DZ,28.033886,1.659626,Algeria",
            "BE,50.503887,4.469936,Belgium",
            "FR,46.227638,2.213749,France",
            "DE,51.165691,10.451526,Germany",
            "HR,45.1,15.2,Croatia",
            "SV,13.794185,-88.89653,El Salvador",
            "IR,32.427908,53.688046,Iran (Islamic Republic of)",
            "SN,14.497401,-14.452362,Senegal",
            "YSFR,36.944975,-4.409041,Yugoslav SFR",
            "MX,23.634501,-102.552784,Mexico",
            "BR,-14.235004,-51.92528,Brazil",
            "JP,36.204824,138.252924,Japan",
    };

    static final String itemFile[] = new String[]{
            "393","'01213","Cauliflowers and broccoli",
            "782","'01922.02","Kenaf, and other textile bast fibres, raw or retted",
            "234","'01379.90","Other nuts (excluding wild edible nuts and groundnuts), in shell, n.e.c.",
            "249","'01460","Coconuts, in shell",
            "544","'01354","Strawberries",
            "373","'01215","Spinach",
            "661","'01640","Cocoa beans",
            "149","'01599.10","Edible roots and tubers with high starch or inulin content, n.e.c., fresh"
    };


    static final String shuffleFile[] = new String[]{
            "\"4\",\"'012\",\"Algeria\",\"393\",\"'01213\",\"Cauliflowers and broccoli\",\"5510\",\"Production\",\"1992\",\"1992\",\"tonnes\",\"38110.000000\",\"A\"",
            "\"4\",\"'012\",\"Algeria\",\"393\",\"'01213\",\"Cauliflowers and broccoli\",\"5419\",\"Yield\",\"1988\",\"1988\",\"hg/ha\",\"90330.000000\",\"E\"",
            "\"4\",\"'012\",\"Algeria\",\"393\",\"'01213\",\"Cauliflowers and broccoli\",\"5510\",\"Production\",\"1978\",\"1978\",\"tonnes\",\"10000.000000\",\"E\"",
            "\"255\",\"'056\",\"Belgium\",\"393\",\"'01213\",\"Cauliflowers and broccoli\",\"5510\",\"Production\",\"2016\",\"1992\",\"tonnes\",\"112706.000000\",\"A\"",
            "\"68\",\"'250\",\"France\",\"393\",\"'01213\",\"Cauliflowers and broccoli\",\"5510\",\"Production\",\"1994\",\"1994\",\"tonnes\",\"531844.000000\",\"A\"",
            "\"79\",\"'276\",\"Germany\",\"393\",\"'01213\",\"Cauliflowers and broccoli\",\"5510\",\"Production\",\"2014\",\"1992\",\"tonnes\",\"149164.000000\",\"A\"",
            "\"98\",\"'191\",\"Croatia\",\"393\",\"'01213\",\"Cauliflowers and broccoli\",\"5312\",\"Area harvested\",\"2003\",\"1992\",\"ha\",\"85.000000\",\"A\"",
            "\"60\",\"'222\",\"El Salvador\",\"782\",\"'01922.02\",\"Kenaf, and other textile bast fibres, raw or retted\",\"5510\",\"Production\",\"2000\",\"2000\",\"tonnes\",\"2800.000000\",\"A\"",
            "\"102\",\"'364\",\"Iran (Islamic Republic of)\",\"234\",\"'01379.90\",\"Other nuts(excluding wild edible nuts and groundnuts), in shell, n.e.c.\",\"5510\",\"Production\",\"1965\",\"1965\",\"tonnes\",\"250.000000\",\"E\"",
            "\"195\",\"'686\",\"Senegal\",\"249\",\"'01460\",\"Coconuts, in shell\",\"5312\",\"Area harvested\",\"1999\",\"1999\",\"ha\",\"1346.000000\",\"I\"",
            "\"248\",\"'890\",\"Yugoslav SFR\",\"544\",\"'01354\",\"Strawberries\",\"5510\",\"Production\",\"1986\",\"1986\",\"tonnes\",\"27441.000000\",\"T\"",
            "\"138\",\"'484\",\"Mexico\",\"373\",\"'01215\",\"Spinach\",\"5419\",\"Yield\",\"1998\",\"1998\",\"hg / ha\",\"117291.000000\",\"E\"",
            "\"21\",\"'076\",\"Brazil\",\"661\",\"'01640\",\"Cocoa beans\",\"5419\",\"Yield\",\"2009\",\"2009\",\"hg / ha\",\"3435.000000\",\"E\"",
            "\"110\",\"'392\",\"Japan\",\"149\",\"'01599.10\",\"Edible roots and tubers with high starch or inulin content, n.e.c., fresh\",\"5419\",\"Yield\",\"1979\",\"1979\",\"hg / ha\",\"162417.000000\",\"E\"",

    };


}