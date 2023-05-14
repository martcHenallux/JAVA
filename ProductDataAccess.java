import java.util.ArrayList;

public interface ProductDataAccess {
    void createProduct(String tag, float price);
    void addNewMerchandiseToStock(int quantity, int productSerialNumber);
    void takeMerchandiseOutOfStock(int quantity, int productSerialNumber);
    ArrayList<Product> readAllProducts();
    Product readOneProduct(int productSerialNumber);
}
