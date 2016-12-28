package lab.GUI;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import lab.domain.Post;
import lab.domain.ValidationException;
import lab.service.DuplicateIdException;
import lab.service.PostManagement;
import lab.utils.Observable;
import lab.utils.Observer;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 23.11.2016
 * Time: 19:47
 * To change this template use File | Settings | File Templates.
 */
public class PostController implements Observer<Post> {
    private ObservableList<Post> model;
    private PostView view;
    private PostManagement serv;
    private FilteredList<Post> postFilteredList;

    public PostController(PostManagement serv, PostView view) {
        this.serv = serv;
        this.view = view;
        this.model= FXCollections.observableArrayList(serv.getAllPosts());
        view.postTable.setItems(model);
//        handleFilterId();
    }

    public ChangeListener<Post> changePostTableItemListener(){
        ChangeListener<Post> changeListener=(observable,oldValue,newValue) ->{
            showPostDetails(newValue);
            view.idTextFiled.setEditable(false);
        };
        return changeListener;
    }

    @Override
    public void update(Observable<Post> observable) {
        PostManagement s=(PostManagement)observable;
        model.setAll(s.getAllPosts());
    }

    public Post extractPost(){
        Integer id=Integer.parseInt(view.idTextFiled.getText());
        String denumire=view.denumireTextFiled.getText();
        String tip=view.tipTextFiled.getText();
        return new Post(id,denumire,tip);
    }

    public void showPostDetails(Post value){
        if (value==null){
            view.idTextFiled.setText("");
            view.denumireTextFiled.setText("");
            view.tipTextFiled.setText("");
        }
        else
        {
            view.idTextFiled.setText(value.getId().toString());
            view.denumireTextFiled.setText(value.getDenumire());
            view.tipTextFiled.setText(value.getTip());
        }
    }

    static void showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }

    public void handleAddPost(ActionEvent event){
        Post p=extractPost();
        try{
            serv.addPost(p.getId(),p.getDenumire(),p.getTip());
            showPostDetails(null);
        }
        catch (ValidationException ex){
            showErrorMessage(ex.getMessage());
        }
        catch (DuplicateIdException dupId){
            showErrorMessage(dupId.getMessage());
        }
    }

    public void handleClearAllFields(ActionEvent event){
        showPostDetails(null);
        view.idTextFiled.setEditable(true);
    }

    public void handleDeletePost(ActionEvent event){
        Post p=extractPost();
        serv.deletePost(p.getId());
        showPostDetails(null);
    }

    public void handleUpdatePost(ActionEvent event){
        Post p=extractPost();
        try {
            serv.updatePost(p.getId(),p.getDenumire(),p.getTip());
            showPostDetails(null);
        }
        catch (ValidationException ex){
            showErrorMessage(ex.getMessage());
        }

    }

    public void handleToggleNone(ActionEvent event){
        view.postTable.setItems(model);
    }

    public void handleToggleParttime(ActionEvent event){
        view.postTable.setItems(FXCollections.observableList(serv.filterPostByType("parttime")));
    }

    public void handleToggleFulltime(ActionEvent event){
        view.postTable.setItems(FXCollections.observableList(serv.filterPostByType("fulltime")));
    }

    public void handleFilterId(){
        postFilteredList=new FilteredList<Post>(model);
        postFilteredList.predicateProperty().bind(Bindings.createObjectBinding(() ->
                        (Post post) -> post.getId().compareTo(Integer.parseInt(view.textFieldIdFloor.getText()))>0
                                && post.getId().compareTo(Integer.parseInt(view.textFieldIdRoof.getText() ) )<0,
//                    serv.findPostById(view.textFieldIdFloor) && serv.findPostById(view.textFieldIdRoof)
                view.textFieldIdRoof.textProperty(),
                view.textFieldIdFloor.textProperty()
        ));
        view.postTable.setItems(postFilteredList);
    }

    public void handleUntoggleFilterId(){
        view.postTable.setItems(model);
    }

}
