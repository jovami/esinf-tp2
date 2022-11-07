package jovami.exercises;

import java.util.List;
import java.util.Optional;

import jovami.App;
import jovami.model.Area;
import jovami.model.Element;
import jovami.model.Item;
import jovami.model.Value;
import jovami.model.Year;
import jovami.model.stores.AreaKDTree;
import jovami.model.stores.AreaTree;
import jovami.model.stores.ItemTree;

public class Exercise5 implements Runnable {

    private final App app;
    private AreaKDTree areaTree;
    private ItemTree itemStore;
    private static float total;
    private static int numPaisesNaArea;

    public Exercise5() {
        app = App.getInstance();
        areaTree = app.getKDAreaTree();
        itemStore = app.getItemTree();
        total=0;
        numPaisesNaArea =0;
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

        areaTree.getKDtree().rangeSearch(latitudeInicial, longitudeInicial, latitudeFinal, longitudeFinal)
            .forEach(area->{
                
                numPaisesNaArea++;
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
                    System.out.printf("\nArea: %20.0s value %f",area.getAreaName(),value.get());
                    total+=(float)value.get();
                }
        });
        // n^2
        System.out.println("\nForam encontrados "+numPaisesNaArea+" paises dentro da area fornecida, com um soma total de valores de :"+total);
        
    }
}
