package jovami.exercises;

import jovami.App;
import jovami.model.*;
import jovami.model.stores.AreaTree;

import jovami.util.Pair;

import java.util.*;


public class Exercise3 implements Runnable {

    protected final Comparator<Pair<Area, Value>> comparator =
            Comparator.comparing(Pair::second, Comparator.reverseOrder());

    ArrayList<Pair<Area, Value>> listBiggestValues = new ArrayList<>();

    ArrayList<Pair<Area, Value>> listTopN = new ArrayList<>();
    protected final AreaTree areaTree;


    public Exercise3() {
        App app = App.getInstance();
        areaTree = app.getAreaTree();
    }

    @Override
    public void run() {

        String itemCode = "393";
        String elementCode = "5510";
        int topNumArea = 10;

        getListLastYearValues(itemCode, elementCode);

        sortListDescending(listBiggestValues);
        addTopNValuesToList(topNumArea);


        for (Pair<Area, Value> areaValuePair : listTopN) {
            System.out.printf("Area: %-25s, Value: %.4f %s\n", areaValuePair.first().getAreaName(),
                    areaValuePair.second().getValue().orElse(0.0f), areaValuePair.second().getUnit());
        }
        System.out.println();

    }

    /**
     * @param itemCode
     * @param elementCode
     */
    protected void getListLastYearValues(String itemCode, String elementCode) {

        areaTree.getNameTree().forEach(area -> {
            Optional<Item> i = area.getItemByItemCode(itemCode);
            if (i.isPresent()) {
                Optional<Element> e = i.get().getElementByElementCode(elementCode);
                if (e.isPresent()) {
                    Year year = e.get().getTreeYear().biggestElement();
                    addToList(area, year.getValue());
                }
            }
        });
    }

    /**
     * @param topNumArea
     */
    protected void addTopNValuesToList(int topNumArea) {
        for (int i = 0; i < topNumArea; i++) {
            listTopN.add(listBiggestValues.get(i));
        }
    }

    /**
     * @param list
     */
    protected void sortListDescending(List<Pair<Area, Value>> list) {
        list.sort(comparator);
    }


    /**
     * @param area
     * @param value
     */
    protected void addToList(Area area, Value value) {
        listBiggestValues.add(new Pair<>(area, value));

    }


}
