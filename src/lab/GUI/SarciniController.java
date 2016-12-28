package lab.GUI;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import lab.domain.Post;
import lab.domain.Sarcina;
import lab.domain.ValidationException;
import lab.service.DuplicateIdException;
import lab.service.SarcinaManagement;
import lab.utils.Observable;
import lab.utils.Observer;

import java.util.function.Predicate;


/**
 * Created by Costi on 27.11.2016.
 */
public class SarciniController implements Observer<Sarcina> {
    SarcinaManagement sarcinaManagement;//=new SarcinaManagement();
    ObservableList<Sarcina> model;//=FXCollections.observableArrayList(sarcinaManagement.getAllSarcina());;
    FilteredList<Sarcina> filteredList;

    @FXML
    public ListView<Sarcina> listViewSarcini;
    @FXML
    Button addButtonSarcini;
    @FXML
    Button deleteButtonSarcini;
    @FXML
    Button updateButtonSarcini;
    @FXML
    Button clearAllButtonSarcini;
    @FXML
    TextField idTextFieldSarcini;
    @FXML
    TextField descriereTextFieldSarcini;

    @FXML
    TextField searchBox;

    public SarciniController() {
    }

    public void setService(SarcinaManagement service) {
        this.sarcinaManagement=service;
        this.model= FXCollections.observableArrayList(service.getAllSarcina());
        listViewSarcini.setItems(model);
        listViewSarcini.getSelectionModel().selectedItemProperty().addListener(changeSarcinaTableItemListener()) ;
        filteredList=new FilteredList<Sarcina>(model,e->true);
    }

  /*  @FXML
    public void initialize(){
        listViewSarcini.setItems(model);
    }*/

    public ChangeListener<Sarcina> changeSarcinaTableItemListener(){
        ChangeListener<Sarcina> changeListener=(observable,oldValue,newValue) ->{
            showPostDetails(newValue);
            idTextFieldSarcini.setEditable(false);
        };
        return changeListener;
    }

    public void handleSearchBox(){

            searchBox.textProperty().addListener((observableValue,oldValue,newValue)->{
                filteredList.setPredicate((Predicate<? super Sarcina>) sarcina->{
                    if(newValue==null ||newValue.isEmpty())
                        return true;
                    String lowerCaseFilter=newValue.toLowerCase();
                    if (sarcina.getDescriere().toLowerCase().contains(lowerCaseFilter))
                        return true;
                    else if(sarcina.getId().toString().contains(newValue))
                        return true;
                    return false;

                } );
            });
        listViewSarcini.setItems(filteredList);
    }


    public void handleDeleteButton(){
        sarcinaManagement.deleteSarcina(Integer.parseInt(idTextFieldSarcini.getText()));
        showPostDetails(null);
    }

    public void showPostDetails(Sarcina value){
        if (value==null){
            idTextFieldSarcini.setText("");
            descriereTextFieldSarcini.setText("");
            idTextFieldSarcini.setEditable(true);
        }
        else
        {
            idTextFieldSarcini.setText(value.getId().toString());
            descriereTextFieldSarcini.setText(value.getDescriere());
            idTextFieldSarcini.setEditable(false);
        }
    }

    public void handleAddButton(){
        try {
            Integer id = Integer.parseInt(idTextFieldSarcini.getText());
            String descriere = descriereTextFieldSarcini.getText();
            sarcinaManagement.addSarcina(id, descriere);
        }catch (ValidationException ex){
            showErrorMessage(ex.getMessage());
        }
        catch (DuplicateIdException dupId){
            MessageAlert.showErrorMessage(null,dupId.getMessage());
        }
    }

    public void handleClearAllFields(){
        showPostDetails(null);
        idTextFieldSarcini.setEditable(true);
    }

    public void handleUpdatePost(){
        try {
            sarcinaManagement.updateSarcina(Integer.parseInt(idTextFieldSarcini.getText()),descriereTextFieldSarcini.getText().toString());
            showPostDetails(null);
        }
        catch (ValidationException ex){
            MessageAlert.showErrorMessage(null,ex.getMessage());
        }

    }


    static void showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }

    @Override
    public void update(Observable<Sarcina> observable) {
        SarcinaManagement service=(SarcinaManagement) observable;
        model.setAll(service.getAllSarcina());
    }
}
