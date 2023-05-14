import java.sql.Date;
import java.util.ArrayList;

public interface DocumentDataAccess {
    void createDocument(DocumentType type, int documentNumber, Date date, float priceVATExcluded, 
        float priceVATIncluded, Date paymentDeadline, Workflow workflow, DocumentStatus status);
    ArrayList<Document> readAllDocuments();
    ArrayList<Document> readDocumentsOfAWorkflow(Workflow workflow);
    ArrayList<Document> readDocumentsOfAStatus(DocumentStatus status);
    ArrayList<Document> readDocumentsOfAType(DocumentType type);
    ArrayList<Document> readDocumentsPaymentDeadline(Date date);
    ArrayList<Document> readDocumentsFromADate(Date date);
    Document readOneDocument(DocumentType type, int number);
    ArrayList<Document> readDocumentsMoreExpensiveThanVATExcluded(float price);
    ArrayList<Document> readDocumentsCheaperThanVATExcluded(float price);
}
