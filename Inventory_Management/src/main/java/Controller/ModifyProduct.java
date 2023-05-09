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
 * This class displays and controls the Modify Product Screen.
 * The user can enter or change data into the fields. The user may also add or remove parts associated with the product and then
 * update a product.
 *
 * @author Zamir Rizvi
 * */
public class ModifyProduct implements Initializable {


    /** Stage field*/
    Stage stage;

    /** Parent field*/
    Parent scene;

    /** private Observable List of associated parts*/
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** private in field for product index*/
    private int productIndex;

    /** Product object*/
    Product product;
    @FXML
    public TextField modProductSearchTxt;
    @FXML
    public TextField modProductIdTxt;
    @FXML
    public TextField modProductNameTxt;
    @FXML
    public TextField modProductInvTxt;
    @FXML
    public TextField modProductPriceTxt;
    @FXML
    public TextField modProductMaxTxt;
    @FXML
    public TextField modProductMinTxt;

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
        Part SP = partsTableView.getSelectionModel().getSelectedItem();
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
     * This method updates and saves the Product and navigates back to the Main Menu.
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
            int id = product.getId();
            String name = modProductNameTxt.getText();
            double price = Double.parseDouble(modProductPriceTxt.getText());
            int stock = Integer.parseInt(modProductInvTxt.getText());
            int min = Integer.parseInt(modProductMinTxt.getText());
            int max = Integer.parseInt(modProductMaxTxt.getText());

            if (!(min <= stock && stock <= max)) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Error: Minimum inventory must be less than or equal to the stock inventory " +
                        "and stock inventory must be less than or equal to maximum inventory.");
                alert.showAndWait();
                return;
            }

            Product updateProduct = new Product(id, name, price, stock, min, max);
            for (Part associatePart : associatedParts) {
                updateProduct.addAssociatedPart(associatePart);
            }
            Inventory.updateProduct(productIndex, updateProduct);
            //this at end to go back to main
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/softwareproject/inventory_management/MainMenu.fxml"));
            scene = loader.load();
            stage.setScene(new Scene(scene, 1000, 600));
            stage.show();
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Please enter valid value for each Field");
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
     * Method to receive the selected Product from the Main Menu.
     * Populates the data in the fields and the associated parts in the table.
     * There was an error with the associated parts table where when modifying it, all the associated parts were removed.
     * Fixed it by implementing a for loop to get each associated part.
     *
     * @param product selected Product
     * */
    public void receiveProduct(Product product) {
        this.product = product;
        productIndex = Inventory.getAllProducts().indexOf(product);
        modProductIdTxt.setText(String.valueOf(product.getId()));
        modProductNameTxt.setText(product.getName());
        modProductInvTxt.setText(String.valueOf(product.getStock()));
        modProductPriceTxt.setText(String.valueOf(product.getPrice()));
        modProductMaxTxt.setText(String.valueOf(product.getMax()));
        modProductMinTxt.setText(String.valueOf(product.getMin()));

        for (Part SP: product.getAllAssociatedParts()) {
            associatedParts.add(SP);
        }
        associatedPartTableView.setItems(associatedParts);
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
        partsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        modProductSearchTxt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (modProductSearchTxt == null) {
                    partsTableView.setItems(Inventory.getAllParts());
                }
                try {
                    if (!(Inventory.getAllParts().contains(Inventory.lookupPart(Integer.parseInt(modProductSearchTxt.getText()))))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Part ID Not Found");
                        alert.showAndWait();
                    }
                    else {
                        partsTableView.setItems(FXCollections.observableArrayList(Inventory.lookupPart(Integer.parseInt(modProductSearchTxt.getText()))));
                    }
                }
                catch (NumberFormatException e) {
                    if (Inventory.lookupPart(modProductSearchTxt.getText()).isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("No Part Found");
                        alert.showAndWait();
                    }
                    else  {
                        partsTableView.setItems(Inventory.lookupPart(modProductSearchTxt.getText()));
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
