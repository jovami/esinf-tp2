package jovami.exercises;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.DoubleAdder;

import jovami.App;
import jovami.model.Area;
import jovami.util.Triplet;
import jovami.util.Utils;

public class Exercise2 implements Runnable {

    // Descending order of value
    protected final Comparator<Triplet<String, String, Float>> cmp =
        Comparator.comparing(Triplet::third, Comparator.reverseOrder());

    private final App app;

    public Exercise2() {
        app = App.getInstance();
    }

    @Override
    public void run() {
        final String areaCode = "174"; // FIXME: change to int
        final int yearMin = 1996, yearMax = 2005;

        final var list = getAreaAverages(areaCode, yearMin, yearMax);

        // O(n * log n), as per the SDK19 documentation
        Utils.mergeSort(list,
                        Comparator.comparing(Triplet::third,
                                             Comparator.reverseOrder())
        );

        System.out.printf(
                "Value averages for each Item and Element in the years %d..%d\n",
                yearMin,
                yearMax
        );

        for (final var element : list) {
            System.out.printf("Item: \"%s\", Element: \"%s\" ==> Average: %.2f\n",
                              element.first(),
                              element.second(),
                              element.third()
            );
        }
    }

    // methods to find the area by each property

    // FIXME: change areaCode to be an integer
    public List<Triplet<String, String, Float>>
    getAreaAverages(final String areaCode, final int yearMin, final int yearMax)
    {
        // O(log n)
        final Optional<Area> opt = app.getAreaTree().getAreaByAreaCode(areaCode);

        if (opt.isEmpty())
            throw new NoSuchElementException("The requested area does not exist");

        // O(n^3)   (As seen below)
        return getAreaAverages(opt.get(), yearMin, yearMax); // Worst-case time complexity: O(n^3)
    }

    /* public List<Triplet<String, String, Float>>
    getAreaAverages(final String areaName, final int yearMin, final int yearMax)
    {
        // O(log n)
        final Optional<Area> opt = app.getAreaTree().getAreaByAreaName(areaName);

        if (opt.isEmpty())
            throw new NoSuchElementException("The requested area does not exist");

        // O(n^3)   (As seen below)
        return getAreaAverages(opt.get(), yearMin, yearMax); // Worst-case time complexity: O(n^3)
    } */


    /****************************************************************************/


    // driver method
    private List<Triplet<String, String, Float>>
    getAreaAverages(final Area area, final int yearMin, final int yearMax)
    {
        // checks
        if (yearMin > yearMax) {
            throw new IllegalArgumentException(
                    "Interval lower bound was larger than the upper bound!"
            );
        }

        final int n = yearMax - yearMin + 1;
        final var averages = new ArrayList<Triplet<String, String, Float>>();

        // driver code
        area.getTreeCode().forEach(item -> {                                    // O(n * inside)
            item.getTreeCode().forEach(element -> {                             // O(n * inside)
                final DoubleAdder sum = new DoubleAdder();                      // O(1)

                element.getTreeYear().forEach(year -> {                         // O(n * inside)
                    final int y = year.getYear();                               // O(1)
                    final Optional<Float> valOpt = year.getValue().getValue();  // O(1)
                    if (valOpt.isPresent() && y >= yearMin && y <= yearMax)     // O(1)
                        sum.add(valOpt.get());                                  // O(1)
                });

                Float f = sum.floatValue();                                     // O(1)
                if (f.compareTo(0.0F) > 0)                                      // O(1)
                    averages.add(                                               // O(1)
                        new Triplet<>(item.getItemDescription(),
                                      element.getElementType(),
                                      f / n)
                    );
            });
        });

        // Worst-case time complexity: O(n * n * n) => O(n^3)
        return averages;
    }
}
