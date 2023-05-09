module softwareproject.inventory_management {
    requires javafx.controls;
    requires javafx.fxml;


    opens softwareproject.inventory_management to javafx.fxml;
    exports softwareproject.inventory_management;
    exports Controller;
    opens Controller to javafx.base, javafx.fxml;
    exports Model;
    opens Model to javafx.base, javafx.fxml;
}