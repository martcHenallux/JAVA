package DataAccess;
import java.util.ArrayList;

import Model.TransactionDetails;

public interface TransactionDetailsDataAccess {
    void createTransactionDetails(String documentType, int documentNumber, String code, int quantity, 
        float productPrice, int productReference);
    ArrayList<TransactionDetails> readAllTransactionDetails();
    ArrayList<TransactionDetails> readTransactionsDetailsOfDocument(String documentType, int documentNumber);
    ArrayList<TransactionDetails> readTransactionsDetailsOfProduct(int productReference);
    TransactionDetails readOneTransactionDetails(String code);
    TransactionDetails readTransactionDetailsFromDocumentAndProduct(String documentType, int documentNumber, 
        int productReference);
}
