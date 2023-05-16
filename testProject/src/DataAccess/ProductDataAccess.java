package DataAccess;
import java.util.ArrayList;

import Model.Product;

public interface ProductDataAccess {
    void createProduct(String tag, float price);
    void addNewMerchandiseToStock(int quantity, int productSerialNumber);
    void takeMerchandiseOutOfStock(int quantity, int productSerialNumber);
    ArrayList<Product> readAllProducts();
    Product readOneProduct(int productSerialNumber);
    Product bestProductSoldForEntity(int serialNumberEntity);
}
