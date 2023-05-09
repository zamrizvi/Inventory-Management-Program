package softwareproject.inventory_management;

import Model.InHouse;
import Model.Inventory;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**This program creates an application that manages inventory for a company.
 *
 * For future enhancement I would like to make it so that when you add associated parts from the parts table, the program
 * will subtract that part from the inventory. It will also calculate how many parts are used based on how many of the products are being made
 * and which parts are being used. I would like to create dialog boxes that will inform the user that there are not enough parts
 * to create the product they want to create. This can add further functionality to Inventory Management.
 *
 * @author Zamir Rizvi
 * */
public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** Main method. Starts the application.
     *
     * @param args args
     * */
    public static void  main(String[] args) {
        launch(args);
    }
}