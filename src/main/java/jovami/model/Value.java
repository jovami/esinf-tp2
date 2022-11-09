package jovami.model;

import java.util.Objects;
import java.util.Optional;

public class Value implements Comparable<Value>{

    private final Float value;
    private final String unit;
    private final Flag flag;

    public Value(String unit, Float value, Flag flag) {
        this.value = value;
        this.unit = unit;
        this.flag = flag;
    }


    public Optional<Float> getValue() {
        return Optional.ofNullable(value);
    }

    public String getUnit() {
        return unit;
    }

    public Flag getFlag() {
        return flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Value value1)) return false;
        return value.equals(value1.value)
            && unit.equals(value1.unit)
            && flag.equals(value1.flag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit, flag);
    }

    @Override
    public String toString() {
        return "Value{" +
                "value=" + value +
                ", unit='" + unit + '\'' +
                ", flag=" + flag +
                '}';
    }

    @Override
    public int compareTo(Value o) {
        return this.value.compareTo(o.value);
    }
}
