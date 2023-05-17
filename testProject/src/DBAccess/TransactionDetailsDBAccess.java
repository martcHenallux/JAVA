package DBAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DataAccess.DocumentDataAccess;
import DataAccess.DocumentTypeDataAccess;
import DataAccess.ProductDataAccess;
import DataAccess.TransactionDetailsDataAccess;
import Model.TransactionDetails;

public class TransactionDetailsDBAccess implements TransactionDetailsDataAccess{

    private DocumentDataAccess document;
    private DocumentTypeDataAccess documentType;
    private ProductDataAccess product;
    
    public TransactionDetailsDBAccess(){
        this.document = new DocumentDBAccess();
        this.documentType = new DocumentTypeDBAccess();
        this.product = new ProductDBAccess();
    }

    public void createTransactionDetails(String documentType, int documentNumber, String code, int quantity, 
        float productPrice, int productReference) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("insert into transaction_details");
        sqlInstruction.append(" ( documentType, documentNumber, code, quantity, unitPrice, reference)");
        sqlInstruction.append(" values ( ?, ?, ?, ?, ?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setString(1, documentType);
            preparedStatement.setInt(2, documentNumber);
            preparedStatement.setString(3, code);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setFloat(5, productPrice);
            preparedStatement.setInt(6, productReference);
            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<TransactionDetails> readAllTransactionDetails() throws SQLException{
        String sqlInstruction = "select * from transaction_details";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<TransactionDetails> allTransactions = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            try (ResultSet data = preparedStatement.executeQuery()){
                TransactionDetails transaction;
                while(data.next()){
                    transaction = new TransactionDetails(
                        document.readOneDocument(documentType.readOneDocumentType(
                            data.getString("documentType")).getCode(), 
                            data.getInt("documentNumber")), 
                        data.getString("code"),
                        data.getInt("quantity"), 
                        data.getFloat("unitPrice"), 
                        product.readOneProduct(data.getInt("reference")));
                    allTransactions.add(transaction);
                }
            }
        }
        return allTransactions;
    }

    public ArrayList<TransactionDetails> readTransactionsDetailsOfDocument(String documentTypeWanted, 
        int documentNumber) throws SQLException{
        String sqlInstruction = "select * from transaction_details where documentType = ? and documentNumber = ?";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<TransactionDetails> allTransactions = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setString(1, documentTypeWanted);
            preparedStatement.setInt(2, documentNumber);
            try (ResultSet data = preparedStatement.executeQuery()){
                TransactionDetails transaction;
                while(data.next()){
                    transaction = new TransactionDetails(
                        document.readOneDocument(documentType.readOneDocumentType(
                            data.getString("documentType")).getCode(), 
                            data.getInt("documentNumber")), 
                        data.getString("code"),
                        data.getInt("quantity"), 
                        data.getFloat("unitPrice"), 
                        product.readOneProduct(data.getInt("reference")));
                    allTransactions.add(transaction);
                }
            }
        }
        return allTransactions;
    }

    public ArrayList<TransactionDetails> readTransactionsDetailsOfProduct(int productReference) throws SQLException{
        String sqlInstruction = "select * from transaction_details where reference = ?";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<TransactionDetails> allTransactions = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setInt(1, productReference);
            try (ResultSet data = preparedStatement.executeQuery()){
                TransactionDetails transaction;
                while(data.next()){
                    transaction = new TransactionDetails(
                        document.readOneDocument(documentType.readOneDocumentType(
                            data.getString("documentType")).getCode(), 
                            data.getInt("documentNumber")), 
                        data.getString("code"),
                        data.getInt("quantity"), 
                        data.getFloat("unitPrice"), 
                        product.readOneProduct(data.getInt("reference")));
                    allTransactions.add(transaction);
                }
            }
        }
        return allTransactions;
    }

    public TransactionDetails readOneTransactionDetails(String code) throws SQLException{
        String sqlInstruction = "select * from transaction_details where code = ?";
        Connection connection = SingletonConnexion.getInstance();
        TransactionDetails transaction = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setString(1, code);
            try (ResultSet data = preparedStatement.executeQuery()){
                if(data.next()){
                transaction = new TransactionDetails(
                    document.readOneDocument(documentType.readOneDocumentType(
                        data.getString("documentType")).getCode(), 
                        data.getInt("documentNumber")), 
                    data.getString("code"),
                    data.getInt("quantity"), 
                    data.getFloat("unitPrice"), 
                    product.readOneProduct(data.getInt("reference")));
                }
            }
        }
        return transaction;
    }

    public TransactionDetails readTransactionDetailsFromDocumentAndProduct(String documentTypeWanted, 
        int documentNumber, int productReference) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("select *");
        sqlInstruction.append(" from transaction_details");
        sqlInstruction.append(" where documentType = ? and documentNumber = ? and reference = ?");
        Connection connection = SingletonConnexion.getInstance();
        TransactionDetails transaction = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setString(1, documentTypeWanted);
            preparedStatement.setInt(2, documentNumber);
            preparedStatement.setInt(3, productReference);
            try (ResultSet data = preparedStatement.executeQuery()){
                if(data.next()){
                transaction = new TransactionDetails(
                    document.readOneDocument(documentType.readOneDocumentType(
                        data.getString("documentType")).getCode(), 
                        data.getInt("documentNumber")), 
                    data.getString("code"),
                    data.getInt("quantity"), 
                    data.getFloat("unitPrice"), 
                    product.readOneProduct(data.getInt("reference")));
                }
            }
        }
        return transaction;
    }
}
