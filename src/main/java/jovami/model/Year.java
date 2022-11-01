package jovami.model;

import java.util.Objects;

public class Year {

    private int year;

    public Year(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Year)) return false;
        Year year1 = (Year) o;
        return year == year1.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year);
    }

    @Override
    public String toString() {
        return "Year{" +
                "year=" + year +
                '}';
    }
}
