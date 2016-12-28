package lab.GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lab.domain.Post;
import lab.service.PostManagement;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 20.11.2016
 * Time: 20:21
 * To change this template use File | Settings | File Templates.
 */
public class PostView {
    PostController postController;

    BorderPane borderPane;
    TableView<Post> postTable=new TableView();
    TableColumn<Post,Integer> idView=new TableColumn<>("Id");
    TableColumn denumireView=new TableColumn<Post, String>("Denumire");
    TableColumn tipView=new TableColumn<Post, String>("Tip");

    TextField idTextFiled=new TextField();
    TextField denumireTextFiled=new TextField();
    TextField tipTextFiled=new TextField();

    Button buttonAdd=new Button("Add");
    Button buttonUpdate=new Button("Update");
    Button buttonDelete=new Button("Delete");
    Button buttonClear = new Button("ClearAll");

    TextField textFieldIdFloor;
    TextField textFieldIdRoof;

    ToggleButton toggleFilterId;


    public PostView(PostManagement postManagement) {
        postController=new PostController(postManagement,this);
        postManagement.addObserver(postController);
        initBorderPane();
    }

    private void initBorderPane() {
        borderPane=new BorderPane();
//        borderPane.setTop(initTop());
        borderPane.setCenter(initCenter());
        borderPane.setLeft(initLeft());
    }

    private Node initLeft() {
        AnchorPane anchorPane=new AnchorPane();
        //anchor the TableView into the ap
        AnchorPane.setLeftAnchor(postTable,20d);
        AnchorPane.setTopAnchor(postTable,20d);

        postTable.setMinHeight(100d);
        postTable.setPrefHeight(400d);
        initTableView();
        anchorPane.getChildren().add(postTable);
        return anchorPane;
    }

    private void initTableView() {
        postTable.getColumns().add(idView);
        postTable.getColumns().add(denumireView);
        postTable.getColumns().add(tipView);

        idView.setCellValueFactory(new PropertyValueFactory<Post,Integer>("id"));
        denumireView.setCellValueFactory(new PropertyValueFactory<Post, String>("denumire"));
        tipView.setCellValueFactory(new PropertyValueFactory<Post, String>("tip"));

        postTable.getSelectionModel().selectedItemProperty().addListener(postController.changePostTableItemListener());
        postTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        postController.showPostDetails(null);
    }

    public BorderPane getView(){
        return borderPane;
    }

    private Node initCenter(){
        AnchorPane anchorPane=new AnchorPane();

        //init GridPane students details
        GridPane gridDetails=new GridPane();
        gridDetails.setHgap(5);
        gridDetails.setVgap(5);
        AnchorPane.setLeftAnchor(gridDetails,20d);
        AnchorPane.setTopAnchor(gridDetails,20d);
        ColumnConstraints c=new ColumnConstraints();
        c.setPrefWidth(100);
        gridDetails.getColumnConstraints().add(c);



        Label labelId=createLabel("Id:",12, Color.BLACK);
        labelId.setStyle("-fx-font-weight: bold");
        Label labelDenumire=createLabel("Denumire:",12,Color.BLACK);
        labelDenumire.setStyle("-fx-font-weight: bold");
        Label labelTip=createLabel("Tip:",12,Color.BLACK);
        labelTip.setStyle("-fx-font-weight: bold");

        gridDetails.add(labelId, 0, 0);
        gridDetails.add(labelDenumire, 0, 1);
        gridDetails.add(labelTip, 0, 2);
        gridDetails.add(idTextFiled, 1, 0);
        gridDetails.add(denumireTextFiled, 1, 1);
        gridDetails.add(tipTextFiled, 1, 2);

        HBox hb=new HBox(5, buttonAdd,buttonUpdate, buttonDelete,buttonClear);

        gridDetails.add(hb,1,5);
        /*gridDetails.add(buttonAdd,0,4);
        gridDetails.add(buttonDelete,0,5);
        gridDetails.add(buttonUpdate,0,6);
        gridDetails.add(buttonClear,0,7);*/

        gridDetails.addRow(6, new Text(""));
        gridDetails.addRow(7, new Text(""));
        gridDetails.addRow(8, new Text(""));

        final ToggleGroup groupFilterByType = new ToggleGroup();
        Label lableFilterByType=createLabel("Filter by type:",12, Color.BLACK);
        RadioButton buttonNone=new RadioButton("None");
        buttonNone.setToggleGroup(groupFilterByType);
        buttonNone.setOnAction(postController::handleToggleNone);

        RadioButton buttonParttime=new RadioButton("Parttime");
        buttonParttime.setToggleGroup(groupFilterByType);
        buttonParttime.setOnAction(postController::handleToggleParttime);

        RadioButton buttonFulltime=new RadioButton("Fulltime");
        buttonFulltime.setToggleGroup(groupFilterByType);
        buttonFulltime.setOnAction(postController::handleToggleFulltime);

        VBox hbFilterType=new VBox(5,lableFilterByType,buttonNone,buttonFulltime,buttonParttime);

        gridDetails.add(hbFilterType,1,9);

        toggleFilterId=new ToggleButton("Toggle Id filter");
        gridDetails.add(toggleFilterId,1,11);

        Label lableFilterById=createLabel("Filter by ID:",12, Color.BLACK);
        textFieldIdFloor=new TextField();
        textFieldIdFloor.setPrefWidth(70d);

        Label lableBetweenId= createLabel("<-->",12, Color.BLACK);
        textFieldIdRoof=new TextField();
        textFieldIdRoof.setPrefWidth(70d);

        final ToggleGroup groupToggleIdFilter = new ToggleGroup();
        groupToggleIdFilter.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if(toggle==null)
                    postController.handleFilterId();
                else
                    postController.handleUntoggleFilterId();
            }
        });

        HBox hbFilterId=new HBox(lableFilterById,textFieldIdFloor,lableBetweenId,textFieldIdRoof);

        gridDetails.addRow(10,new Text(""));
        gridDetails.add(hbFilterId,1,12);

        anchorPane.getChildren().add(gridDetails);
        //init HBox Button
//        anchorPane.getChildren().add(hbFilterType);

       // HBox hb=new HBox(5, buttonAdd,buttonUpdate, buttonDelete,buttonClear);
        buttonAdd.setOnAction(postController::handleAddPost);
        buttonUpdate.setOnAction(postController::handleUpdatePost);
        buttonClear.setOnAction(postController::handleClearAllFields);
        buttonDelete.setOnAction(postController::handleDeletePost);

        toggleFilterId.setToggleGroup(groupToggleIdFilter);
       // toggleFilterId.setOnAction(postController::handleFilterId);
//        toggleFilterId.setOnAction(postController::handleUntoggleFilterId);


        AnchorPane.setBottomAnchor(hb,100d);
        AnchorPane.setLeftAnchor(hb,20d);
        //hb.setPadding(new Insets(30));
//        anchorPane.getChildren().add(hb);

        return anchorPane;
    }

    private Label createLabel(String s, int fontSize, Color c){
        Label l=new Label();
        l.setText(s);
        l.setFont(new Font(15));
        l.setTextFill(c);
        return l;
    }



}

