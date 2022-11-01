package jovami.model;

public enum Flag {

    A("Official figure"),
    E("Estimated value"),
    I("Imputed value"),
    M("Missing value"),
    T("Unofficial figure");


    private final String flagName;

    Flag(String flagName)
    {
        this.flagName = flagName;
    }

    public String getFlagName() {
        return flagName;
    }
}
