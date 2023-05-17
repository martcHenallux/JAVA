package DataAccess;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.TransactionDetails;

public interface TransactionDetailsDataAccess {
    void createTransactionDetails(String documentType, int documentNumber, String code, int quantity, 
        float productPrice, int productReference) throws SQLException;
    ArrayList<TransactionDetails> readAllTransactionDetails() throws SQLException;
    ArrayList<TransactionDetails> readTransactionsDetailsOfDocument(String documentType, int documentNumber) throws SQLException;
    ArrayList<TransactionDetails> readTransactionsDetailsOfProduct(int productReference) throws SQLException;
    TransactionDetails readOneTransactionDetails(String code) throws SQLException;
    TransactionDetails readTransactionDetailsFromDocumentAndProduct(String documentType, int documentNumber, 
        int productReference) throws SQLException;
}
