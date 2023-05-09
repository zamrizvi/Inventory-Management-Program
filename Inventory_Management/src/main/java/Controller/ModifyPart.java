package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
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
 * This class displays and controls the Modify Part Screen.
 * Navigate to this page from the Main Menu. This class allow the user to change and update the fields of a selected Part.
 *
 * @author Zamir Rizvi
 * */
public class ModifyPart implements Initializable {


    /** public Label field*/
    public Label modPartProperty;

    /** Stage field*/
    Stage stage;

    /** Parent field*/
    Parent scene;
    @FXML
    public RadioButton modPartInHouseRbtn;
    @FXML
    public RadioButton modPartOutRbtn;
    @FXML
    public TextField modPartIdTxt;
    @FXML
    public TextField modPartNameTxt;
    @FXML
    public TextField modPartInvTxt;
    @FXML
    public TextField modPartPriceTxt;
    @FXML
    public TextField modPartMaxTxt;
    @FXML
    public TextField modPartMinTxt;
    @FXML
    public TextField modPartMachineTxt;

    /** Part object field*/
    Part part;

    /** Private int field for part index*/
    private int partIndex;

    /** This method changes the part property to In House if the radio button is selected.
     *
     * @param actionEvent action Event
     * */
    public void onActionModInHouse(ActionEvent actionEvent) {
        modPartProperty.setText("In House");
    }

    /** This method changes the part property to Outsourced if the radio button is selected
     *
     * @param actionEvent action Event
     * */
    public void onActionModOutsourced(ActionEvent actionEvent) {
        modPartProperty.setText("Outsourced");
    }

    /**
     * This method saves the Part and navigates back to the Main Menu.
     * The ID is unique and auto generated. The user can change and enter data into the fields and save the part.
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
            int id = part.getId();
            String name = modPartNameTxt.getText();
            int stock = Integer.parseInt(modPartInvTxt.getText());
            double price = Double.parseDouble(modPartPriceTxt.getText());
            int min = Integer.parseInt(modPartMinTxt.getText());
            int max = Integer.parseInt(modPartMaxTxt.getText());

            if (!(min <= stock && stock <= max)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Error: Minimum inventory must be less than or equal to the stock inventory " +
                        "and stock inventory must be less than or equal to maximum inventory.");
                alert.showAndWait();
                return;
            }

            int machineId = 0;
            if (modPartInHouseRbtn.isSelected()) {
                try {
                    machineId = Integer.parseInt(modPartMachineTxt.getText());
                } catch (NumberFormatException e) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setContentText("Machine ID must be an integer");
                    alert.showAndWait();
                    return;
                }

                InHouse modInHouse = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.updatePart(partIndex, modInHouse);
            }
            if (modPartOutRbtn.isSelected()) {
                String companyName = modPartMachineTxt.getText();

                Outsourced modOutSourced = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.updatePart(partIndex, modOutSourced);
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/softwareproject/inventory_management/MainMenu.fxml"));
            scene = loader.load();
            stage.setScene(new Scene(scene, 1000, 600));
            stage.show();
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Please enter valid values in the fields");
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
     * Method to receive the part data from the Main Menu.
     * Populated the fields with the Part data. Automatically selects the correct radio button that corresponds to the part property.
     *
     * @param part selected part
     * */
    public void receivePart(Part part) {

        this.part = part;
        partIndex = Inventory.getAllParts().indexOf(part);
        modPartIdTxt.setText(String.valueOf(part.getId()));
        modPartNameTxt.setText(part.getName());
        modPartPriceTxt.setText(String.valueOf(part.getPrice()));
        modPartInvTxt.setText(String.valueOf(part.getStock()));
        modPartMinTxt.setText(String.valueOf(part.getMin()));
        modPartMaxTxt.setText(String.valueOf(part.getMax()));

        if (part instanceof InHouse) {
            modPartInHouseRbtn.setSelected(true);
            modPartProperty.setText("In House");
            modPartMachineTxt.setText(String.valueOf(((InHouse)part).getMachineId()));
        }
        if (part instanceof Outsourced) {
            modPartOutRbtn.setSelected(true);
            modPartProperty.setText("Outsourced");
            modPartMachineTxt.setText(((Outsourced)part).getCompanyName());
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

