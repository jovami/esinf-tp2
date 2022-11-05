package jovami.exercises;

import jovami.App;
import jovami.model.*;
import jovami.model.stores.AreaTree;
import jovami.model.stores.ItemTree;

import jovami.util.ListPrinter;
import jovami.util.Pair;

import java.util.*;


public class Exercise3 implements Runnable {

    private final Comparator<Pair<Area, Value>> comparator =
            (o1, o2) -> (int) Value.compare(o2.second(), o1.second());

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
       printTopNValues(listTopN);

    }

    private void getTopNPairs(String itemCode, String elementCode, int topNumArea) {
      //  try{
            areaTree.getTree().forEach(area -> {
                Optional<Item> i = area.getTreeItem().find(itemTree.getItemByItemCode(itemCode));
                if (i.isPresent()) {
                    i.get().getTreeElement();
                    itemTree.getTree().forEach(item -> {
                        Optional<Element> e = item.getTreeElement().find(item.getElementByElementCode(elementCode));
>>>>>>> feat(ex3): empty verification
                        if (e.isPresent()){
                            Year year =  e.get().getTreeYear().biggestElement();
                            addToList(area, year.getValue());
                        }
                    });
                }
            });
            sortListDescending(listBiggestValues);

            addTopNValuesToList(topNumArea);


       // }catch (IndexOutOfBoundsException exception){
         //   System.out.println("Area Tree is Empty");
        //}



    }

    private void addTopNValuesToList(int topNumArea) {
        for (int i = 0; i < topNumArea; i++) {
            listTopN.add(listBiggestValues.get(i));
        }
    }

    private void sortListDescending(List<Pair<Area, Value>> list) {
        list.sort(comparator);
    }

    private <E> void printTopNValues(List<E> listTopN) {
        String header = "\nArea --- Value\n";
        ListPrinter.print(listTopN, header, null);
    }

    private void addToList(Area area, Value value){
        listBiggestValues.add(new Pair<>(area, value));

    }



}
