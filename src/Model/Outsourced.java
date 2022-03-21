package Model;

/**
 * The Outsourced class is a concrete class that inherits from the provided part class. This specifies a part that is not
 * created in-house, so it was outsourced and created by another company.
 * The name of the company that made the part is required for the creation of an instance of an Outsourced object.
 */

public class Outsourced extends Part{

    /**
     * This holds the name of the company that made the part.
     */
    private String companyName;

    /**
     * This is the constructor that creates an instance of an Outsourced part.
     * @param id: This is the id number assigned to the part.
     * @param name: This is the name of the part.
     * @param price: This is the price of the part.
     * @param stock: This is the number quantifying stock or the amount of this object in inventory.
     * @param min: The min is the minimum amount of this part that should be in stock.
     * @param max: The max is the maximum amount of this part that should be in stock.
     * @param companyName: This is the name of the company that made the part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        setCompanyName(companyName);
    }

    /**
     * This is the getter for the company name of the part.
     * @return the company name assigned to the part
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * This is the setter for the company name of the part.
     * @param companyName: This is the name of the company that made the part.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

