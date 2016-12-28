package lab.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lab.domain.ElementFisa;
import lab.domain.Sarcina;
import lab.service.FiseManagement;
import lab.service.SarcinaManagement;

import java.util.*;

/**
 * Created by Costi on 18.12.2016.
 */
public class Top3ViewController {
    @FXML
    Button buttonTop;

    @FXML
    TextField textFieldTop;

    @FXML
    ListView<Sarcina> sarcinaListView;

    private FiseManagement fiseManagement;
    private SarcinaManagement sarcinaManagement;
    Stage dialogStage;
//    ElementFisa elementFisa;


    public void setService(FiseManagement fiseManagement, SarcinaManagement sarcinaManagement, Stage stage) {
        this.fiseManagement = fiseManagement;
        this.sarcinaManagement=sarcinaManagement;
        this.dialogStage=stage;
//        this.elementFisa=s;

    }

    public void handleTopButton(){
        Map<Sarcina,Integer> sarciniNumber=new HashMap<Sarcina,Integer>();
        ArrayList<Sarcina> listaSarcini=new ArrayList<>();
        for (Sarcina s:sarcinaManagement.getAllSarcina()){
            int count=0;
            for (ElementFisa elementFisa:fiseManagement.getAllFise()){
                listaSarcini=new ArrayList<>(elementFisa.getSarcini());
                if(listaSarcini.contains(s))
                    count++;
            }
            sarciniNumber.put(s,count);
        }

        sarciniNumber=sortByValues(sarciniNumber);

        listaSarcini.clear();
        listaSarcini.addAll(sarciniNumber.keySet()) ;
//        int size=listaSarcini.size();

        if(isNumeric(textFieldTop.getText()) && !textFieldTop.getText().isEmpty() ) {

            int nr = Integer.parseInt(textFieldTop.getText());

      /*  for (int i=Integer.parseInt(textFieldTop.getText());i<size;i++){
            listaSarcini.remove(i);
        }*/

            while (nr < listaSarcini.size()) {
                listaSarcini.remove(nr);
            }
            sarcinaListView.setItems(FXCollections.observableList(listaSarcini));
        }else{
            MessageAlert.showErrorMessage(null,"Introduceti un numar intreg pozitiv valid");
        }

    }

    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    public static <K, V extends Comparable<V>> TreeMap<K, V> sortByValues(final Map<K,V> map) {
        Comparator<K> valueComparator =  new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = map.get(k2).compareTo(map.get(k1));
                if (compare == 0) return 1;
                else return compare;
            }
        };
        TreeMap<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

}
