package jovami.model.stores;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Optional;

import jovami.model.Flag;

/**
 * FlagStore
 */
public class FlagStore implements Iterable<Flag> {

    private final LinkedHashMap<Character, Flag> flags;

    public FlagStore() {
        int capacity = 2 << 4;
        this.flags = new LinkedHashMap<>(capacity);
    }

    public Optional<Flag> get(char code) {
        return Optional.ofNullable(this.flags.get(code));
    }

    public boolean add(char code, String name) {
        if (this.flags.get(code) == null)
            return false;

        forceAdd(code, name);
        return true;
    }

    public void forceAdd(char code, String name) {
        Objects.requireNonNull(name);
        this.flags.put(code, new Flag(code, name));
    }

    @Override
    public Iterator<Flag> iterator() {
        return this.flags.values().iterator();
    }

    @Override
    public String toString() {
        return "FlagStore{" +
                "flagStore=" + flags +
                '}';
    }
}
