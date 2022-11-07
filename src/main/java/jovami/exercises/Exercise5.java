package jovami.exercises;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;

import jovami.App;
import jovami.model.Element;
import jovami.model.Item;
import jovami.model.Year;
import jovami.model.stores.AreaKDTree;
import jovami.model.stores.ItemTree;

public class Exercise5 implements Runnable {

    private final App app;
    private AreaKDTree areaTree;
    private ItemTree itemStore;

    public Exercise5() {
        app = App.getInstance();
        areaTree = app.getKDAreaTree();
        itemStore = app.getItemTree();
    }

    @Override
    public void run() {
        
        String itemCode = "1058";
        String elementCode = "5510";
        String yearCode = "2018";
        
        double latitudeInicial = -30;
        double latitudeFinal = 30;

        double longitudeInicial = 10;
        double longitudeFinal = 40;

        Optional<Item> itemDesired = itemStore.getItemByItemCode(itemCode);
        
        if(!itemDesired.isPresent()){
            return;
        }
        double sumTotal=getRangeSum(elementCode, yearCode, latitudeInicial, latitudeFinal, longitudeInicial, longitudeFinal, itemDesired);
        

        System.out.printf("Item Code: %s\nElement Code: %s\nYear Code:%s\n",itemCode,elementCode,yearCode );
        System.out.printf("Latitude inicial: %f\nLatitude final: %f\n---------------\nLongitude inicial: %f\nLongitude final: %f\n\n",
            latitudeInicial,latitudeFinal,longitudeInicial,longitudeFinal);
        System.out.println("Soma total de valores dentro da area forncecida :"+sumTotal);
        
    }

    private double getRangeSum(String elementCode, String yearCode, double latitudeInicial, double latitudeFinal,
            double longitudeInicial, double longitudeFinal, Optional<Item> itemDesired) {

        DoubleAdder sum = new DoubleAdder();
        

        if(longitudeFinal<longitudeInicial){
            double backup = longitudeFinal;
            longitudeFinal = longitudeInicial;
            longitudeInicial = backup;
        }
        if(latitudeFinal<latitudeInicial){
            double backup = latitudeFinal;
            latitudeFinal = latitudeInicial;
            latitudeInicial = backup;
        }


        areaTree.getKDtree().rangeSearch(latitudeInicial, longitudeInicial, latitudeFinal, longitudeFinal)
            .forEach(area->{
                Optional<Item> item= area.getTreeItem().find(itemDesired.get());
                if(!item.isPresent()){
                    return;
                }

                Optional<Element> element= item.get().getElementByElementCode(elementCode);
                if(!element.isPresent()){
                    return;
                }
                Optional<Year> year = element.get().getYearByYearCode(yearCode);
                if(!year.isPresent()){
                    return;
                }
                
                Optional<Float> value = year.get().getValue().getValue();
                if(value.isPresent()){
                    sum.add(value.get());
                }
        });
        return sum.doubleValue();
    }
}
