package jovami.exercises;

import java.util.Optional;

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
    private static float sum;

    public Exercise5() {
        app = App.getInstance();
        areaTree = app.getKDAreaTree();
        itemStore = app.getItemTree();
    }

    @Override
    public void run() {
        
        String itemCode = "252";
        String elementCode = "5510";
        String yearCode = "1961";
        
        double latitudeInicial = -150;
        double latitudeFinal = 180;

        double longitudeInicial = -180;
        double longitudeFinal = 180;

        Optional<Item> itemDesired = itemStore.getItemByItemCode(itemCode);
        
        if(!itemDesired.isPresent()){
            return;
        }

        float sumTotal=getRangeSum(elementCode, yearCode, latitudeInicial, latitudeFinal, longitudeInicial, longitudeFinal, itemDesired);
        
        System.out.println("Soma total de valores dentro da area forncecida :"+sumTotal);
        
    }

    private float getRangeSum(String elementCode, String yearCode, double latitudeInicial, double latitudeFinal,
            double longitudeInicial, double longitudeFinal, Optional<Item> itemDesired) {

        var wrap = new Object(){
                float sumTotal=0;
        };

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
                    wrap.sumTotal += value.get();
                }
        });

        return sum;
    }
}
