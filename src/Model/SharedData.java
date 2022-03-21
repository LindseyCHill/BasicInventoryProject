package Model;

/**
 * The SharedData class is specifically to share data across multiple scenes, such as the id or index of a selected object
 * as well as to offer a central class location for methods that are used on multiple scenes, such as validation of an object's attributes.
 */

public class SharedData {
    /**
     * The partId is the next available id for a part to use.
     */
    private static int partId;
    /**
     * The productId is the next available id for a product to use.
     */
    private static int productId;
    /**
     * The selectedPartIndex is the index in the Inventory list of all parts that belongs to the part currently selected.
     */
    private static int selectedPartIndex;
    /**
     * The selectedProductIndex is the index in the Inventory list of all products that belongs to the product currently selected.
     */
    private static int selectedProductIndex;
    /**
     * The isFirstCreate boolean provides the answer to if the program is just opened up for the first time so test data
     * can be added but not added repeatedly.
     */
    private static boolean isFirstCreate = true;

    /**
     * The getPartId method is a method that returns the next available part id to be used when adding a new part.
     * The part id increases each time this method is called so no two parts will end up with the same id.
     * @return the next available part id for a new part.
     */

    public static int getPartId() {
        partId += 1;
        return partId;
    }

    /**
     * This is a method that returns the next available product id to be used when adding a new product.
     * The product id increases each time this method is called so no two products will end up with the same id.
     * @return the next available part id for a new part.
     */

    public static int getProductId() {
        productId += 1;
        return productId;
    }

    /**
     * This method decreases the next available part id by 1 and is called only when the user clicks the cancel button
     * on the AddPart view. This way, the unused part id can be offered again for the next possible new part being created
     * and will keep the part id from being accidentally increased when no new part was added.
     */

    public static void decreasePartId() {
        partId -= 1;
    }

    /**
     * This method decreases the next available product id by 1 and is called only when the user clicks the cancel button
     * on the AddProduct view. This way, the unused product id can be offered again for the next possible new product being created
     * and will keep the product id from being accidentally increased when no new product was added.
     */

    public static void decreaseProductId() {
        productId -= 1;
    }

    /**
     * This is the getter method for retrieving the saved selected part index.
     * @return the index of the select part as an int.
     */

    public static int getSelectedPartIndex() {
        return selectedPartIndex;
    }

    /**
     * This is the setter method for setting the int of the selected part index.
     * @param p the part that is selected to be compared to the Inventory list of parts to save the index of the part provided.
     */

    public static void setSelectedPartIndex(Part p) {
        int selectedPartId = p.getId();
        int index = -1;
        for (int i = 0; i < Inventory.getAllParts().size(); i++) {
            if (Inventory.getAllParts().get(i).getId() == selectedPartId) {
                index = i;
                break;
            }
        }
        selectedPartIndex = index;
    }

    /**
     * This is the setter method for setting the int of the selected product index.
     * @param p the product that is selected to be compared to the Inventory list of products to save the index of the product provided.
     */

    public static void setSelectedProductIndex(Product p) {
        int selectedProductId = p.getId();
        int index = -1;
        for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
            if (Inventory.getAllProducts().get(i).getId() == selectedProductId) {
                index = i;
                break;
            }
        }
        selectedProductIndex = index;
    }

    /**
     * This is the getter method for retrieving the saved selected product index.
     * @return the index of the select product as an int.
     */

    public static int getSelectedProductIndex() {
        return selectedProductIndex;
    }

    /**
     *  This method determines if this is the first time the Program has started up, so test data can be added once at the beginning of running
     *  and not added again.
     * @return the boolean isFirstCreate or the opposite of that boolean depending on the result of the boolean when
     * this method is called.
     */

    public static boolean isFirstCreate() {
        if (isFirstCreate) {
            isFirstCreate = false;
            return !isFirstCreate;
        }
        return isFirstCreate;
    }

    /**
     * This method validates various strings provided to see if all the provided strings contain the correct information
     * to create the specified part.
     * @param idText This string needs to contain an int.
     * @param name This string needs to not be empty.
     * @param priceText This string needs to contain a double.
     * @param stockText This string needs to contain an int and be a number between the min and max.
     * @param minText This string needs to contain an int and be less than the maxText int.
     * @param maxText This string needs to contain an int and be more than the minText int.
     * @param uniqueText This string needs to not be empty. If the boolean inHousePart is true this will
     *                  also need to be an int.
     * @param inHousePart This is the boolean showing what class the part will be, to change how unique text is
     *                   validated. If the boolean is true, uniqueText will need to be an int for machineId. If this
     *                   is false, uniqueText just needs to not be empty.
     * @return  boolean of true if all validation criteria is met, and a result of false if any criteria is not met.
     */

    public static boolean validatePart(String idText, String name, String priceText, String stockText, String minText, String maxText, String uniqueText, Boolean inHousePart) {
        boolean partInformationIsValid = false;
        if (inHousePart) {
            //if the part is of the inHouse class, this checks that the parameters to create an inHouse part are
            // of the correct data type including machine id being an int
            if(isInt(idText) && isInt(minText) && isInt(maxText) && isInt(stockText) && isInt(uniqueText) && isDouble(priceText)){
                partInformationIsValid = Integer.parseInt(maxText) >= Integer.parseInt(minText);
            if (name.isEmpty()|| Integer.parseInt(stockText)>Integer.parseInt(maxText) ||
                    Integer.parseInt(stockText)<Integer.parseInt(minText)){partInformationIsValid=false;}
            }
        }
        else {
            //if the part is not of the inHouse class (therefore outsourced), this checks that the parameters
            // to create an outsourced part are of the correct data type.
            if(isInt(idText) && isInt(minText) && isInt(maxText) && isInt(stockText) && isDouble(priceText)){
                partInformationIsValid = Integer.parseInt(maxText) >= Integer.parseInt(minText);
                if (name.isEmpty()||uniqueText.isEmpty()|| Integer.parseInt(stockText)>Integer.parseInt(maxText) ||
                        Integer.parseInt(stockText)<Integer.parseInt(minText)){partInformationIsValid=false;}
            }
        }
        return partInformationIsValid;
    }

    /**
     * This method validates various strings provided to see if all the provided strings contain the correct information
     * to create the specified product.
     * @param idText This string needs to contain an int.
     * @param name This string needs to not be empty.
     * @param priceText This string needs to contain a double.
     * @param stockText This string needs to contain an int and be a number between the min and max.
     * @param minText This string needs to contain an int and be less than the maxText int.
     * @param maxText This string needs to contain an int and be more than the minText int.
     * @return boolean of true if all validation criteria is met, and a result of false if any criteria is not met.
     */
    public static boolean validateProduct(String idText, String name, String priceText, String stockText, String minText, String maxText) {
        boolean productInformationIsValid = false;
            if(isInt(idText) && isInt(minText) && isInt(maxText) && isInt(stockText) && isDouble(priceText)){
                productInformationIsValid = Integer.parseInt(maxText) >= Integer.parseInt(minText);
                if (name.isEmpty()|| Integer.parseInt(stockText)>Integer.parseInt(maxText) ||
                        Integer.parseInt(stockText)<Integer.parseInt(minText)){productInformationIsValid=false;}
            }
            return productInformationIsValid;}
    /**
     * This method returns an error message for what caused the validation method for this product to return as false.
     * @param idText This string needs to contain an int.
     * @param name This string needs to not be empty.
     * @param priceText This string needs to contain a double.
     * @param stockText This string needs to contain an int and be a number between the min and max.
     * @param minText This string needs to contain an int and be less than the maxText int.
     * @param maxText This string needs to contain an int and be more than the minText int.
     * @return a detailed error message in a string to be displayed to the user about why the entered information is
     * not valid.
     */
    public static String productValidationError(String idText, String name, String priceText, String stockText, String minText, String maxText){
        String error = "";
        if (name.isEmpty() || idText.isEmpty() || priceText.isEmpty() || stockText.isEmpty() ||
                minText.isEmpty() || maxText.isEmpty()){
            error = "Please fill in all fields.";}
        else if (!isInt(stockText) || !isInt(minText) || !isInt(maxText) || !isDouble(priceText)){
            error = "Inv, Price, Min and Max must be valid numbers.";}
        else if (Integer.parseInt(minText)>Integer.parseInt(maxText)){
            error = "Max must be larger than min";
        }
        else if(Integer.parseInt(stockText)>Integer.parseInt(maxText) || Integer.parseInt(stockText)<Integer.parseInt(minText)){
            error = "Inv must be a number between min and max";
        }
        return error;
    }

    /**
     * This method returns an error message for what caused the validation method for this part to return as false.
     * @param idText This string needs to contain an int.
     * @param name This string needs to not be empty.
     * @param priceText This string needs to contain a double.
     * @param stockText This string needs to contain an int and be a number between the min and max.
     * @param minText This string needs to contain an int and be less than the maxText int.
     * @param maxText This string needs to contain an int and be more than the minText int.
     * @param uniqueText This string needs to not be empty. If the boolean inHousePart is true this will
     *                  also need to be an int.
     * @param inHousePart This is the boolean showing what class the part will be, to change how unique text is
     *                   validated. If the boolean is true, uniqueText will need to be an int for machineId. If this
     *                   is false, uniqueText just needs to not be empty.
     * @return a detailed error message in a string to be displayed to the user about why the entered information is
     * not valid.
     */

    public static String partValidationError(String idText, String name, String priceText, String stockText, String minText, String maxText, String uniqueText, Boolean inHousePart){
        String error = "";
        if (name.isEmpty() || idText.isEmpty() || priceText.isEmpty() || stockText.isEmpty() ||
                minText.isEmpty() || maxText.isEmpty() || uniqueText.isEmpty()){
            error = "Please fill in all fields.";}
        else if (!isInt(stockText) || !isInt(minText) || !isInt(maxText) || !isDouble(priceText)){
            error = "Inv, Price, Min and Max must be valid numbers.";}
        else if (Integer.parseInt(minText)>Integer.parseInt(maxText)){
            error = "Max must be larger than min";}
        else if (inHousePart && !isInt(uniqueText)){
            error = "Machine ID must be a valid number";
        }
        else if(Integer.parseInt(stockText)>Integer.parseInt(maxText) || Integer.parseInt(stockText)<Integer.parseInt(minText)){
            error = "Inv must be a number between min and max";
        }

        return error;
    }

    /**
     *  This method checks to see if a string is holding the value of an int.
     * @param s string to check if value of string is an int.
     * @return if the string had the value of an int, true is returned, otherwise false is returned.
     */

    private static boolean isInt (String s) {
        try {
            int x = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     *  This method checks to see if a string is holding the value of a double.
     * @param s string to check if value of string is a double.
     * @return if the string had the value of a double, true is returned, otherwise false is returned.
     */

    private static boolean isDouble (String s) {
        try {
            double x = Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
