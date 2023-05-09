package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Class for Products
 *
 * @author Zamir Rizvi
 * */
public class Product {

    /**
     * Observable list field for the associatedParts of the product
     * */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * field for id
     * */
    private int id;

    /** field for name
     * */
    private String name;

    /** field for price
     * */
    private double price;
    /** field for stock
     * */
    private int stock;
    /**field for min
     * */
    private int min;

    /** field for max
     * */
    private int max;

    /** Overloaded Constructor for a product
     *
     * @param id id
     * @param name name
     * @param price price
     * @param stock stock
     * @param min min
     * @param max max
     * */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Default Constructor for Product
     *
     **/
    public Product() {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Method to set id
     *
     * @param id id to set*/
    public void setId(int id) {
        this.id = id;
    }

    /**Method to set name
     *
     * @param name name to set
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to set price
     *
     * @param price price to set
     * */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Method to set stock
     *
     * @param stock stock to set
     * */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Method to set min
     *
     * @param min min to set
     * */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Method to set max
     *
     * @param max max to set*/
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return id
     * */
    public int getId() {
        return id;
    }

    /**
     * @return name
     * */
    public String getName() {
        return name;
    }

    /**
     * @return price
     * */
    public double getPrice() {
        return price;
    }

    /**
     * @return stock */
    public int getStock() {
        return stock;
    }

    /**
     * @return min
     * */
    public int getMin() {
        return min;
    }

    /**
     * @return max
     * */
    public int getMax() {
        return max;
    }

    /**
     * Method to add a part to the associated parts list
     *
     * @param part part to add
     * */
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    /**
     * Method to delete an associated part from the list
     *
     * @param selectedAssociatedPart part to delete
     * @return boolean
     * */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        for (Part part : getAllAssociatedParts()) {
            if (part.getId() == selectedAssociatedPart.getId()) {
                return getAllAssociatedParts().remove(selectedAssociatedPart);
            }
        }
        return false;
    }

    /**
     * @return associatedParts
     * */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
