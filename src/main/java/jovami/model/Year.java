package jovami.model;

import jovami.trees.AVL;

import java.util.Objects;

public class Year implements Comparable<Year> {

    private String yearCode;
    private int year;

    private final AVL<Value> treeValue ;

    public Year(String yearCode, int year) {

        this.yearCode = yearCode;
        this.year = year;
        //varificar o compare na class Value
        this.treeValue =  new AVL<>();
    }

    public int getYear() {
        return year;
    }

    public String getYearCode() {
        return yearCode;
    }
    //-----------------------------------------
    //-------------AVL<Value>------------------

    public AVL<Value> getTreeValue() {
        return treeValue;
    }

    public boolean addValue(Value value)
    {
        this.treeValue.insert(value);
        return true;
    }

    public Value getValueByValue(Value value)
    {
        for(Value val : treeValue.inOrder())
        {
            if(val.getValue() == value.getValue() && val.getUnit().compareToIgnoreCase(value.getUnit()) == 0
                && val.getFlag().getFlagName().compareToIgnoreCase(value.getFlag().getFlagName()) ==0 )
                return val;
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Year)) return false;
        Year year1 = (Year) o;
        return year == year1.year && yearCode.equals(year1.yearCode) && treeValue.equals(year1.treeValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(yearCode, year, treeValue);
    }

    @Override
    public String toString() {
        return "Year{" +
                "yearCode='" + yearCode + '\'' +
                ", year=" + year +
                ", treeValue=" + treeValue +
                '}';
    }

    @Override
    public int compareTo(Year o) {
        return this.yearCode.compareToIgnoreCase(o.yearCode);
    }
}
