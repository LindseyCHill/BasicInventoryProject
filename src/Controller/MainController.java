package Controller;

import Model.*;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is the controller for the main screen of the application.
 */

public class MainController implements Initializable {
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part,Integer> partIdClm;
    @FXML private TableColumn<Part, String> partNameClm;
    @FXML private TableColumn<Part, Double> partPriceClm;
    @FXML private TableColumn<Part, Integer> partStockClm;
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product,Integer> productIdClm;
    @FXML private TableColumn<Product, String> productNameClm;
    @FXML private TableColumn<Product, Double> productPriceClm;
    @FXML private TableColumn<Product, Integer> productStockClm;
    @FXML private TextField searchPart;
    @FXML private TextField searchProduct;
    @FXML private Label MainErrorMessage;
    /**
     * This is a list of parts to display in the part table
     */
    public ObservableList<Part> displyPartList;
    /**
     * This is a list of parts to display in the product table
     */
    public ObservableList<Product> displyProductList;

    /**
     * Initializes the controller and populates tables.
     * @param url - possible url location for root object if provided, null if not needed.
     * @param resourceBundle - possible resources for root object, null if not needed.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIdClm.setCellValueFactory(new PropertyValueFactory<Part,Integer>("id"));
        partNameClm.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
        partPriceClm.setCellValueFactory(new PropertyValueFactory<Part,Double>("price"));
        partStockClm.setCellValueFactory(new PropertyValueFactory<Part,Integer>("stock"));
        productIdClm.setCellValueFactory(new PropertyValueFactory<Product,Integer>("id"));
        productNameClm.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
        productPriceClm.setCellValueFactory(new PropertyValueFactory<Product,Double>("price"));
        productStockClm.setCellValueFactory(new PropertyValueFactory<Product,Integer>("stock"));
        if(SharedData.isFirstCreate())addTestData();
        displyPartList = FXCollections.observableArrayList(Inventory.getAllParts());
        partTableView.setItems(displyPartList);
        displyProductList = FXCollections.observableArrayList(Inventory.getAllProducts());
        productTableView.setItems(displyProductList);
    }

    /**
     * This method adds text data to the Inventory the first time the application is launched.
     */

    public void addTestData(){
        Inventory.addPart(new InHouse(SharedData.getPartId(),"Ryzen 9",498.99,5,1,20,123));
        Inventory.addPart(new InHouse(SharedData.getPartId(),"Ryzen 7",328.99,11,1,20,123));
        Inventory.addPart(new Outsourced(SharedData.getPartId(),"i9",487.99,7,1,20,"Intel"));
        Inventory.addPart(new Outsourced(SharedData.getPartId(),"i7",386.99,12,1,20,"Intel"));
        Inventory.addPart(new InHouse(SharedData.getPartId(),"Motherboard",175.99,18,1,20,456));
        Inventory.addProduct(new Product(SharedData.getProductId(),"AMD Computer Med",849.99,5,1,20));
        Inventory.getAllProducts().get(0).addAssociatedPart(Inventory.getAllParts().get(0));
        Inventory.addProduct(new Product(SharedData.getProductId(),"Intel Computer Med",826.99,4,1,20));
        Inventory.addProduct(new Product(SharedData.getProductId(),"AMD Computer High",646.99,10,1,20));
        Inventory.addProduct(new Product(SharedData.getProductId(),"Intel Computer High",726.99,12,1,20));
    }

    /**
     * This method changes the list of parts displayed in the table based on user input into a search bar.
     */

    public void searchPartFunction(){
        displyPartList.clear();
        if(searchPart.getText().isBlank())displyPartList = FXCollections.observableArrayList(Inventory.getAllParts());
        else {String searchString;
            searchString = String.valueOf(searchPart.getText());
            displyPartList.clear();
            displyPartList = Inventory.lookupPart(searchString);
            if(displyPartList.isEmpty()){sendErrorMessage("No parts exist with that search criteria. Please try again.");}
            partTableView.refresh();}
        partTableView.setItems(displyPartList);
        partTableView.refresh();
    }

    /**
     * This method changes the list of products displayed in the table based on user input into a search bar.
     */

    public void searchProductFunction(){
        displyProductList.clear();
        if(searchProduct.getText().isBlank())displyProductList = FXCollections.observableArrayList(Inventory.getAllProducts());
        else {
            String searchString = String.valueOf(searchProduct.getText());
            displyProductList.clear();
            displyProductList = Inventory.lookupProduct(searchString);
            if(displyProductList.isEmpty()){sendErrorMessage("No products exist with that search criteria. Please try again.");}
            productTableView.refresh();
        }
        productTableView.setItems(displyProductList);
        productTableView.refresh();
    }

    /**
     * If a part is selected, this method generates a confirmation message to see if a part should be deleted.
     * The part is removed or not removed based on the user's answer to the confirmation.
     */

    public void deletePartTrigger() {
        if(!partTableView.getSelectionModel().isEmpty()){
            Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete "
                    + selectedPart.getName() + "?",ButtonType.YES,ButtonType.CANCEL);
            alert.setTitle("Delete Part");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()) {
                if(result.get() == ButtonType.YES)
                    if (Inventory.deletePart(selectedPart)){
                        displyPartList = FXCollections.observableArrayList(Inventory.getAllParts());
                        partTableView.setItems(displyPartList);
                        partTableView.refresh();}
                    else sendErrorMessage("Part not found");
            }
        }
        else{sendErrorMessage("Select a part to delete");}
    }

    /**
     * If a product is selected, this method generates a confirmation message to see if a product should be deleted.
     * The product is removed or not removed based on the user's answer to the confirmation.
     */

    public void deleteProductTrigger() {
        if(!productTableView.getSelectionModel().isEmpty() && productTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()){
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete "
                    + selectedProduct.getName() + "?",ButtonType.YES,ButtonType.CANCEL);
            alert.setTitle("Delete Product");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()) {
                if(result.get() == ButtonType.YES)
                    if (Inventory.deleteProduct(selectedProduct)) {
                        displyProductList = FXCollections.observableArrayList(Inventory.getAllProducts());
                        productTableView.setItems(displyProductList);
                        productTableView.refresh();}
            }
        }
        else{sendErrorMessage("Select a product with no associated parts to delete.");}
    }

    /**
     *Loads ModifyPart view if a part is selected.
     * @param event Modify button clicked on part side of interface
     * @throws Exception from FXMLLoader.
     */

    public void modifyPartTrigger(ActionEvent event) throws Exception{
        if(!partTableView.getSelectionModel().isEmpty()){
        SharedData.setSelectedPartIndex(partTableView.getSelectionModel().getSelectedItem());
        Parent root = FXMLLoader.load(getClass().getResource("../View/ModifyPart.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();}
        else{sendErrorMessage("Select a part to modify");}
    }

    /**
     *Loads ModifyProduct view if a product is selected.
     * @param event Modify button clicked on project side of interface
     * @throws Exception from FXMLLoader.
     */

    public void modifyProductTrigger(ActionEvent event) throws Exception{
        if(!productTableView.getSelectionModel().isEmpty()){
        SharedData.setSelectedProductIndex(productTableView.getSelectionModel().getSelectedItem());
        Parent root = FXMLLoader.load(getClass().getResource("../View/ModifyProduct.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();}
        else{sendErrorMessage("Select a product to modify");}
    }

    /**
     * Loads AddPart view.
     * @param event Add button clicked on part side of interface
     * @throws Exception from FXMLLoader.
     */

    public void addPartTrigger(ActionEvent event) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("../View/AddPart.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

    /**
     * Loads AddProduct view.
     * @param event Add button clicked on product side of interface
     * @throws Exception from FXMLLoader.
     */

    public void addProductTrigger(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/AddProduct.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * This is the method called to display an error message to the user for 6 seconds
     * @param message - contains the error message to be displayed to the user.
     */

    private void sendErrorMessage(String message){
        MainErrorMessage.setText(message);
        MainErrorMessage.setVisible(true);
        PauseTransition visibleError = new PauseTransition(Duration.seconds(6));
        visibleError.setOnFinished(event -> MainErrorMessage.setVisible(false));
        visibleError.play();
    }

    /**
     * This method exits the program.
     * @param event Exit button clicked.
     */
    @FXML
    public void exitButtonTrigger(ActionEvent event) {

        System.exit(0);
    }
}