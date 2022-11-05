package jovami.exercises;

import jovami.App;
import jovami.model.Area;
import jovami.trees.KDTree;
import jovami.util.Triplet;

import java.util.ArrayList;
import java.util.List;


public class Exercise4 implements Runnable {

    private final App app;
    private final KDTree kdTree ;


    public Exercise4() {
        app = App.getInstance();
        this.kdTree = new KDTree();
    }

    @Override
    public void run() {
        final double x = 41.14961, y = -8.61099; //x=latitude, y=longitude
        final String itemCPC = "01315", elementCode = "5510", year = "2018";
        var matchingAreas = getAreas(itemCPC, elementCode, year);
    }

    public List getAreas(String itemCPC, String elementCode, String yearCode){
        final var matchingAreas = new ArrayList<Triplet<Area, Double, Double>>();
        app.getAreaTree().getTree().inOrder().forEach( area -> {
            if (area.getItemByItemCode(itemCPC) != null){
                if (area.getItemByItemCode(itemCPC).getElementByElementCode(elementCode) != null){
                    if(area.getItemByItemCode(itemCPC).getElementByElementCode(elementCode).getYearByYearCode(yearCode) != null){
                        matchingAreas.add(new Triplet<>(area, area.getCoords().getLatitude(), area.getCoords().getLongitude()));
                    }
                }
            }
        });
        return matchingAreas;
    }

    public void loadKDTree(List<Triplet<Area, Double, Double>> list){
        for (var element: list){
            kdTree.insert(element.first(), element.second(), element.third());
        }
    }
}
