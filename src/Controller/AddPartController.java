package Controller;

import Model.*;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for AddPart view.
 */

public class AddPartController implements Initializable {
    @FXML private TextField idTextBox;
    @FXML private TextField nameTextBox;
    @FXML private TextField priceTextBox;
    @FXML private TextField stockTextBox;
    @FXML private TextField minTextBox;
    @FXML private TextField maxTextBox;
    @FXML private TextField uniqueTextBox;
    @FXML private Label uniqueLabel;
    @FXML private RadioButton inhouseRadio;
    @FXML private RadioButton outsourcedRadio;
    @FXML private Label errorMessage;

    /**
     * Initializes the controller.
     * @param url - possible url location for root object if provided, null if not needed.
     * @param resourceBundle - possible resources for root object, null if not needed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int tempId = SharedData.getPartId();
        idTextBox.setText(String.valueOf(tempId));
    }
    /**
     * Changes the user interface to account of if the part is InHouse or Outsourced based on radio button selection.
     */
    public void partTypeRadioControl(){
        if(inhouseRadio.isSelected()){uniqueLabel.setText("Machine ID");}
        else uniqueLabel.setText("Company Name");
    }
    /**
     * This method returns the user to the Main view without adding the part.
     * @param event - the cancel button is pressed
     * @throws Exception from FXMLLoader.
     */
    public void cancelAddPartTrigger(ActionEvent event) throws Exception {
        SharedData.decreasePartId();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Main.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * This method returns the user to the Main view after adding the part if the information is valid.
     * @param event - the save button is pressed
     * @throws Exception from FXMLLoader.
     */
    public void commitAddPartTrigger(ActionEvent event) throws Exception {
        if (SharedData.validatePart(idTextBox.getText(),nameTextBox.getText(), priceTextBox.getText(),stockTextBox.getText(),minTextBox.getText(),
                maxTextBox.getText(), uniqueTextBox.getText(), inhouseRadio.isSelected())){
            createAndAddNewPart();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Main.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();}
        else {sendErrorMessage(SharedData.partValidationError(idTextBox.getText(),nameTextBox.getText(), priceTextBox.getText(),stockTextBox.getText(),minTextBox.getText(),
                maxTextBox.getText(), uniqueTextBox.getText(), inhouseRadio.isSelected()));}
    }

    /**
     * This method creates the new part and adds it to the Inventory list of parts.
     */

    private void createAndAddNewPart(){
        int id = Integer.parseInt(idTextBox.getText());
        String name = nameTextBox.getText();
        int stock = Integer.parseInt(stockTextBox.getText());
        double price = Double.parseDouble(priceTextBox.getText());
        int min = Integer.parseInt(minTextBox.getText());
        int max = Integer.parseInt(maxTextBox.getText());
        Part part;
        if(outsourcedRadio.isSelected()){
            String companyName = uniqueTextBox.getText();
            part = new Outsourced(id,name,price,stock,min,max,companyName);
        }
        else{
            int machineId = Integer.parseInt(uniqueTextBox.getText());
            part = new InHouse(id,name,price, stock,min,max,machineId);
        }
        Inventory.addPart(part);
    }
    /**
     * This is the method called to display an error message to the user for 6 seconds
     * @param message - contains the error message to be displayed to the user.
     */
    private void sendErrorMessage(String message){
        errorMessage.setText(message);
        errorMessage.setVisible(true);
        PauseTransition visibleError = new PauseTransition(Duration.seconds(6));
        visibleError.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                errorMessage.setVisible(false);
            }
        });
        visibleError.play();
    }
}
