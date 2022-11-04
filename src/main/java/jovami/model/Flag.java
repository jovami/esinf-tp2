package jovami.model;

import java.util.Objects;

import jovami.util.Pair;

/**
 * Flag
 */
public class Flag {

    private final Pair<Character, String> pair;

    public Flag(char code, String name) {
        this.pair = new Pair<>(code, name);
    }

    public char code() {
        return this.pair.first();
    }

    public String name() {
        return this.pair.second();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        else if (o == null || o.getClass() != this.getClass())
            return false;
        Flag f = (Flag) o;

        return this.pair.equals(f.pair);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pair);
    }

    @Override
    public String toString() {
        return this.pair.toString();
    }
}
