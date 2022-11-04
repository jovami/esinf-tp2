package jovami.model.stores;

import jovami.model.Flag;

import java.util.HashMap;

public class FlagStore {

    private final HashMap<String,String> flagStore;

    public FlagStore() {this.flagStore = new HashMap<>();}

    public HashMap<String, String> getFlagStore() {return flagStore;}

    public boolean addFlag(Flag flag)
    {
        //flagStore.put(flag.getFlagType(),flag.getFlagDescription());
        return true;
    }

    public String getFlagDescription (String flagType)
    {
        return this.flagStore.get(flagType);
    }

    public Flag getFlagByFlag(Flag flag)
    {
        /*if(flagStore.containsKey(flag.getFlagType()))
            return flag;
        else
            return null;*/

        return null;
    }

    @Override
    public String toString() {
        return "FlagStore{" +
                "flagStore=" + flagStore +
                '}';
    }
}
