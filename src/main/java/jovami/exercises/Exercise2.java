package jovami.exercises;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.DoubleAdder;

import jovami.App;
import jovami.model.Area;
import jovami.util.Triplet;
import jovami.util.Utils;

public class Exercise2 implements Runnable {

    private final App app;

    public Exercise2() {
        app = App.getInstance();
    }

    @Override
    public void run() {
        final String areaCode = "174"; // FIXME: change to int
        final int yearMin = 1996, yearMax = 2005;
        final var list = getAreaAverages(areaCode, yearMin, yearMax);

        System.out.printf(
                "Value averages for each Item and Element in the years %d..%d\n",
                yearMin,
                yearMax
        );

        for (final var element : list)
            System.out.println(element);
    }

    // methods to find the area by each property
    public List<Triplet<String, String, Float>>
    getAreaAverages(final String areaCode, final int yearMin, final int yearMax)
    {
        final Area a = app.getAreaTree().getAreaByAreaCode(areaCode);           // O(logn)
        final var list = getAreaAverages(a, yearMin, yearMax);                  // O(n^3)   (As seen below)
        Utils.mergeSort(list,                                                   // O(nlogn) (According to official documentation)
                        Comparator.comparing(Triplet::third,
                                             Comparator.reverseOrder())
        );

        // Worst-case time complexity: O(n^3)
        return list;
    }


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
        area.getTreeItem().forEach(item -> {                                    // O(n * inside)
            item.getTreeElement().forEach(element -> {                          // O(n * inside)
                final DoubleAdder sum = new DoubleAdder();                      // O(1)

                element.getTreeYear().forEach(year -> {                         // O(n * inside)
                    final int y = year.getYear();                               // O(1)
                    final Optional<Float> valOpt = year.getValue().getValue();  // O(1)
                    if (valOpt.isPresent() && y >= yearMin && y <= yearMax)     // O(1)
                        sum.add(valOpt.get());                                  // O(1)
                });

                averages.add(new Triplet<>(item.getItemDescription(),           // O(1)
                                         element.getElementType(),
                                         sum.floatValue() / n)
                );
            });
        });

        // Worst-case time complexity: O(n * n * n) => O(n^3)
        return averages;
    }
}
