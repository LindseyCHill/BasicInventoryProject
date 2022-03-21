package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import Model.SharedData;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for ModifyProduct view.
 */

public class ModifyProductController implements Initializable {

    @FXML
    private TextField idTextBox;
    @FXML private TextField nameTextBox;
    @FXML private TextField priceTextBox;
    @FXML private TextField stockTextBox;
    @FXML private TextField minTextBox;
    @FXML private TextField maxTextBox;
    @FXML private TableView<Part> productPartTableView;
    @FXML private TableColumn<Part,Integer> partIdClm;
    @FXML private TableColumn<Part, String> partNameClm;
    @FXML private TableColumn<Part, Double> partPriceClm;
    @FXML private TableColumn<Part, Integer> partStockClm;
    @FXML private TableView<Part> includedPartTableView;
    @FXML private TableColumn<Part,Integer> includedPartIdClm;
    @FXML private TableColumn<Part, String> includedPartNameClm;
    @FXML private TableColumn<Part, Double> includedPartPriceClm;
    @FXML private TableColumn<Part, Integer> includedPartStockClm;
    @FXML private TextField searchPart;
    @FXML private Label errorMessage;
    /**
     * This is a list of parts to display in the part table
     */
    public ObservableList<Part> displayPartList = FXCollections.observableArrayList();
    /**
     * This is a list of parts to display in the associated parts table
     */
    public ObservableList<Part> includedPartList = FXCollections.observableArrayList();

    /**
     * Initializes the controller and populates tables.
     * @param url - possible url location for root object if provided, null if not needed.
     * @param resourceBundle - possible resources for root object, null if not needed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    Product product = Inventory.getAllProducts().get(SharedData.getSelectedProductIndex());
    idTextBox.setText(String.valueOf(product.getId()));
    nameTextBox.setText(product.getName());
    stockTextBox.setText(String.valueOf(product.getStock()));
    minTextBox.setText(String.valueOf(product.getMin()));
    maxTextBox.setText(String.valueOf(product.getMax()));
    priceTextBox.setText(String.valueOf(product.getPrice()));
    partIdClm.setCellValueFactory(new PropertyValueFactory<>("id"));
    partNameClm.setCellValueFactory(new PropertyValueFactory<>("name"));
    partPriceClm.setCellValueFactory(new PropertyValueFactory<>("price"));
    partStockClm.setCellValueFactory(new PropertyValueFactory<>("stock"));
    includedPartIdClm.setCellValueFactory(new PropertyValueFactory<>("id"));
    includedPartNameClm.setCellValueFactory(new PropertyValueFactory<>("name"));
    includedPartPriceClm.setCellValueFactory(new PropertyValueFactory<>("price"));
    includedPartStockClm.setCellValueFactory(new PropertyValueFactory<>("stock"));
    displayPartList = FXCollections.observableArrayList(Inventory.getAllParts());
    includedPartList = FXCollections.observableArrayList(product.getAllAssociatedParts());
    productPartTableView.setItems(displayPartList);
    includedPartTableView.setItems(includedPartList);
    }
    /**
     * This method returns the user to the Main view without making modifications.
     * @param event - the cancel button is pressed
     * @throws Exception from FXMLLoader.
     */
    public void cancelModifyProductTrigger(ActionEvent event) throws Exception {
        SharedData.decreaseProductId();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Main.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method updates the list of products in inventory with changes made if changes are valid.
     * @param event - the save button is pressed
     * @throws Exception from FXMLLoader.
     */

    public void commitModifyProductTrigger(ActionEvent event) throws Exception{
        if(SharedData.validateProduct(idTextBox.getText(),nameTextBox.getText(),priceTextBox.getText(), stockTextBox.getText(),
                minTextBox.getText(),maxTextBox.getText())){
        createAndAddNewProduct();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Main.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();}
        else sendErrorMessage(SharedData.productValidationError(idTextBox.getText(),nameTextBox.getText(),
                priceTextBox.getText(),stockTextBox.getText(), minTextBox.getText(), maxTextBox.getText()));
    }

    /**
     * This method creates the new product with modifications made, so it can replace the product in the Inventory list of products.
     */

    private void createAndAddNewProduct(){
        int id = Integer.parseInt(idTextBox.getText());
        String name = nameTextBox.getText();
        int stock = Integer.parseInt(stockTextBox.getText());
        double price = Double.parseDouble(priceTextBox.getText());
        int min = Integer.parseInt(minTextBox.getText());
        int max = Integer.parseInt(maxTextBox.getText());
        Product product = new Product(id,name,price,stock,min,max);
        if(!includedPartList.isEmpty()){
            int i = 0;
            while(i<includedPartList.size()){
                product.addAssociatedPart(includedPartList.get(i));
                i++;}}
        Inventory.updateProduct(SharedData.getSelectedProductIndex(), product);
    }

    /**
     * This method changes the list of parts displayed based on user input into the search bar.
     */

    public void searchPartFunction(){
        displayPartList.clear();
        if(searchPart.getText().isBlank()) displayPartList = FXCollections.observableArrayList(Inventory.getAllParts());
        else {String searchString;
            searchString = String.valueOf(searchPart.getText());
            displayPartList.clear();
            displayPartList = Inventory.lookupPart(searchString);
            productPartTableView.refresh();}
        productPartTableView.setItems(displayPartList);
        productPartTableView.refresh();
        if(displayPartList.isEmpty()){sendErrorMessage("No parts exist with that search criteria. Please try again.");}
    }

    /**
     * This method adds an associated part to the list of associated parts.
     */

    public void addPartToIncludedList(){
        includedPartList.add(productPartTableView.getSelectionModel().getSelectedItem());
        includedPartTableView.setItems(includedPartList);
        includedPartTableView.refresh();
    }

    /**
     * This method removes an associated part from the list of associated parts.
     */

    public void removePartFromIncludedList(){
        includedPartList.remove(includedPartTableView.getSelectionModel().getSelectedItem());
        includedPartTableView.setItems(includedPartList);
        includedPartTableView.refresh();
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
