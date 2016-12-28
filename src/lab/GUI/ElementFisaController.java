package lab.GUI;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lab.Main;
import lab.domain.ElementFisa;
import lab.domain.Post;
import lab.domain.Sarcina;
import lab.service.*;
import lab.utils.Observable;
import lab.utils.Observer;

import java.io.IOException;

/**
 * Created by Costi on 07.12.2016.
 */
public class ElementFisaController implements Observer<ElementFisa> {
    @FXML
    public TableView<ElementFisa> tableViewFisaPost;
    public TableView<Post> tableViewPosturiFise;
    public TableView<Sarcina> tableViewSarciniFise;
    public ListView<Sarcina> listViewSarcini;


    TableColumn<Post,Integer> idViewPost;
    TableColumn denumireViewPost;
    TableColumn tipViewPost;

    TableColumn<Sarcina,Integer> idViewSarcina;
    TableColumn descriereViewSarcina;

    TableColumn<ElementFisa,Integer> idViewFisa;
    TableColumn postViewFisa;
    TableColumn sarciniViewFisa;


    FiseManagement fiseManagement;
    PostManagement postManagement;
    SarcinaManagement sarcinaManagement;
    ObservableList<ElementFisa> modelFise;
    ObservableList<Post> modelPosturi;
    ObservableList<Sarcina> modelSarcini;
    @Override
    public void update(Observable<ElementFisa> observable) {
        FiseManagement service=(FiseManagement) observable;
        modelFise.setAll(service.getAllFise());

    }

    public void setService(FiseManagement serviceFise, PostManagement servicePosturi, SarcinaManagement serviceSarcini) {
        this.fiseManagement=serviceFise;
        sarcinaManagement=serviceSarcini;
        postManagement=servicePosturi;
        this.modelFise= FXCollections.observableArrayList(fiseManagement.getAllFise());
        this.modelPosturi= FXCollections.observableArrayList(postManagement.getAllPosts());
        this.modelSarcini= FXCollections.observableArrayList(sarcinaManagement.getAllSarcina());
        tableViewFisaPost.setItems(modelFise);
        tableViewPosturiFise.setItems(modelPosturi);
        tableViewSarciniFise.setItems(modelSarcini);
        //listViewSarcini.getSelectionModel().selectedItemProperty().addListener(changeSarcinaTableItemListener()) ;
        tableViewFisaPost.getSelectionModel().selectedItemProperty().addListener(changeSarcinaTableItemListener());
        initTableSarcini();
        initTableFise();
        initTablePosturi();
        //tableViewFisaPost.getSelectionModel().selectedItemProperty().addListener(changeSarcinaTableItemListener()) ;
    }

    private void initTableFise() {
        idViewFisa=new TableColumn<>("Id");
        postViewFisa=new TableColumn<ElementFisa, String>("Post");
//        sarciniViewFisa=new TableColumn<ElementFisa, String>("Sarcini");
        sarciniViewFisa=new TableColumn<ElementFisa,Integer>("Numar sarcini");
        tableViewFisaPost.getColumns().add(idViewFisa);
        tableViewFisaPost.getColumns().add(postViewFisa);
        tableViewFisaPost.getColumns().add(sarciniViewFisa);

        idViewFisa.setCellValueFactory(new PropertyValueFactory<ElementFisa,Integer>("id"));
        postViewFisa.setCellValueFactory(new PropertyValueFactory<ElementFisa,Integer>("post"));
        sarciniViewFisa.setCellValueFactory(new PropertyValueFactory<ElementFisa,Integer>("sarcini"));
//        sarciniViewFisa.setCellValueFactory(sarcinaManagement.getAllSarcina().size());

        tableViewPosturiFise.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void initTablePosturi() {
        idViewPost=new TableColumn<>("Id");
        denumireViewPost=new TableColumn<Post, String>("Denumire");
        tipViewPost=new TableColumn<Post, String>("Tip");
        tableViewPosturiFise.getColumns().add(idViewPost);
        tableViewPosturiFise.getColumns().add(denumireViewPost);
        tableViewPosturiFise.getColumns().add(tipViewPost);

        idViewPost.setCellValueFactory(new PropertyValueFactory<Post,Integer>("id"));
        denumireViewPost.setCellValueFactory(new PropertyValueFactory<Post, String>("denumire"));
        tipViewPost.setCellValueFactory(new PropertyValueFactory<Post, String>("tip"));

        //tableViewPosturiFise.getSelectionModel().selectedItemProperty().addListener(postController.changePostTableItemListener());
        tableViewPosturiFise.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    private void initTableSarcini() {
        idViewSarcina=new TableColumn<>("Id");
        descriereViewSarcina=new TableColumn<Post, String>("Descriere");
        tableViewSarciniFise.getColumns().add(idViewSarcina);
        tableViewSarciniFise.getColumns().add(descriereViewSarcina);


        idViewSarcina.setCellValueFactory(new PropertyValueFactory<Sarcina,Integer>("id"));
        descriereViewSarcina.setCellValueFactory(new PropertyValueFactory<Post, String>("descriere"));

        //tableViewPosturiFise.getSelectionModel().selectedItemProperty().addListener(postController.changePostTableItemListener());
        tableViewPosturiFise.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    public void handleAddButton(){
        if(tableViewPosturiFise.getSelectionModel().getSelectedItem()!=null && tableViewSarciniFise.getSelectionModel().getSelectedItem()!=null ) {
            Post p = tableViewPosturiFise.getSelectionModel().getSelectedItem();
            Sarcina s = tableViewSarciniFise.getSelectionModel().getSelectedItem();
            try {
                fiseManagement.addFisa(p, s);
            }
            catch (ExistaSarcinaDejaException ex1){
                MessageAlert.showErrorMessage(null,ex1.getMessage());
            }
        }else
            MessageAlert.showErrorMessage(null,"Selectati un Post si o Sarcina");
    }

    public void handleDeleteButton(){
        if(tableViewFisaPost.getSelectionModel().getSelectedItem()!= null)
            fiseManagement.deleteElementFisa(tableViewFisaPost.getSelectionModel().getSelectedItem().getId());
        else  MessageAlert.showErrorMessage(null,"Selectati mai intai Fisa");
    }

    public ChangeListener<ElementFisa> changeSarcinaTableItemListener(){
        ChangeListener<ElementFisa> changeListener=(observable,oldValue,newValue) ->{
            showSarcini(newValue);
        };
        return changeListener;
    }

    private void showSarcini(ElementFisa value) {
        if (value==null){

        }
        else
        {
            ObservableList<Sarcina> model=FXCollections.observableArrayList(value.getSarcini());
            listViewSarcini.setItems(model);
        }
    }

    public void showTop(){
        try {
            FXMLLoader loader1 = new FXMLLoader();
            loader1.setLocation(ElementFisaController.class.getResource("top3view.fxml"));
            BorderPane root = (BorderPane) loader1.load();


            Stage dialogStage = new Stage();
            dialogStage.setTitle("Top 3");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            Top3ViewController top3ViewController = loader1.getController();
            top3ViewController.setService(fiseManagement,sarcinaManagement,dialogStage);

            dialogStage.show();

        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
