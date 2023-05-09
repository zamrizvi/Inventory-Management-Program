package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * This class displays and controls the Add Product Screen.
 * The user can enter data into the fields. The user may also add or remove parts associated with the product and then
 * create a product.
 *
 * @author Zamir Rizvi
 * */

public class AddProduct implements Initializable {


    /** Stage field*/
    Stage stage;

    /** Parent field*/
    Parent scene;

    /** Observable List field of associated Parts */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    @FXML
    public TextField addProductSearchTxt;
    @FXML
    public TextField addProductIdTxt;
    @FXML
    public  TextField addProductNameTxt;
    @FXML
    public TextField addProductInvTxt;
    @FXML
    public TextField addProductPriceTxt;
    @FXML
    public TextField addProductMaxTxt;
    @FXML
    public TextField addProductMinTxt;

    @FXML
    public TableView<Part> partTableView;
    @FXML
    public TableColumn<Part, Integer> partIdCol;
    @FXML
    public TableColumn<Part, String> partNameCol;
    @FXML
    public TableColumn<Part, Integer> partInvCol;
    @FXML
    public TableColumn<Part, Double> partPriceCol;
    @FXML
    public TableView<Part> associatedPartTableView;
    @FXML
    public TableColumn<Part, Integer> associatedPartIdCol;
    @FXML
    public TableColumn<Part, String> associatedPartNameCol;
    @FXML
    public TableColumn<Part, Integer> associatedPartInvCol;
    @FXML
    public TableColumn<Part, Double> associatedPartPriceCol;


    /**
     * This method adds a part from the Parts table to the associated parts table.
     *
     * @param event event
     * */
    @FXML
    void onActionAddPart(ActionEvent event) {
        Part SP = partTableView.getSelectionModel().getSelectedItem();
        if (SP == null) {
            return;
        }
        associatedParts.add(SP);
        associatedPartTableView.setItems(associatedParts);
    }

    /**
     * This method removes a part from the associated parts table.
     * Dialog box pops up to confirm if the user wants to remove a part.
     *
     * @param event event
     * */
    @FXML
    void onActionRemovePart(ActionEvent event) {
        Part SP = associatedPartTableView.getSelectionModel().getSelectedItem();
        if (SP == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            associatedParts.remove(SP);
            associatedPartTableView.setItems(associatedParts);
        }
    }

    /**
     * This method saves the Product and navigates back to the Main Menu.
     * The ID is unique and auto generated. The user can enter data into the fields and save the Product.
     * There was a runtime error: NullPointerException that occurred when you tried to save without having data in the fields.
     * Added a dialog box that informs the user to check the fields.
     * There was also another runtime error: NumberFormatError that occurred when you tried to enter the wrong type of data into the fields.
     * Added a dialog box that informs the user to check the fields.
     * Added a logic check to the inventory levels in the form of min <= stock <= max.
     *
     * @param event event
     * */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {

        try {
            int id = Inventory.uniqueProductId += 2;//Integer.parseInt(addProductIdTxt.getText());
            String name = addProductNameTxt.getText();
            double price = Double.parseDouble(addProductPriceTxt.getText());
            int stock = Integer.parseInt(addProductInvTxt.getText());
            int min = Integer.parseInt(addProductMinTxt.getText());
            int max = Integer.parseInt(addProductMaxTxt.getText());

            if (!(min <= stock && stock <= max)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Error: Minimum inventory must be less than or equal to the stock inventory " +
                        "and stock inventory must be less than or equal to maximum inventory.");
                alert.showAndWait();
                return;
            }

            Product newProduct = new Product(id, name, price, stock, min, max);
            for (Part associatePart : associatedParts) {
                newProduct.addAssociatedPart(associatePart);
            }
            Inventory.addProduct(newProduct);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/softwareproject/inventory_management/MainMenu.fxml"));
            scene = loader.load();
            stage.setScene(new Scene(scene, 1000, 600));
            stage.show();
        }
        catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Please enter valid values for fields");
            alert.showAndWait();
        }

    }

    /**
     * This method cancels the product creation and navigates back to the main menu.
     * A dialog box pops up to confirm if the user wants to cancel.
     *
     * @param event event
     * */
    @FXML
    void onActionDisplayMain(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/softwareproject/inventory_management/MainMenu.fxml"));
            scene = loader.load();
            stage.setScene(new Scene(scene, 1000, 600));
            stage.show();
        }

    }

    /**
     * This method initializes when first opening the screen.
     * Sets up the part table that populates data from the main menu.
     * Sets up the associated parts table.
     * Also handles the search boxes for the parts table.
     * The user may search using the ID or the name. The method filters the parts based on what is entered.
     * There was an error where if the user entered data that wasn't found in the table, the table displayed nothing.
     * Added a dialog box that pops up informing the user that the part not found.
     *
     * @param url url
     * @param resourceBundle resourceBundle
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        addProductSearchTxt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (addProductSearchTxt == null) {
                    partTableView.setItems(Inventory.getAllParts());
                }
                try {
                    if (!(Inventory.getAllParts().contains(Inventory.lookupPart(Integer.parseInt(addProductSearchTxt.getText()))))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Part ID Not Found");
                        alert.showAndWait();
                    }
                    else {
                        partTableView.setItems(FXCollections.observableArrayList(Inventory.lookupPart(Integer.parseInt(addProductSearchTxt.getText()))));
                    }
                }
                catch (NumberFormatException e) {
                    if (Inventory.lookupPart(addProductSearchTxt.getText()).isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("No Part Found");
                        alert.showAndWait();
                    }
                    else  {
                        partTableView.setItems(Inventory.lookupPart(addProductSearchTxt.getText()));
                    }
                }
            }
        });

        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartTableView.setItems(associatedParts);
    }
}
