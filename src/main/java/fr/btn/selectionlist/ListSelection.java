package fr.btn.selectionlist;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
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
        allItemsView.setOnDragDetected(dragEvent -> startDragEventHandler(dragEvent, allItemsView, "Select"));

        selectedItemsView.setOnDragDetected(dragEvent -> startDragEventHandler(dragEvent, selectedItemsView, "UnSelect"));
    }
    private void startDragEventHandler(MouseEvent e, ListView<T> list, String strContent) {
        Dragboard dragboard = list.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putString(strContent);
        dragboard.setContent(content);
        e.consume();
    }
    private void setDragOver() {
        allItemsView.setOnDragOver(e -> {
            if (isFrom(e, selectedItemsView, 1, Integer.MAX_VALUE))
                e.acceptTransferModes(TransferMode.ANY);
            e.consume();
        });

        selectedItemsView.setOnDragOver(e -> {
            if (isFrom(e, allItemsView, 1, Integer.MAX_VALUE))
                e.acceptTransferModes(TransferMode.ANY);
            e.consume();
        });

        searchField.setOnDragOver(e -> {
            if(isFrom(e, allItemsView, 1, 2)
                    || isFrom(e, selectedItemsView, 1, 2))
                e.acceptTransferModes(TransferMode.ANY);

            e.consume();
        });
    }

    private boolean isFrom(DragEvent e, ListView<T> list, int minSize, int maxSize) {
        boolean case1 = e.getGestureSource() == list;
        boolean case2 = list.getSelectionModel().getSelectedItems().size() >= minSize && list.getSelectionModel().getSelectedItems().size() < maxSize;
        return case1 && case2;
    }

    private void setDragAndDrop() {
        allItemsView.setOnDragDropped(e -> {
            if(isFrom(e, selectedItemsView, 1, Integer.MAX_VALUE))
                removeItems();
            e.consume();
        });

        selectedItemsView.setOnDragDropped(e -> {
            if(isFrom(e, allItemsView, 1, Integer.MAX_VALUE))
                addItems();
            e.consume();
        });

        searchField.setOnDragDropped(e -> {
            if(isFrom(e, allItemsView, 1, 2))
                searchField.textProperty().set(allItemsView.getSelectionModel().getSelectedItem().toString());
            else if(isFrom(e, selectedItemsView, 1 ,2))
                searchField.textProperty().set(selectedItemsView.getSelectionModel().getSelectedItem().toString());
            e.consume();
        });
    }




}
