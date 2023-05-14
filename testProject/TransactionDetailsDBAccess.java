import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TransactionDetailsDBAccess implements TransactionDetailsDataAccess{

    private DocumentDataAccess document;
    private DocumentTypeDataAccess documentType;
    private ProductDataAccess product;
    
    public TransactionDetailsDBAccess(){
        this.document = new DocumentDBAccess();
        this.documentType = new DocumentTypeDBAccess();
        this.product = new ProductDBAccess();
    }

    public void createTransactionDetails(Document document, String code, int quantity, Product product){
        StringBuilder sqlInstruction = new StringBuilder("insert into transaction_details");
        sqlInstruction.append(" ( documentType, documentNumber, code, quantity, unitPrice, reference)");
        sqlInstruction.append(" values ( ?, ?, ?, ?, ?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setString(1, document.getType().getCode());
            preparedStatement.setInt(2, document.getDocumentNumber());
            preparedStatement.setString(3, code);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setFloat(5, product.getActualUnitPrice());
            preparedStatement.setInt(6, product.getProductSerialNumber());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error creating transaction details: " + e.getMessage());
        }
    }

    public ArrayList<TransactionDetails> readAllTransactionDetails(){
        String sqlInstruction = "select * from transaction_details";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<TransactionDetails> allTransactions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            TransactionDetails transaction;
            while(data.next()){
                transaction = new TransactionDetails(
                    document.readOneDocument(documentType.readOneDocumentType(
                        data.getString("documentType")), data.getInt("documentNumber")), 
                    data.getString("code"),
                    data.getInt("quantity"), 
                    data.getFloat("unitPrice"), 
                    product.readOneProduct(data.getInt("reference")));
                allTransactions.add(transaction);
            }
        } catch (Exception e) {
            System.out.println("Error reading all transactions: " + e.getMessage());
        }
        return allTransactions;
    }

    public ArrayList<TransactionDetails> readTransactionsDetailsOfDocument(Document documentWanted){
        String sqlInstruction = "select * from transaction_details where documentType = ? and documentNumber = ?";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<TransactionDetails> allTransactions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, documentWanted.getType().getCode());
            preparedStatement.setInt(2, documentWanted.getDocumentNumber());
            ResultSet data = preparedStatement.executeQuery();
            TransactionDetails transaction;
            while(data.next()){
                transaction = new TransactionDetails(
                    document.readOneDocument(documentType.readOneDocumentType(
                        data.getString("documentType")), data.getInt("documentNumber")), 
                    data.getString("code"),
                    data.getInt("quantity"), 
                    data.getFloat("unitPrice"), 
                    product.readOneProduct(data.getInt("reference")));
                allTransactions.add(transaction);
            }
        } catch (Exception e) {
            System.out.println("Error reading all transactions: " + e.getMessage());
        }
        return allTransactions;
    }

    public ArrayList<TransactionDetails> readTransactionsDetailsOfProduct(Product productWanted){
        String sqlInstruction = "select * from transaction_details where reference = ?";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<TransactionDetails> allTransactions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, productWanted.getProductSerialNumber());
            ResultSet data = preparedStatement.executeQuery();
            TransactionDetails transaction;
            while(data.next()){
                transaction = new TransactionDetails(
                    document.readOneDocument(documentType.readOneDocumentType(
                        data.getString("documentType")), data.getInt("documentNumber")), 
                    data.getString("code"),
                    data.getInt("quantity"), 
                    data.getFloat("unitPrice"), 
                    product.readOneProduct(data.getInt("reference")));
                allTransactions.add(transaction);
            }
        } catch (Exception e) {
            System.out.println("Error reading all transactions: " + e.getMessage());
        }
        return allTransactions;
    }

    public TransactionDetails readOneTransactionDetails(String code){
        String sqlInstruction = "select * from transaction_details where code = ?";
        Connection connection = SingletonConnexion.getInstance();
        TransactionDetails transaction = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, code);
            ResultSet data = preparedStatement.executeQuery();
            if(data.next()){
                transaction = new TransactionDetails(
                    document.readOneDocument(documentType.readOneDocumentType(
                        data.getString("documentType")), data.getInt("documentNumber")), 
                    data.getString("code"),
                    data.getInt("quantity"), 
                    data.getFloat("unitPrice"), 
                    product.readOneProduct(data.getInt("reference")));
            }
        } catch (Exception e) {
            System.out.println("Error reading all transactions: " + e.getMessage());
        }
        return transaction;
    }

    public TransactionDetails readTransactionDetailsFromDocumentAndProduct(Document documentWanted, 
        Product productWanted){
        StringBuilder sqlInstruction = new StringBuilder("select *");
        sqlInstruction.append(" from transaction_details");
        sqlInstruction.append(" where documentType = ? and documentNumber = ? and reference = ?");
        Connection connection = SingletonConnexion.getInstance();
        TransactionDetails transaction = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setString(1, documentWanted.getType().getCode());
            preparedStatement.setInt(2, documentWanted.getDocumentNumber());
            preparedStatement.setInt(3, productWanted.getProductSerialNumber());
            ResultSet data = preparedStatement.executeQuery();
            if(data.next()){
                transaction = new TransactionDetails(
                    document.readOneDocument(documentType.readOneDocumentType(
                        data.getString("documentType")), data.getInt("documentNumber")), 
                    data.getString("code"),
                    data.getInt("quantity"), 
                    data.getFloat("unitPrice"), 
                    product.readOneProduct(data.getInt("reference")));
            }
        } catch (Exception e) {
            System.out.println("Error reading transaction of a product in a document: " + e.getMessage());
        }
        return transaction;
    }
}
