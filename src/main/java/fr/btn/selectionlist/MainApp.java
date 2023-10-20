package fr.btn.selectionlist;

import fr.btn.selectionlist.components.Color;
import fr.btn.selectionlist.components.Component;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {
    private List<Color> allColors;
    private List<Color> selectedColors;
    @Override
    public void start(Stage stage) throws Exception {
        setUpApp(stage);
    }

    public void setUpApp(Stage stage) {
        ListSelection<Color> listSelection = new ListSelection<>();
        generateLists();
        listSelection.setLists(allColors, selectedColors);

        stage.setScene(new Scene(listSelection));
        stage.show();
    }

    public void generateLists() {
        allColors = new ArrayList<>();
        selectedColors = new ArrayList<>();

        allColors.add(new Color(1, "Black"));
        allColors.add(new Color(2, "White"));
        allColors.add(new Color(3, "Green"));
        allColors.add(new Color(4, "Blue"));
        allColors.add(new Color(5, "Pink"));
        allColors.add(new Color(6, "Gray"));
        allColors.add(new Color(7, "Rose Gold"));

        selectedColors.add(new Color(3, "Red"));
        selectedColors.add(new Color(1, "Emerald Green"));

    }
}
