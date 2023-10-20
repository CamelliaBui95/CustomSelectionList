package fr.btn.selectionlist;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class ListSelection<T> extends BorderPane {
    @FXML
    private TextField searchField;
    @FXML
    private ListView<T> allItemsView;
    @FXML
    private ListView<T> selectedItemsView;
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
        setOnClickItem();
        setDragStart();
        setDragOver();
        setDragAndDrop();

        searchField.textProperty().addListener((ob, o, n) -> bean.filter(n));
    }

    private void loadComponents() {
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
        this.allItemsView.setItems(bean.getFilteredAll());
        this.selectedItemsView.setItems(bean.getFilteredSelected());

        allItemsView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectedItemsView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void addItem() {
        T item = this.allItemsView.getSelectionModel().getSelectedItem();
        bean.addItem(item);
    }

    private void removeItem() {
        T item = this.selectedItemsView.getSelectionModel().getSelectedItem();
        bean.removeItem(item);
    }

    @FXML
    private void addItems() {
        ObservableList<T> items = this.allItemsView.getSelectionModel().getSelectedItems();
        bean.add(items);
    }

    @FXML
    private void removeItems() {
        ObservableList<T> items = this.selectedItemsView.getSelectionModel().getSelectedItems();
        bean.remove(items);
    }

    @FXML
    private void addAll() {
        bean.addAll();
    }

    @FXML
    private void removeAll() {
        bean.removeAll();
    }

    private void setOnClickItem() {
        allItemsView.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2)
                addItems();
            else if(e.getClickCount() == 3)
                addAll();
        });

        selectedItemsView.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2)
                removeItems();
            else if(e.getClickCount() == 3)
                removeAll();
        });
    }

    private void setDragStart() {
        allItemsView.setOnDragDetected(dragEvent -> {
            Dragboard dragboard = allItemsView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString("Select");
            dragboard.setContent(content);
            dragEvent.consume();
        });

        selectedItemsView.setOnDragDetected(dragEvent -> {
            Dragboard dragboard = selectedItemsView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString("UnSelect");
            dragboard.setContent(content);
            dragEvent.consume();
        });

    }
    private void setDragOver() {
        allItemsView.setOnDragOver(e -> {
            if (e.getGestureSource() == selectedItemsView)
                e.acceptTransferModes(TransferMode.ANY);
            e.consume();
        });

        selectedItemsView.setOnDragOver(e -> {
            if (e.getGestureSource() == allItemsView)
                e.acceptTransferModes(TransferMode.ANY);
            e.consume();
        });

        searchField.setOnDragOver(e -> {
            if(e.getGestureSource() == allItemsView && allItemsView.getSelectionModel().getSelectedItems().size() == 1
                    || e.getGestureSource() == selectedItemsView && selectedItemsView.getSelectionModel().getSelectedItems().size() == 1)
                e.acceptTransferModes(TransferMode.ANY);

            e.consume();
        });
    }

    private void setDragAndDrop() {
        allItemsView.setOnDragDropped(e -> {
            if(e.getGestureSource() == selectedItemsView)
                removeItems();
            e.consume();
        });

        selectedItemsView.setOnDragDropped(e -> {
            if(e.getGestureSource() == allItemsView)
                addItems();
            e.consume();
        });

        searchField.setOnDragDropped(e -> {
            if(e.getGestureSource() == allItemsView)
                searchField.textProperty().set(allItemsView.getSelectionModel().getSelectedItem().toString());
            else if(e.getGestureSource() == selectedItemsView)
                searchField.textProperty().set(selectedItemsView.getSelectionModel().getSelectedItem().toString());
            e.consume();
        });
    }




}
