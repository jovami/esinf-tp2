package jovami.model;

import java.util.Objects;

public class Value {

    private float value;
    private String unit;
    private Flag flag;


    public Value(float value, String unit, Flag flag) {
        this.value = value;
        this.unit = unit;
        this.flag = flag;
    }

    public float getValue() {
        return value;
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
        if (!(o instanceof Value)) return false;
        Value value1 = (Value) o;
        return Float.compare(value1.value, value) == 0 && unit.equals(value1.unit) && flag == value1.flag;
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
}
