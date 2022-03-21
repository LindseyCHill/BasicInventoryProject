package Model;

/**
 * The InHouse class is a concrete class that inherits from the provided part class. This specifies a part that is
 * created in-house as opposed to being outsourced and created by another company.
 * A machine ID is required for an in-house part.
 */

public class InHouse extends Part {
    /**
     * This is the id of the machine that made the part.
     */
    private int machineId;

    /**
     * This constructor creates an instance of an in-house created part. The parameters required are listed below.
     * @param id: This is the id number assigned to the part.
     * @param name: This is the name of the part.
     * @param price: This is the price of the part.
     * @param stock: This is the number quantifying stock or the amount of this object in inventory.
     * @param min: The min is the minimum amount of this part that should be in stock.
     * @param max: The max is the maximum amount of this part that should be in stock.
     * @param machineId: This is an ID number assigned to the machine that made the part in-house.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        setMachineId(machineId);
    }

    /**
     * This method is the getter for the machine ID.
     * @return the machineId of this part.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * This method is the setter for the machine ID
     * @param machineId: sets the machine ID for the part, so it can be recalled later.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}

