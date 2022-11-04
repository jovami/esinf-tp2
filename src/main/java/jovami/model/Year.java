package jovami.model;

import java.util.Objects;
import java.util.Optional;

public class Year implements Comparable<Year> {

    private String yearCode;
    private int year;

    // NOTE: Optional<Value> may be better
    private Value value;

    public Year(String yearCode, int year) {
        this.yearCode = yearCode;
        this.year = year;
        this.value = null;
    }

    public int getYear() {
        return this.year;
    }

    public String getYearCode() {
        return this.yearCode;
    }

    public Optional<Value> getValue() {
        return Optional.ofNullable(this.value);
    }

    public void addValue(Value value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;

        Year year1 = (Year) o;
        return this.year == year1.year;
    }

    // NOTE: compareTo() should be consistent with equals()
    @Override
    public int compareTo(Year other) {
        return Integer.compare(this.year, other.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, yearCode);
    }

    @Override
    public String toString() {
        return "Year{" +
                "yearCode='" + yearCode + '\'' +
                ", year=" + year +
                ", value=" + value +
                '}';
    }
}
