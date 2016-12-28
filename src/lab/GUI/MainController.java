package lab.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import lab.Main;

/**
 * Created by Costi on 26.11.2016.
 */
public class MainController {
    Main mainApp;
    public MainController() {

    }


    public void setMainApplic(Main app)
    {
        this.mainApp=app;
    }

    public void handleBtnAcasa(){
        mainApp.showStartView();
    }


    @FXML
    public Button btnAcasa;

}
