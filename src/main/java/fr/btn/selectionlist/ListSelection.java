package fr.btn.selectionlist;

import fr.btn.selectionlist.components.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;


public class ListSelection<T> extends BorderPane {
    @FXML
    private TextField searchField;
    @FXML
    private ListView<T> allItems;
    @FXML
    private ListView<T> selectedItems;
    @FXML
    private Button selectTwo;
    @FXML
    private Button unSelectOne;
    @FXML
    private Button unSelectTwo;
    @FXML
    private Button selectOne;
    BorderPane borderPane;

    private ListSelectionBean<T> bean;

    public ListSelection() {
        loadComponents();
        bean = new ListSelectionBean<>();

        searchField.textProperty().addListener((ob, o, n) -> bean.filter(n));
    }

    public void loadComponents() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectionList-View.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            borderPane = loader.load();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void setLists(List<T> allItems, List<T> selectedItems) {
        bean.setLists(allItems, selectedItems);
        this.allItems.setItems(bean.getAllItems());
        this.selectedItems.setItems(bean.getSelectedItems());


    }


}
