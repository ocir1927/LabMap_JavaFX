package lab.GUI;

import javafx.fxml.FXML;
import lab.Main;

/**
 * Created by Costi on 27.11.2016.
 */
public class StartViewController {
    Main mainApp;

    public StartViewController() {
    }

    public void setMainApplic(Main app)
    {
        this.mainApp=app;
    }

    @FXML
    private void handleSarciniButton(){
        mainApp.initSarcinaView();
    }

    public void handlePosturiButton(){
        mainApp.initPostView();
    }

    public void handleFiseButton(){mainApp.initFisaPostView();}
}
