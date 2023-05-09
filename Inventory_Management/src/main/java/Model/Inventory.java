package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class that manages the data and functionality of parts and products.
 * This class contains the data of the parts and products in lists that can be accessed and changed.
 *
 * @author Zamir Rizvi
 * */
public class Inventory {

    /**
     * static integer that is used for generating unique ids for the parts
     * */
    public static int uniquePartId = 1;

    /**
     * static integer that is used for generating unique ids for the products
     * */
    public static int uniqueProductId = 2;

    /**
     * Static ObservableList to contain parts data
     * */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * Static ObservableList to contain products data
     * */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * adds a new part to the allParts list
     *
     * @param newPart part to add
     * */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * adds a new product to the allProducts list
     *
     * @param newProduct product to add
     * */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** Method to look up part using the part id.
     * The user can look up parts by entering the unique part id in the search box.
     *
     * @param partId id to look for
     * @return lookupPart
     * */
    public static Part lookupPart(int partId) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /** Method to look up product using the product id.
     * The user can look up products by entering the unique product id in the search box.
     *
     * @param productId id to look for
     * @return lookupProduct
     * */
    public static Product lookupProduct(int productId) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /** Method to look up parts using a String.
     * The user can look up parts by entering the part name or a letter in the part name
     * and returns a list of parts that matches.
     *
     * @param partName name of part to look for
     * @return lookupPart
     * */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> lookupPart = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part SP : allParts) {
            if (SP.getName().contains(partName)) {
                lookupPart.add(SP);
            }
        }
        return lookupPart;
    }

    /** Method to look up products using a String.
     * The user can look up products by entering the product name or a letter in the product name
     * and returns a list of products that matches.
     *
     * @param productName name of product to look for
     * @return lookupProduct
     * */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> lookupProduct = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (Product SP : allProducts) {
            if (SP.getName().contains(productName)) {
                lookupProduct.add(SP);
            }
        }
        return lookupProduct;
    }

    /** Method to update a part in the list
     *
     * @param index index of the part to change
     * @param selectedPart part to update
     * */
    public static void updatePart(int index, Part selectedPart) {
        Inventory.getAllParts().set(index, selectedPart);
    }

    /** Method to update a product in the list
     *
     * @param index index of the product to change
     * @param newProduct product to change
     * */
    public static void updateProduct(int index, Product newProduct) {
        Inventory.getAllProducts().set(index, newProduct);
    }

    /** Method to delete a part from the list
     *
     * @param selectedPart part to delete
     * @return boolean
     * */
    public static boolean deletePart(Part selectedPart) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == selectedPart.getId()) {
                return Inventory.getAllParts().remove(part);
            }
        }
        return false;
    }

    /** Method to delete a product from the list
     *
     * @param selectedProduct product to delete
     * @return boolean
     * */
    public static boolean deleteProduct(Product selectedProduct) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == selectedProduct.getId()) {
                return Inventory.getAllProducts().remove(product);
            }
        }
        return false;
    }

    /**
     * @return allParts
     * */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return allProducts
     * */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
