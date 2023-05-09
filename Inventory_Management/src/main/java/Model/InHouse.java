package Model;

import Model.Part;

/** Class for a part that is developed InHouse.
 * This class inherits from the Part abstract class.
 *
 * @author Zamir Rizvi
 * */
public class InHouse extends Part {

    /** Default Constructor for a Part developed In House
     * @param id id
     * @param name name
     * @param price price
     * @param stock stock
     * @param min min
     * @param max max
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    /** Field for machineId.
     * Used for a property of a Part that was developed In House.
     * */
    private int machineId;

    /** Overloaded Constructor for a Part developed In House.
     * @param id id
     * @param name name
     * @param price price
     * @param stock stock
     * @param min min
     * @param max max
     * @param machineId machineId
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @param machineId machineid to set
     * */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /** @return machineId
     * */
    public int getMachineId() {
        return machineId;
    }
}
