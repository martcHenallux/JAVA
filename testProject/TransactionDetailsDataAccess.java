import java.util.ArrayList;

public interface TransactionDetailsDataAccess {
    void createTransactionDetails(Document document, String code, int quantity, Product product);
    ArrayList<TransactionDetails> readAllTransactionDetails();
    ArrayList<TransactionDetails> readTransactionsDetailsOfDocument(Document documentWanted);
    ArrayList<TransactionDetails> readTransactionsDetailsOfProduct(Product productWanted);
    TransactionDetails readOneTransactionDetails(String code);
    TransactionDetails readTransactionDetailsFromDocumentAndProduct(Document documentWanted, Product productWanted);
}
