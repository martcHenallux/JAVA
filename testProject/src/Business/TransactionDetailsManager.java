package Business;

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

    private void createTransactionDetails(Document document, String code, int quantity, Product product){
        dao.createTransactionDetails(document.getType().getCode(), document.getDocumentNumber(), code, quantity, 
            product.getActualUnitPrice(), product.getProductSerialNumber());
    }

    public ArrayList<TransactionDetails> readAllTransactionDetails(){
        return dao.readAllTransactionDetails();
    }

    public ArrayList<TransactionDetails> readTransactionsDetailsOfDocument(Document document){
        return dao.readTransactionsDetailsOfDocument(document.getType().getCode(), document.getDocumentNumber());
    }

    public ArrayList<TransactionDetails> readTransactionsDetailsOfProduct(Product product){
        return dao.readTransactionsDetailsOfProduct(product.getProductSerialNumber());
    }

    public TransactionDetails readOneTransactionDetails(String code){
        return dao.readOneTransactionDetails(code);
    }

    public TransactionDetails readTransactionDetailsFromDocumentAndProduct(Document document, Product product){
        return dao.readTransactionDetailsFromDocumentAndProduct(document.getType().getCode(), 
            document.getDocumentNumber(), product.getProductSerialNumber());
    }

    public void tryCreateTransactionDetails(Document document, String code, int quantity, Product product){
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
