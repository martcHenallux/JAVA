package DataAccess;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.BankInformations;
import Model.Document;

public interface DocumentDataAccess {
    void createDocument(String type, int documentNumber, Date date, float priceVATExcluded, 
        float priceVATIncluded, Date paymentDeadline, int workflowId, String status) throws SQLException;
    ArrayList<Document> readAllDocuments() throws SQLException;
    ArrayList<Document> readDocumentsOfAWorkflow(int workflow) throws SQLException;
    ArrayList<Document> readDocumentsOfAStatus(String status) throws SQLException;
    ArrayList<Document> readDocumentsOfAType(String type) throws SQLException;
    ArrayList<Document> readDocumentsPaymentDeadline(Date date) throws SQLException;
    ArrayList<Document> readDocumentsFromADate(Date date) throws SQLException;
    Document readOneDocument(String type, int number) throws SQLException;
    ArrayList<Document> readDocumentsMoreExpensiveThanVATExcluded(float price) throws SQLException;
    ArrayList<Document> readDocumentsCheaperThanVATExcluded(float price) throws SQLException;
    ArrayList<BankInformations> getBankAccountsOfEntityFromDocument(String typeWanted, int numberWanted) 
        throws SQLException;
}
