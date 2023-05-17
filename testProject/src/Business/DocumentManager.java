package Business;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import DBAccess.DocumentDBAccess;
import Model.BankInformations;
import Model.Document;
import Model.DocumentStatus;
import Model.DocumentType;
import Model.Workflow;

public class DocumentManager {

    DocumentDBAccess dao;

    public DocumentManager(){
        dao = new DocumentDBAccess();
    }

    private void createDocument(DocumentType type, int documentNumber, Date date, float priceVATExcluded, 
        float priceVATIncluded, Date paymentDeadline, Workflow workflow, DocumentStatus status) throws SQLException{
            dao.createDocument(type.getCode(), documentNumber, date, priceVATExcluded, priceVATIncluded, 
            paymentDeadline, workflow.getId(), status.getCode());
    }

    public ArrayList<Document> readAllDocuments() throws SQLException{
        return dao.readAllDocuments();
    }

    public ArrayList<Document> readDocumentsOfAWorkflow(Workflow workflow) throws SQLException{
        return dao.readDocumentsOfAWorkflow(workflow.getId());
    }

    public ArrayList<Document> readDocumentsOfAStatus(DocumentStatus status) throws SQLException{
        return dao.readDocumentsOfAStatus(status.getCode());
    }

    public ArrayList<Document> readDocumentsOfAType(DocumentType type) throws SQLException{
        return dao.readDocumentsOfAType(type.getCode());
    }

    public ArrayList<Document> readDocumentsPaymentDeadline(Date date) throws SQLException{
        return dao.readDocumentsPaymentDeadline(date);
    }

    public ArrayList<Document> readDocumentsFromADate(Date date) throws SQLException{
        return dao.readDocumentsFromADate(date);
    }

    public Document readOneDocument(DocumentType type, int number) throws SQLException{
        return dao.readOneDocument(type.getCode(), number);
    }

    public ArrayList<Document> readDocumentsMoreExpensiveThanVATExcluded(float price) throws SQLException{
        return dao.readDocumentsMoreExpensiveThanVATExcluded(price);
    }

    public ArrayList<Document> readDocumentsCheaperThanVATExcluded(float price) throws SQLException{
        return dao.readDocumentsCheaperThanVATExcluded(price);
    }

    public ArrayList<BankInformations> getBankAccountsOfEntityFromDocument(Document document) throws SQLException{
        return dao.getBankAccountsOfEntityFromDocument(document.getType().getCode(), document.getDocumentNumber());
    }
    
    public void tryCreateDocument(DocumentType type, int documentNumber, Date date, float priceVATExcluded, 
        float priceVATIncluded, Date paymentDeadline, Workflow workflow, DocumentStatus status) throws SQLException{
            ArrayList<Document> documents = new ArrayList<>();
            documents = readAllDocuments();
            Boolean isIn = false;
            for (Document document : documents) {
                if(document.getType().getCode().toLowerCase().equals(type.getCode().toLowerCase()) && 
                    document.getDocumentNumber() == documentNumber){
                        isIn = true;
                }
            }
            if(!isIn){
                createDocument(type, documentNumber, date, priceVATExcluded, priceVATIncluded, 
                paymentDeadline, workflow, status);
            }
    }
}
