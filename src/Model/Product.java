package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the product class that models a product and can contain associated parts.
 */
public class Product {
    /**
     * This is a list of all the parts associated with the product, retained in an observable list
     */
    private ObservableList<Part> associatedParts;
    /**
     * This is the ID number assigned to the product in an int.
     */
    private int id;
    /**
     * This is the name of the product in a string.
     */
    private String name;
    /**
     * This is the price of the product in a double.
     */
    private double price;
    /**
     * This is the stock or amount of the product that is in inventory. The number is an int.
     */
    private int stock;
    /**
     * This is the minimum amount of the product that should be in inventory, as an int.
     */
    private int min;
    /**
     * This is the maximum amount of the product that should be in inventory, as an int.
     */
    private int max;

    /**
     * This is the contructor for an instance of a product.
     * @param id the ID int number assigned to the product
     * @param name a string containing the name of the product
     * @param price a double containing the price of the product
     * @param stock an int containing the number of the product that is in stock
     * @param min an int containing the minimum amount of this product that should be in stock
     * @param max an int containing the maximum amount of this product that should be in stock
     */

    public Product( int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = FXCollections.observableArrayList();
    }

    /**
     * getter for the list of associated parts with an instance of a product
     * @return a list of associated parts in the form of an observable list
     */

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * adds an associated part to the list
     * @param part: the part being added to the list of associated parts
     */

    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    /**
     * removes an associated part from the list of associated parts
     * @param part: part to be removed
     */

    public void deleteAssociatedPart(Part part) {
        this.associatedParts.remove(part);
    }

    /**
     * getter for the int ID of the product
     * @return ID of the product as an int
     */

    public int getId() {
        return id;
    }

    /**
     * setter for a product ID number
     * @param id: ID number for a product as an int
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the name of the product
     * @return: String containing the name of the product
     */

    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the product
     * @param name: String containing the name of the product
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the price of the product
     * @return the product price in a double
     */

    public double getPrice() {
        return price;
    }

    /**
     * Setter for the price of the product
     * @param price: double containing the price of the product
     */

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *  Getter for stock of the product, or how much is in inventory.
     * @return the product stock amount in an int
     */

    public int getStock() {
        return stock;
    }

    /**
     * Setter for the amount of stock of the product is in inventory.
     * @param stock: an int for the stock amount of the product exists in inventory
     */

    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Getter for the minimum amount of stock of a product.
     * @return minimum stock of a product as an int
     */

    public int getMin() {
        return min;
    }

    /**
     * Setter for the minimum amount of stock of a product.
     * @param min: an int for the minimum amount of stock of a product
     */

    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Getter for the maximum amount of stock of a product.
     * @return maximum stock of a product as an int
     */

    public int getMax() {
        return max;
    }

    /**
     * Setter for the maximum amount of stock of a product.
     * @param max: an int for the maximum amount of stock of a product
     */

    public void setMax(int max) {
        this.max = max;
    }
}
