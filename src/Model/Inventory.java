package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the inventory class that provides persistent data to model the inventory of both parts and products.
 */

public class Inventory {
    /**
     * This is a list of all the parts in the inventory, both in-house and outsourced parts.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * This is a list of all the products in the inventory.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * This method adds a part to the list of parts in the inventory.
     * @param newPart is the new part being added to the list of parts. The new part can have a class of InHouse or Outsourced.
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * This method adds a product to the list of products in the inventory
     * @param newProduct is the new product being added to the list of products.
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * This method is to look up a part by its ID attribute in the list of parts.
     * @param partId is the id number to compare with the id numbers of the parts in the list of parts
     * @return The method returns the part that has the id matching the id parameter.
     */
    public static Part lookupPart(int partId){
        Part matchingpart = null;
        int s = allParts.size();
        while(s>0){
            if (allParts.get(s).getId()==partId) {matchingpart = allParts.get(s);}
            s--;
        }
        return matchingpart;
    }

    /**
     * RUNTIME ERROR: I encountered an error while creating this project when trying to parse a number from a string.
     * If the string contained both letters and numbers, I would get an error. I fixed this error by formatting any string with numbers
     * so that every character that was not a number was replaced with an empty string. This allowed me to parse out the numbers to
     * use in a search even if the user typed in both letters and numbers.
     *
     * This method is to look up a part by comparing if its name contains a specific string.
     * @param partName is a string to compare with the part list names to see which parts contain the string.
     * @return The method returns an array list of parts with names that contain the string in the
     * form of an observable list.
     */
    public static ObservableList lookupPart(String partName){
        ObservableList<Part> lookupParts = FXCollections.observableArrayList();
        int s = 0;
        boolean partNameMayBeId = false;
        int i = 0;
        while (i<10){
            if (partName.contains(String.valueOf(i))) {
                partNameMayBeId = true;
                break;}
            i++;
        }
        while(s<allParts.size()){
            if(allParts.get(s).getName().toLowerCase().contains(partName.toLowerCase()))lookupParts.add(allParts.get(s));
            else if(partNameMayBeId&&allParts.get(s).getId()==Integer.parseInt(partName.replaceAll("[\\D]", ""))){
                lookupParts.add(allParts.get(s));}
            s++;}
        return lookupParts;
    }
    /**
     * This method is to look up a product by its ID attribute in the list of parts.
     * @param productId is the id number to compare with the id numbers of the products in the list of products
     * @return The method returns the product that has the id matching the id parameter.
     */
    public static Product lookupProduct(int productId){
        Product matchingProduct = null;
        int s = allProducts.size();
        while(s>0){
            if (allProducts.get(s).getId()==productId) {matchingProduct = allProducts.get(s);}
            s--;
        }
        return matchingProduct;
    }
    /**
     * This method is to look up a product by comparing if its name contains a specific string.
     * @param productName is a string to compare with the product list names to see which products contain the string.
     * @return The method returns an array list of products with names that contain the string in the
     * form of an observable list.
     */
    public static ObservableList lookupProduct(String productName){
        ObservableList<Product> lookupProducts = FXCollections.observableArrayList();
        int s = 0;
        boolean productNameMayBeId = false;
        int i = 0;
        while (i<10){
            if (productName.contains(String.valueOf(i))) {
                productNameMayBeId = true;
                break;}
            i++;
        }
        while(s<allProducts.size()){
            if(allProducts.get(s).getName().toLowerCase().contains(productName.toLowerCase()))lookupProducts.add(allProducts.get(s));
            else if(productNameMayBeId&&allProducts.get(s).getId()==Integer.parseInt(productName.replaceAll("[\\D]", ""))){
                lookupProducts.add(allProducts.get(s));}
            s++;}
        return lookupProducts;
    }

    /**
     * This method updates attributes of a part by replacing the part.
     * @param index - the index of the part that is being updated, so it can be deleted
     * @param selectedPart - the new part replacing the previous part.
     */
    public static void updatePart (int index, Part selectedPart){
        allParts.remove(index);
        allParts.add(selectedPart);
    }

    /**
     * This method updates attributes of a product by replacing the product.
     * @param index - the index of the product that is being updated, so it can be deleted
     * @param newProduct - the new product replacing the previous product.
     */

    public static void updateProduct(int index, Product newProduct){
        allProducts.remove(index);
        allProducts.add(newProduct);
    }

    /**
     * This method removes a part from the list of parts.
     * @param selectedPart the part to be deleted
     * @return a boolean that confirms if the part was deleted
     */

    public static boolean deletePart(Part selectedPart){
        boolean deletable = getAllParts().contains(selectedPart);
        if(deletable) getAllParts().remove(selectedPart);
        return deletable;
    }

    /**
     * This method removes a product from the list of products.
     * @param selectedProduct the product to be deleted
     * @return a boolean that confirms if the product was deleted
     */

    public static boolean deleteProduct(Product selectedProduct){
        boolean deletable = getAllProducts().contains(selectedProduct);
        if(deletable) getAllProducts().remove(selectedProduct);
        return deletable;
    }

    /**
     * This method returns an array list of parts in the form of an observable list.
     * @return list of all parts in inventory
     */

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This method returns an array list of products in the form of an observable list.
     * @return list of all products in inventory
     */

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}

