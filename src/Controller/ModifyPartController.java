package Controller;

import Model.*;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
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
 * Controller for ModifyPart view.
 */

public class ModifyPartController implements Initializable {
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
    public void initialize(URL url, ResourceBundle resourceBundle){
        Part currentPart = Inventory.getAllParts().get(SharedData.getSelectedPartIndex());
        try{
            displayCurrentPartAttributes((InHouse) currentPart);
        } catch (Exception e) {
            displayCurrentPartAttributes((Outsourced) currentPart);
        }
    }

    /**
     * This method displays the current part information if the part is an InHouse part.
     * @param currentPart - part that is being modified as it was before modification
     */

    private void displayCurrentPartAttributes(InHouse currentPart){
        inhouseRadio.setSelected(true);
        idTextBox.setText(String.valueOf(currentPart.getId()));
        nameTextBox.setText(currentPart.getName());
        priceTextBox.setText(String.valueOf(currentPart.getPrice()));
        stockTextBox.setText(String.valueOf(currentPart.getStock()));
        minTextBox.setText(String.valueOf(currentPart.getMin()));
        maxTextBox.setText(String.valueOf(currentPart.getMax()));
        uniqueTextBox.setText(String.valueOf(currentPart.getMachineId()));
        uniqueLabel.setText("Machine Id");
    }

    /**
     * This method displays the current part information if the part is an Outsourced part.
     * @param currentPart - part that is being modified as it was before modification
     */

    private void displayCurrentPartAttributes(Outsourced currentPart){
        outsourcedRadio.setSelected(true);
        idTextBox.setText(String.valueOf(currentPart.getId()));
        nameTextBox.setText(currentPart.getName());
        priceTextBox.setText(String.valueOf(currentPart.getPrice()));
        stockTextBox.setText(String.valueOf(currentPart.getStock()));
        minTextBox.setText(String.valueOf(currentPart.getMin()));
        maxTextBox.setText(String.valueOf(currentPart.getMax()));
        uniqueTextBox.setText(String.valueOf(currentPart.getCompanyName()));
        uniqueLabel.setText("Company Name");
    }

    /**
     * Changes the user interface to account of if the part is InHouse or Outsourced based on radio button selection.
     */
    public void partTypeRadioControl(){
        if(inhouseRadio.isSelected()){uniqueLabel.setText("Machine ID");}
        else uniqueLabel.setText("Company Name");
    }
    /**
     * This method returns the user to the Main view without making modifications.
     * @param event - the cancel button is pressed
     * @throws Exception from FXMLLoader.
     */
    public void cancelModifyPartTrigger(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Main.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * This method updates the list of parts in inventory with changes made if changes are valid.
     * @param event - the save button is pressed
     * @throws Exception from FXMLLoader.
     */
    public void commitModifyPartTrigger(ActionEvent event) throws Exception{
        if(SharedData.validatePart(idTextBox.getText(), nameTextBox.getText(), priceTextBox.getText(),
                stockTextBox.getText(), minTextBox.getText(), maxTextBox.getText(), uniqueTextBox.getText(),
                inhouseRadio.isSelected())){
        Inventory.updatePart(SharedData.getSelectedPartIndex(),compilePart());
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Main.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();}
        else {sendErrorMessage(SharedData.partValidationError(idTextBox.getText(),nameTextBox.getText(), priceTextBox.getText(),stockTextBox.getText(),minTextBox.getText(),
                maxTextBox.getText(), uniqueTextBox.getText(), inhouseRadio.isSelected()));}
    }

    /**
     * This method compiles all the user supplied input to make a new part
     * @return new part based on user input
     */

    private Part compilePart(){
        Part newPart;
        int id = Integer.parseInt(idTextBox.getText());
        String name = nameTextBox.getText();
        int stock = Integer.parseInt(stockTextBox.getText());
        int min = Integer.parseInt(minTextBox.getText());
        int max = Integer.parseInt(maxTextBox.getText());
        double price = Double.parseDouble(priceTextBox.getText());
        if(inhouseRadio.isSelected()){
            int machineId = Integer.parseInt(uniqueTextBox.getText());
            newPart = new InHouse(id,name,price,stock,min,max,machineId);
        }
        else{
            String companyName = uniqueTextBox.getText();
            newPart = new Outsourced(id,name,price,stock,min,max,companyName);
        }
        return newPart;
    }
    /**
     * This is the method called to display an error message to the user for 6 seconds
     * @param message - contains the error message to be displayed to the user.
     */
    private void sendErrorMessage(String message){
        errorMessage.setText(message);
        errorMessage.setVisible(true);
        PauseTransition visibleError = new PauseTransition(Duration.seconds(6));
        visibleError.setOnFinished(event -> errorMessage.setVisible(false));
        visibleError.play();
    }
}
