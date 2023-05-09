package Controller;


import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * This class displays and controls the Main Menu.
 * The main menu opens up with two tables, one with a list of parts and the other with a list of products.
 * There are also buttons to add, modify, or delete parts or products.
 *
 * @author Zamir Rizvi*/
public class MainMenu implements Initializable {

    /** Stage field*/
    Stage stage;
    /** Parent field*/
    Parent scene;

    @FXML
    public TextField partsTxt;
    @FXML
    public TextField productsTxt;
    @FXML
    public TableView<Part> partsTableView;
    @FXML
    public TableColumn<Part, Integer> partIdCol;
    @FXML
    public TableColumn<Part, String> partNameCol;
    @FXML
    public TableColumn<Part, Integer> partInvCol;
    @FXML
    public TableColumn<Part, Double> partPriceCol;
    @FXML
    public TableView<Product> productsTableView;
    @FXML
    public TableColumn<Product, Integer> productIdCol;
    @FXML
    public TableColumn<Product, String> productNameCol;
    @FXML
    public TableColumn<Product, Integer> productInvCol;
    @FXML
    public TableColumn<Product, Double> productPriceCol;


    /**
     * Method to display the Add Part Screen.
     * Pressing this button will navigate the user to another screen where they can enter fields to create a new Part
     *
     * @param event event
     * */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/softwareproject/inventory_management/AddPart.fxml"));
        scene = loader.load();
        stage.setScene(new Scene(scene, 600,400));
        stage.show();
    }

    /**
     * Class to display the Modify Part page.
     * Pressing this button will navigate the user to another screen where they can change the fields of a Part.
     * If the user does not select a part from the list, it results in a runtime error: NullPointerException.
     * Created an exception handle that pops up a dialog box if the user did not select a part to modify.
     *
     * @param event event
     * */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        try {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/softwareproject/inventory_management/ModifyPart.fxml"));
            scene = loader.load();

            ModifyPart modPart = loader.getController();
            modPart.receivePart(partsTableView.getSelectionModel().getSelectedItem());

            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene, 600, 400));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a Part to Modify");
            alert.showAndWait();
        }
    }

    /**
     * Method to delete a part that was selected.
     * User selects a part to delete. Before deletion, a dialog box pops up to confirm if the user wants to delete the part.
     *
     * @param event event
     * */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        Part SP = partsTableView.getSelectionModel().getSelectedItem();
        if (SP == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(SP);
        }
    }

    /**
     * Method to display the Add Product Screen.
     * Pressing this button will navigate the user to another screen where they can enter fields to create a new Product.
     * The user can also add or remove parts that are associated with the product.
     *
     * @param event event
     * */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/softwareproject/inventory_management/AddProduct.fxml"));
        scene = loader.load();
        stage.setScene(new Scene(scene, 1000,700));
        stage.show();
    }

    /**
     * Class to display the Modify Product page.
     * Pressing this button will navigate the user to another screen where they can change the fields of a Product.
     * The user may also add or remove parts associated with the Product.
     * If the user does not select a product from the list, it results in a runtime error: NullPointerException.
     * Created an exception handle that pops up a dialog box if the user did not select a product to modify.
     *
     * @param event event
     * */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {

        try {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/softwareproject/inventory_management/ModifyProduct.fxml"));
            scene = loader.load();

            ModifyProduct modProduct = loader.getController();
            modProduct.receiveProduct(productsTableView.getSelectionModel().getSelectedItem());

            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene, 1000, 700));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a Product to Modify");
            alert.showAndWait();
        }
    }

    /**
     * Method to delete a product that was selected.
     * User selects a product to delete. Before deletion, a dialog box pops up to confirm if the user wants to delete the product.
     * Also created an exception handle that pops up a dialog box informing the user to first remove any associated parts from the product
     * before deletion.
     *
     * @param event event
     * */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
            Product SP = productsTableView.getSelectionModel().getSelectedItem();
            if (SP == null) {
                return;
            }
            if (!(SP.getAllAssociatedParts().isEmpty())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Delete all associated parts before deleting the selected Product");
                alert.showAndWait();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(SP);
            }
    }

    /**
     * Method to exit out of the program
     *
     * @param event event
     * */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * This method initializes when first opening the screen.
     * Sets up the tables in the main screen.
     * Also handles the search boxes for the parts and products tables.
     * The user may search using the IDs or the names. The methods filter the parts and products based on what is entered.
     * There was an error where if the user entered data that wasn't found in the table, the table displayed nothing.
     * Added a dialog box that pops up informing the user that the part or product was not found.
     *
     * @param url url
     * @param rb rb
     * */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));


        productsTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));


        partsTxt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (partsTxt == null) {
                    partsTableView.setItems(Inventory.getAllParts());
                }
                try {
                    if (!(Inventory.getAllParts().contains(Inventory.lookupPart(Integer.parseInt(partsTxt.getText()))))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Part ID Not Found");
                        alert.showAndWait();
                    }
                    else {
                        partsTableView.setItems(FXCollections.observableArrayList(Inventory.lookupPart(Integer.parseInt(partsTxt.getText()))));
                    }
                }
                catch (NumberFormatException e) {
                    if (Inventory.lookupPart(partsTxt.getText()).isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("No Part Found");
                        alert.showAndWait();
                    }
                    else {
                        partsTableView.setItems(Inventory.lookupPart(partsTxt.getText()));
                    }
                }
            }
        });

        productsTxt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (productsTxt == null) {
                    productsTableView.setItems(Inventory.getAllProducts());
                }
                try {
                    if(!(Inventory.getAllProducts().contains(Inventory.lookupProduct(Integer.parseInt(productsTxt.getText()))))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Product ID Not Found");
                        alert.showAndWait();
                    }
                    else {
                        productsTableView.setItems(FXCollections.observableArrayList(Inventory.lookupProduct(Integer.parseInt(productsTxt.getText()))));
                    }
                }
                catch (NumberFormatException e) {
                    if (Inventory.lookupProduct(productsTxt.getText()).isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("No Product Found");
                        alert.showAndWait();
                    }
                    else {
                        productsTableView.setItems(Inventory.lookupProduct(productsTxt.getText()));
                    }
                }
            }
        });
    }
}