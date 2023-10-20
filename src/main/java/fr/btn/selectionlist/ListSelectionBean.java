package fr.btn.selectionlist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.List;

public class ListSelectionBean<T> {
    private ObservableList<T> allItems;

    private ObservableList<T> selectedItems;
    private FilteredList<T> filteredAll;
    private FilteredList<T> filteredSelected;
    public ListSelectionBean() {
        allItems = FXCollections.observableArrayList();
        selectedItems = FXCollections.observableArrayList();
        filteredAll = new FilteredList<>(allItems);
        filteredSelected = new FilteredList<>(selectedItems);

    }

    public void setLists(List<T> allItems, List<T> selectedItems) {
        for(T c : allItems)
            if(!selectedItems.contains(c))
                this.allItems.add(c);

        this.selectedItems.addAll(selectedItems);
    }

    public void filter(String searchStr) {
        filteredAll.setPredicate(currentItem -> currentItem.toString().toLowerCase().contains(searchStr.toLowerCase()));
        filteredSelected.setPredicate(currentItem -> currentItem.toString().toLowerCase().contains(searchStr.toLowerCase()));
    }

    public void addItem(T item) {
        allItems.remove(item);
        selectedItems.add(item);
    }

    public void removeItem(T item) {
        selectedItems.remove(item);
        allItems.add(item);
    }

    public void addAll() {
        selectedItems.addAll(allItems);
        allItems.removeAll(selectedItems);
    }
    public void removeAll() {
        allItems.addAll(selectedItems);
        selectedItems.removeAll(allItems);
    }
    public void add(ObservableList<T> list) {
        selectedItems.addAll(list);
        allItems.removeAll(list);
    }
    public void remove(ObservableList<T> list) {
        allItems.addAll(list);
        selectedItems.removeAll(list);
    }

    public ObservableList<T> getAllItems() {
        return this.allItems;
    }

    public ObservableList<T> getSelectedItems() {
        return this.selectedItems;
    }

    public FilteredList<T> getFilteredAll() {
        return filteredAll;
    }

    public FilteredList<T> getFilteredSelected() {
        return filteredSelected;
    }
}
