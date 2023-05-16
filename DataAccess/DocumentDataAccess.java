package DataAccess;
import java.sql.Date;
import java.util.ArrayList;

import Model.BankInformations;
import Model.Document;

public interface DocumentDataAccess {
    void createDocument(String type, int documentNumber, Date date, float priceVATExcluded, 
        float priceVATIncluded, Date paymentDeadline, int workflowId, String status);
    ArrayList<Document> readAllDocuments();
    ArrayList<Document> readDocumentsOfAWorkflow(int workflow);
    ArrayList<Document> readDocumentsOfAStatus(String status);
    ArrayList<Document> readDocumentsOfAType(String type);
    ArrayList<Document> readDocumentsPaymentDeadline(Date date);
    ArrayList<Document> readDocumentsFromADate(Date date);
    Document readOneDocument(String type, int number);
    ArrayList<Document> readDocumentsMoreExpensiveThanVATExcluded(float price);
    ArrayList<Document> readDocumentsCheaperThanVATExcluded(float price);
    ArrayList<BankInformations> getBankAccountsOfEntityFromDocument(String typeWanted, int numberWanted);
}
