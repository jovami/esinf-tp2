package jovami.exercises;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
        String areaCode = "174"; // FIXME: change to int
        final int yearMin = 1996, yearMax = 2005;
        var list = getAreaAverages(areaCode, yearMin, yearMax);

        System.out.printf(
                "Value averages for each Item and Element in the years %d..%d\n",
                yearMin,
                yearMax
        );

        for (var element : list)
            System.out.println(element);
    }

    // methods to find the area by each property
    public List<Triplet<String, String, Float>>
    getAreaAverages(final String areaCode, final int yearMin, final int yearMax)
    {
        Area a = app.getAreaTree().getAreaByAreaCode(areaCode);
        var list = getAreaAverages(a, yearMin, yearMax);
        Utils.mergeSort(list, Comparator.comparing(Triplet::third, Comparator.reverseOrder()));
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
        final var avgMap = new ArrayList<Triplet<String, String, Float>>();

        // driver code
        area.getTreeItem().forEach(item -> {
            item.getTreeElement().forEach(element -> {
                /* HACK: java doesn't allow updating variables inside lambdas
                         so we use a float[1] to work around it */
                float hack[] = new float[1];

                element.getTreeYear().forEach(year -> {
                    int y = year.getYear();
                    Optional<Float> valOpt = year.getValue().getValue();
                    if (valOpt.isPresent() && y >= yearMin && y <= yearMax)
                        hack[0] += valOpt.get();
                });

                avgMap.add(new Triplet<>(item.getItemDescription(),
                                         element.getElementType(),
                                         hack[0] / n)
                );
            });
        });

        return avgMap;
    }
}
