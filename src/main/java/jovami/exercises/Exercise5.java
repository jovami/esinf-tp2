package jovami.exercises;

import java.util.Optional;
import java.util.concurrent.atomic.DoubleAdder;

import jovami.App;
import jovami.model.Element;
import jovami.model.Item;
import jovami.model.Year;
import jovami.model.stores.AreaKDTree;
import jovami.model.stores.ItemTree;

public class Exercise5 implements Runnable {
    //Exercise5 Worst case time complexity: O(nLog n)


    private final AreaKDTree areaTree;
    private final ItemTree itemStore;

    public Exercise5() {
        App app = App.getInstance();
        areaTree = app.getKDAreaTree();
        itemStore = app.getItemTree();
    }

    @Override
    public void run() {

        String itemCode = "1058";   // 0(1)
        String elementCode = "5510";   // 0(1)
        String yearCode = "2018";   // 0(1)

        double latitudeInicial = -30;   // 0(1)
        double latitudeFinal = 30;   // 0(1)

        double longitudeInicial = 10;   // 0(1)
        double longitudeFinal = 40;   // 0(1)

        Optional<Item> itemDesired = itemStore.getItemByItemCode(itemCode);     //O(Log n)

        if(itemDesired.isEmpty()){   // 0(1)
            return;   // 0(1)
        }



        double sumTotal=getRangeSum(elementCode, yearCode, latitudeInicial, latitudeFinal, longitudeInicial, longitudeFinal, itemDesired.get());
        //O(nLog(n))

        System.out.printf("Item Code: %s\nElement Code: %s\nYear Code: %s\n",itemCode,elementCode,yearCode );
        System.out.printf("\nCoordenadas:\nLatitude inicial: %.2f\nLatitude final: %.2f\n---------------\nLongitude inicial: %.2f\nLongitude final: %.2f\n\n",
            latitudeInicial,latitudeFinal,longitudeInicial,longitudeFinal);
        System.out.println("Soma total de valores dentro da area forncecida: "+sumTotal);

    }

    protected double getRangeSum(String elementCode, String yearCode, double latitudeInicial, double latitudeFinal,
            double longitudeInicial, double longitudeFinal, Item itemDesired) {

        DoubleAdder sum = new DoubleAdder();
        
        //rangeSearch worst case time complexity: O(n)
        areaTree.getKDtree().rangeSearch(latitudeInicial, longitudeInicial, latitudeFinal, longitudeFinal)      
            .forEach(area->{                                                                                //O (n*inside) , inside:O(log n)
                Optional<Item> item= area.getTreeCode().find(itemDesired);                                  //O (Logn)

                if(item.isEmpty()){                                                                         //O (1)
                    return;                                                                                 //O (1)
                }

                Optional<Element> element= item.get().getElementByElementCode(elementCode);                 //O (Logn)
                if(element.isEmpty()){                                                                      //O (1)
                    return;                                                                                 //O (1)
                }   
                Optional<Year> year = element.get().getYearByYearCode(yearCode);                            //O (Logn)
                if(year.isEmpty()){                                                                         //O (1)
                    return;                                                                                 //O (1)
                }

                Optional<Float> value = year.get().getValue().getValue();                                   //O (1)
                if (value.isEmpty()) {
                    return;
                }                                                                                           //O (1)
                sum.add(value.get());                                                                       //O (1)

            });
        //Worst time complexity -> O(n + nLog(n)) = O(nLog(n))
        return sum.doubleValue();
    }
}
