package DBAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DataAccess.ProductDataAccess;
import Model.Product;

public class ProductDBAccess implements ProductDataAccess{
    
    public ProductDBAccess(){}

    public void createProduct(String tag, float price) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("insert into product");
        sqlInstruction.append(" ( tag, actualUnitPrice, stockQuantity)");
        sqlInstruction.append(" values ( ?, ?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setString(1, tag);
            preparedStatement.setFloat(2, price);
            preparedStatement.setInt(3, 0);
            preparedStatement.executeUpdate();
        }
    }

    public void addNewMerchandiseToStock(int quantity, int productSerialNumber) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("update product");
        sqlInstruction.append(" set stockQuantity = stockQuantity + ?");
        sqlInstruction.append(" where productSerialNumber = ?");
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productSerialNumber);
            preparedStatement.executeUpdate();
        }
    }

    public void takeMerchandiseOutOfStock(int quantity, int productSerialNumber) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("update product");
        sqlInstruction.append(" set stockQuantity = stockQuantity - ?");
        sqlInstruction.append(" where productSerialNumber = ?");
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productSerialNumber);
            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<Product> readAllProducts() throws SQLException{
        String sqlInstruction = "select * from product";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<Product> allProducts = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){            
            try (ResultSet data = preparedStatement.executeQuery()){
                Product product;
                while(data.next()){
                    product = new Product(data.getInt("productSerialNumber"), 
                        data.getString("tag"), 
                        data.getFloat("actualUnitPrice"), 
                        data.getInt("stockQuantity"));
                    allProducts.add(product);
                }
            }
        }
        return allProducts;
    }

    public Product readOneProduct(int productSerialNumber) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("select *");
        sqlInstruction.append(" from product");
        sqlInstruction.append(" where productSerialNumber = ?");
        Connection connection = SingletonConnexion.getInstance();
        Product product = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){            
            preparedStatement.setInt(1, productSerialNumber);
            try (ResultSet data = preparedStatement.executeQuery()){
                if(data.next()){
                    product = new Product(data.getInt("productSerialNumber"), 
                        data.getString("tag"),
                        data.getFloat("actualUnitPrice"), 
                        data.getInt("stockQuantity"));
                }
            }
        }
        return product;
    }

    public Product bestProductSoldForEntity(int serialNumberEntity) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("select productSerialNumber,");
        sqlInstruction.append(" sum(transaction_details.quantity) number");
        sqlInstruction.append(" from product");
        sqlInstruction.append(" inner join transaction_details on");
        sqlInstruction.append(" product.productSerialNumber = transaction_details.reference");
        sqlInstruction.append(" inner join document on transaction_details.documentType = document.type");
        sqlInstruction.append(" and transaction_details.documentNumber = document.documentNumber");
        sqlInstruction.append(" inner join workflow on document.workflowId = workflow.id");
        sqlInstruction.append(" and workflow.type = 'Client'");
        sqlInstruction.append(" and workflow.serialNumberEntity = ?");
        sqlInstruction.append(" group by productSerialNumber order by number desc limit 1");
        Connection connection = SingletonConnexion.getInstance();
        Product bestProduct = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setInt(1, serialNumberEntity);
            try (ResultSet data = preparedStatement.executeQuery()){
                ProductDataAccess product = new ProductDBAccess();
                if(data.next()){
                    bestProduct = product.readOneProduct(data.getInt("productSerialNumber"));
                }
            }
        }
        return bestProduct;
    }
}
