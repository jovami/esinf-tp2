package jovami.exercises;

import jovami.App;
import jovami.model.Area;
import jovami.model.Element;
import jovami.model.Item;
import jovami.model.Year;
import jovami.trees.KDTree;

import java.util.List;
import java.util.Optional;


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
        final String itemCode = "156", elementCode = "5419", year = "1965";
        getAreas(itemCode, elementCode, year);

        List lista = (List) kdTree.inOrder();
        Area nearestArea = (Area) kdTree.nearestNeighbor(x, y);
    }

    public void getAreas(String itemCode, String elementCode, String yearCode){
        app.getAreaTree().getTree().forEach(area -> {
            Optional<Item> item = area.getItemByItemCode(itemCode);
            if(item.isPresent()) {
                Optional<Element> element = item.get().getElementByElementCode(elementCode);
                if(element.isPresent()){
                    Optional<Year> year = element.get().getYearByYearCode(yearCode);
                    if (year.isPresent())
                        kdTree.insert(area, area.getCoords().getLatitude(), area.getCoords().getLongitude());
                }
            }
        });
    }
}
