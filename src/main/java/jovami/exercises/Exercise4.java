package jovami.exercises;

import jovami.App;

import jovami.model.*;

import jovami.trees.KDTree;
import java.util.Optional;

public class Exercise4 implements Runnable {

    private final App app;


    public Exercise4() {
        app = App.getInstance();
    }

    @Override
    public void run() {
        final double x = 41.14961, y = -8.61099; //x=latitude, y=longitude
        final String itemCode = "569", elementCode = "5510", year = "2018";

        Area nearestArea = getNearestArea(getAreas(itemCode, elementCode, year), x, y);

        System.out.println("Inserted details:\n=======================" +
                "\nLatitude -> " + x + "\nLongitude -> " + y +
                "\nItem Code -> " + itemCode +
                "\nElement Code -> " + elementCode +
                "\nYear -> " + year + "\n");

        System.out.println("Nearest area: " +
                nearestArea.getAreaName()+
                "\n======================="+
                "\nLatitude -> " + nearestArea.getCoords().getLatitude() +
                "\nLongitude -> " + nearestArea.getCoords().getLongitude() + "\n");

        printNearestNeighborDetails(nearestArea);
    }

    public KDTree<Area> getAreas(String itemCode, String elementCode, String yearCode){
        KDTree<Area> kdTree = new KDTree<>();
        app.getAreaTree().getNameTree().forEach(area -> {
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
        return kdTree;
    }

    public Area getNearestArea(KDTree <Area> tree, double x, double y){
        return tree.nearestNeighbor(x, y);
    }

    private void printNearestNeighborDetails(Area area){
        System.out.println("Details:\n-----------------------");
        for (Item item : area.getTreeCode().inOrder()){
            System.out.println("Item: " + item.getItemDescription() + " (" + item.getItemCode() + ")\n");
            for (Element element : item.getTreeCode().inOrder()){
                for (Year year : element.getTreeYear().inOrder()){
                    System.out.println("-> " + element.getElementType() + " (" + element.getElementCode() +")" + ", in " + year.getYear());
                }
            }
            System.out.println("-----------------------");
        }
    }
}
