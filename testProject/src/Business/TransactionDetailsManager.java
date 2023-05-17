package Business;

import java.sql.SQLException;
import java.util.ArrayList;

import DBAccess.TransactionDetailsDBAccess;
import Model.Document;
import Model.Product;
import Model.TransactionDetails;

public class TransactionDetailsManager {
    
    TransactionDetailsDBAccess dao;

    public TransactionDetailsManager(){
        dao = new TransactionDetailsDBAccess();
    }

    private void createTransactionDetails(Document document, String code, int quantity, Product product) throws SQLException{
        dao.createTransactionDetails(document.getType().getCode(), document.getDocumentNumber(), code, quantity, 
            product.getActualUnitPrice(), product.getProductSerialNumber());
    }

    public ArrayList<TransactionDetails> readAllTransactionDetails() throws SQLException{
        return dao.readAllTransactionDetails();
    }

    public ArrayList<TransactionDetails> readTransactionsDetailsOfDocument(Document document) throws SQLException{
        return dao.readTransactionsDetailsOfDocument(document.getType().getCode(), document.getDocumentNumber());
    }

    public ArrayList<TransactionDetails> readTransactionsDetailsOfProduct(Product product) throws SQLException{
        return dao.readTransactionsDetailsOfProduct(product.getProductSerialNumber());
    }

    public TransactionDetails readOneTransactionDetails(String code) throws SQLException{
        return dao.readOneTransactionDetails(code);
    }

    public TransactionDetails readTransactionDetailsFromDocumentAndProduct(Document document, Product product) throws SQLException{
        return dao.readTransactionDetailsFromDocumentAndProduct(document.getType().getCode(), 
            document.getDocumentNumber(), product.getProductSerialNumber());
    }

    public void tryCreateTransactionDetails(Document document, String code, int quantity, Product product) throws SQLException{
        ArrayList<TransactionDetails> transactions = new ArrayList<>();
        transactions = readAllTransactionDetails();
        Boolean isIn = false;
        for (TransactionDetails transactionDetails : transactions) {
            if(transactionDetails.getCode().toUpperCase().equals(code.toUpperCase())){
                isIn = true;
            }
        }
        if(!isIn){
            createTransactionDetails(document, code, quantity, product);
        }
    }
}
