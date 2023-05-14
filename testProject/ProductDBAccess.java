import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductDBAccess implements ProductDataAccess{
    
    public ProductDBAccess(){}

    public void createProduct(String tag, float price){
        StringBuilder sqlInstruction = new StringBuilder("insert into product");
        sqlInstruction.append(" ( tag, actualUnitPrice, stockQuantity)");
        sqlInstruction.append(" values ( ?, ?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setString(1, tag);
            preparedStatement.setFloat(2, price);
            preparedStatement.setInt(3, 0);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error creating a product: " + e.getMessage());
        }
    }

    public void addNewMerchandiseToStock(int quantity, int productSerialNumber){
        StringBuilder sqlInstruction = new StringBuilder("update product");
        sqlInstruction.append(" set stockQuantity = stockQuantity + ?");
        sqlInstruction.append(" where productSerialNumber = ?");
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productSerialNumber);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error adding stock: " + e.getMessage());
        }
    }

    public void takeMerchandiseOutOfStock(int quantity, int productSerialNumber){
        StringBuilder sqlInstruction = new StringBuilder("update product");
        sqlInstruction.append(" set stockQuantity = stockQuantity - ?");
        sqlInstruction.append(" where productSerialNumber = ?");
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productSerialNumber);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error adding stock: " + e.getMessage());
        }
    }

    public ArrayList<Product> readAllProducts(){
        String sqlInstruction = "select * from product";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<Product> allProducts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            Product product;
            while(data.next()){
                product = new Product(data.getInt("productSerialNumber"), 
                    data.getString("tag"), 
                    data.getFloat("actualUnitPrice"), 
                    data.getInt("stockQuantity"));
                allProducts.add(product);
            }
        } catch (Exception e) {
            System.out.println("Error reading all products: " + e.getMessage());
        }
        return allProducts;
    }

    public Product readOneProduct(int productSerialNumber){
        StringBuilder sqlInstruction = new StringBuilder("select *");
        sqlInstruction.append(" from product");
        sqlInstruction.append(" where productSerialNumber = ?");
        Connection connection = SingletonConnexion.getInstance();
        Product product = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setInt(1, productSerialNumber);
            ResultSet data = preparedStatement.executeQuery();
            if(data.next()){
                product = new Product(data.getInt("productSerialNumber"), 
                    data.getString("tag"),
                    data.getFloat("actualUnitPrice"), 
                    data.getInt("stockQuantity"));
            }
        } catch (Exception e) {
            System.out.println("Error reading one product: " + e.getMessage());
        }
        return product;
    }
}
