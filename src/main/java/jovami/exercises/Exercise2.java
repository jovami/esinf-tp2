package jovami.exercises;

import java.util.HashMap;
import java.util.Map;

import jovami.App;
import jovami.model.Area;
import jovami.model.Element;
import jovami.model.Item;
import jovami.util.Pair;
import jovami.util.Triplet;

public class Exercise2 implements Runnable {

    private final App app;

    public Exercise2() {
        app = App.getInstance();

    }

    @Override
    public void run() {

    }

    // methods to find the area by each property


    // driver method
    private Map<Triplet<Pair<Integer, Integer>, Item, Element>, Double>
    nameTBD(final Area area, final int yearMin, final int yearMax)
    {
        // checks
        if (yearMin > yearMax) {
            throw new IllegalArgumentException(
                    "Interval lower bound was larger than the upper bound!"
            );
        }

        final int n = yearMax - yearMin + 1;

        final Pair<Integer, Integer> interval = new Pair<>(yearMin, yearMax);

        final HashMap<Triplet<Pair<Integer, Integer>, Item, Element>, Double> avgMap;

        { // reduce scope for this part
            // TODO: find a nice value for this
            final int capacity = 1 << 6;
            avgMap = new HashMap<>(capacity);
        }

        // driver code
        area.getTreeItem().forEach(item -> {
            item.getTreeElement().forEach(element -> {
                /* HACK: java doesn't allow updating variables inside lambdas
                         so we use a double[1] to work around it */
                double hack[] = new double[1];

                element.getTreeYear().forEach(year -> {
                    int y = year.getYear();
                    if (y >= yearMin && y <= yearMax)
                        hack[0] += year.getValue(); // FIX: Have each Year store a Value, not an AVL<Value>
                });
                avgMap.put(new Triplet<>(interval, item, element), hack[0] / n);
            });
        });

        return avgMap;
    }
}
