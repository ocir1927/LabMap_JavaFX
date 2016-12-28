package lab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lab.GUI.*;
import lab.service.FiseManagement;
import lab.service.PostManagement;
import lab.service.SarcinaManagement;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 09.10.2016
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
public class Main extends Application {

    Stage mainStage;
    BorderPane rootLayout;
    //PostManagement postManagement;
    //PostView postView=new PostView(serv);
    SarcinaManagement sarcinaManagement;
    PostManagement postManagement;
    FiseManagement fiseManagement;
    SarciniController sarciniController;

    @Override
    public void start(Stage stage) throws Exception {
        /*PostManagement serv=new PostManagement();
        PostView postView=new PostView(serv);
        Parent root=postView.getView();
        Scene scene=new Scene(root);
        stage.setScene(scene);


        stage.setTitle("Fisa Postului");
        stage.setMinHeight(400);
        stage.setMinWidth(650);
        stage.setWidth(650);
        stage.setHeight(400);
        stage.show();*/
        fiseManagement=new FiseManagement();
        postManagement=new PostManagement();
        sarcinaManagement=new SarcinaManagement();
        this.mainStage=stage;
        mainStage.setTitle("Fisa Postului");
        mainStage.setMinHeight(650);
        mainStage.setMinWidth(700);
        mainStage.setWidth(700);
        mainStage.setHeight(650);
        showMainStage();
        showStartView();


    }

    public void showStartView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/./lab/GUI/StartView.fxml"));
            BorderPane startView = fxmlLoader.load();
            rootLayout.setCenter(startView);
            StartViewController startViewController=fxmlLoader.getController();
            startViewController.setMainApplic(this);
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void showMainStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/./lab/GUI/Main.fxml"));
            rootLayout = fxmlLoader.load();
            MainController mainController=fxmlLoader.getController();
            mainController.setMainApplic(this);

            Scene scene=new Scene(rootLayout);
            mainStage.setScene(scene);
            mainStage.show();
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void initSarcinaView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/./lab/GUI/SarciniView.fxml"));
            BorderPane sarciniView=loader.load();
            SarciniController sarciniController = loader.getController();
            sarciniController.setService(sarcinaManagement);
            rootLayout.setCenter(sarciniView);
        //    sarciniController.listViewSarcini.setItems(FXCollections.observableArrayList(sarcinaManagement.getAllSarcina()));
            sarcinaManagement.addObserver(sarciniController);

        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void initPostView(){
        PostView pw=new PostView(postManagement);
        PostController postController=new PostController(postManagement,pw);
        rootLayout.setCenter(pw.getView());
        postManagement.addObserver(postController);
    }

    public void initFisaPostView(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/./lab/GUI/ElementFisaView.fxml"));
            BorderPane fisaPostView = loader.load();
            rootLayout.setCenter(fisaPostView);
            ElementFisaController elementFisaController=loader.getController();
            elementFisaController.setService(fiseManagement,postManagement,sarcinaManagement);
            fiseManagement.addObserver(elementFisaController);
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

}
