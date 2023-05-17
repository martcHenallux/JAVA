package DataAccess;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Product;

public interface ProductDataAccess {
    void createProduct(String tag, float price) throws SQLException;
    void addNewMerchandiseToStock(int quantity, int productSerialNumber) throws SQLException;
    void takeMerchandiseOutOfStock(int quantity, int productSerialNumber) throws SQLException;
    ArrayList<Product> readAllProducts() throws SQLException;
    Product readOneProduct(int productSerialNumber)throws SQLException;
    Product bestProductSoldForEntity(int serialNumberEntity) throws SQLException;
}
