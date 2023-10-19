package fr.btn.selectionlist;

import fr.btn.selectionlist.components.Color;
import fr.btn.selectionlist.components.Component;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ListSelection<Color> listSelection = new ListSelection<>();
        stage.setScene(new Scene(listSelection));
        stage.show();
    }
}
