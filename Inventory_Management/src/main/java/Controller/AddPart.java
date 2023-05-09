package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * This class displays and controls the Add Part Screen.
 * Navigate to this page from the Main Menu. This class allows the user to enter fields to create a new Part.
 *
 * @author Zamir Rizvi
 * */

public class AddPart implements Initializable {

    /** public Label field*/
    public Label partProperty;

    /** Stage field*/
    Stage stage;

    /** Parent field*/
    Parent scene;
    @FXML
    public RadioButton addPartInHouseRbtn;
    @FXML
    public RadioButton addPartOutRbtn;
    @FXML
    public TextField addPartIdTxt;
    @FXML
    public TextField addPartNameTxt;
    @FXML
    public TextField addPartInvTxt;
    @FXML
    public TextField addPartPriceTxt;
    @FXML
    public TextField addPartMaxTxt;
    @FXML
    public TextField addPartMinTxt;
    @FXML
    public TextField addPartMachineTxt;

    /** This method changes the part property to In House if the radio button is selected.
     *
     * @param actionEvent action Event
     * */
    @FXML
    public void onActionInHouse(ActionEvent actionEvent) {
        partProperty.setText("In House");
    }

    /** This method changes the part property to Outsourced if the radio button is selected
     *
     * @param actionEvent action Event
     * */
    @FXML
    public void onActionOutsourced(ActionEvent actionEvent) {
        partProperty.setText("Outsourced");
    }

    /**
     * This method saves the Part and navigates back to the Main Menu.
     * The ID is unique and auto generated. The user can enter data into the fields and save the part.
     * There was a runtime error: NullPointerException that occurred when you tried to save without having data in the fields.
     * Added a dialog box that informs the user to check the fields.
     * There was also another runtime error: NumberFormatError that occurred when you tried to enter the wrong type of data into the fields.
     * Added a dialog box that informs the user to check the fields.
     * Added a logic check to the inventory levels in the form of min <= stock <= max.
     *
     * @param event event
     * */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        try {
            int id = Inventory.uniquePartId += 2;
            String name = addPartNameTxt.getText();
            int stock = Integer.parseInt(addPartInvTxt.getText());
            double price = Double.parseDouble(addPartPriceTxt.getText());
            int min = Integer.parseInt(addPartMinTxt.getText());
            int max = Integer.parseInt(addPartMaxTxt.getText());

            if (!(min <= stock && stock <= max)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Error: Minimum inventory must be less than or equal to the stock inventory " +
                        "and stock inventory must be less than or equal to maximum inventory.");
                alert.showAndWait();
                return;
            }

            int machineId = 0;
            if (addPartInHouseRbtn.isSelected()) {
                try {
                    machineId = Integer.parseInt(addPartMachineTxt.getText());
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setContentText("Machine ID must be an integer");
                    alert.showAndWait();
                    return;
                }

                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId) {
                });
            }

            if (addPartOutRbtn.isSelected()) {
                String companyName = addPartMachineTxt.getText();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName) {
                });
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/softwareproject/inventory_management/MainMenu.fxml"));
            scene = loader.load();
            stage.setScene(new Scene(scene, 1000, 600));
            stage.show();
        }

        catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Please enter valid value for each field");
            alert.showAndWait();
        }
    }

    /**
     * This method cancels the part creation and navigates back to the main menu.
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
     * Initialize method when the screen is first activated
     *
     * @param url url
     * @param resourceBundle resourceBundle*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
