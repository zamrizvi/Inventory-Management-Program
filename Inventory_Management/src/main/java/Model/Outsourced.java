package Model;

import Model.Part;

/** Class for a part that is Outsourced.
 * This class inherits from the Part abstract class.
 *
 * @author Zamir Rizvi*/
public class Outsourced extends Part {

    /** Default Constructor for an outsourced part
     * @param id id
     * @param name name
     * @param price price
     * @param stock stock
     * @param min min
     * @param max max
     * */
    public Outsourced(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    /** Field for companyName.
     * Used as a property for a Part that was outsourced from a different company.
     * */
    private String companyName;

    /** Overloaded Constructor for an outsourced part
     * @param id id
     * @param name name
     * @param price price
     * @param stock stock
     * @param min min
     * @param max max
     * @param companyName companyName
     * */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @param companyName company name to set
     * */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return company name
     **/
    public String getCompanyName() {
        return companyName;
    }
}
