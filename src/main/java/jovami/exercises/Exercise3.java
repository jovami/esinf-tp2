package jovami.exercises;

import jovami.App;
import jovami.model.*;
import jovami.model.stores.AreaTree;
import jovami.model.stores.ItemTree;

import jovami.util.Pair;

import java.util.*;


public class Exercise3 implements Runnable {

    private final Comparator<Pair<Area, Value>> comparator =
            Comparator.comparing(Pair::second, Comparator.reverseOrder());

    ArrayList<Pair<Area,Value>> listBiggestValues = new ArrayList<>();

    ArrayList<Pair<Area,Value>> listTopN = new ArrayList<>();
    private final App app;
    private final AreaTree areaTree;
    private final ItemTree itemTree;


    public Exercise3() {
        app = App.getInstance();
        areaTree = app.getAreaTree();
        itemTree = app.getItemTree();

    }

    @Override
    public void run() {

        String itemCode = "393";
        String elementCode = "5510";
        int topNumArea = 3;

       getTopNPairs(itemCode,elementCode,topNumArea);

        for (Pair<Area, Value> areaValuePair : listTopN) {
            System.out.printf("area: %s, Value: %.4f\n", areaValuePair.first().getAreaName(),
                    areaValuePair.second().getValue().orElse(0.0f));
        }
        System.out.println();

    }

    private void getTopNPairs(String itemCode, String elementCode, int topNumArea) {

            areaTree.getTree().forEach(area -> {
                Optional<Item> i = area.getTreeItem().find(new Item(itemCode,null, null));
                if (i.isPresent()) {
                    Optional<Element> e = i.get().getTreeElement().find(new Element(elementCode, null));
                    if (e.isPresent()) {
                        Year year = e.get().getTreeYear().biggestElement();
                        addToList(area, year.getValue());
                    }
                }
            });
            sortListDescending(listBiggestValues);

            addTopNValuesToList(topNumArea);


    }

    private void addTopNValuesToList(int topNumArea) {
        for (int i = 0; i < topNumArea; i++) {
            listTopN.add(listBiggestValues.get(i));
        }
    }

    private void sortListDescending(List<Pair<Area, Value>> list) {
        list.sort(comparator);
    }


    private void addToList(Area area, Value value){
        listBiggestValues.add(new Pair<>(area, value));

    }



}
