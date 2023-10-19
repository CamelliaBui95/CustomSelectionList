module fr.btn.selectionlist {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens fr.btn.selectionlist to javafx.fxml;
    exports fr.btn.selectionlist;
}