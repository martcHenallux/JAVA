package DBAccess;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DataAccess.BusinessEntityDataAccess;
import DataAccess.DocumentDataAccess;
import Model.BankInformations;
import Model.Document;
import DataAccess.DocumentStatusDataAccess;
import DataAccess.DocumentTypeDataAccess;
import DataAccess.WorkflowDataAccess;

public class DocumentDBAccess implements DocumentDataAccess{

    private DocumentTypeDataAccess type;
    private WorkflowDataAccess workflow;
    private DocumentStatusDataAccess status;

    public DocumentDBAccess(){
        type = new DocumentTypeDBAccess();
        workflow = new WorkflowDBAccess();
        status = new DocumentStatusDBAccess();
    }
    
    public void createDocument(String typeGiven, int documentNumber, Date date, float priceVATExcluded, 
        float priceVATIncluded, Date paymentDeadline, int workflowId, String statusGiven){
        
        StringBuilder sqlInstruction = new StringBuilder("insert into document");
        sqlInstruction.append(" (type, documentNumber, date, priceVATExcluded, priceVATIncluded,");
        sqlInstruction.append(" paymentDeadline, workflowId, status)");
        sqlInstruction.append(" values (?, ?, ?, ?, ?, ?, ?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setString(1, typeGiven);
            preparedStatement.setInt(2, documentNumber);
            preparedStatement.setDate(3, date);
            preparedStatement.setFloat(4, priceVATExcluded);
            preparedStatement.setFloat(5, priceVATIncluded);
            preparedStatement.setDate(6, paymentDeadline);
            preparedStatement.setInt(7, workflowId);
            preparedStatement.setString(8, statusGiven);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error creating document: " + e.getMessage());
        }
    }

    public ArrayList<Document> readAllDocuments(){
        String sqlInstruction = "select * from document";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<Document> allDocuments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            Document document;
            while(data.next()){
                document = new Document(type.readOneDocumentType(data.getString("type")), 
                    data.getInt("documentNumber"), 
                    data.getDate("date"), 
                    data.getFloat("priceVATExcluded"), 
                    data.getFloat("priceVATIncluded"), 
                    data.getDate("paymentDeadline"), 
                    workflow.readOneWorkflow(data.getInt("workflowId")), 
                    status.readOneDocumentStatus(data.getString("status")));
                allDocuments.add(document);
            }
        } catch (Exception e) {
            System.out.println("Error reading all documents: " + e.getMessage());
        }
        return allDocuments;
    }

    public ArrayList<Document> readDocumentsOfAWorkflow(int workflowWanted){
        String sqlInstruction = "select * from document where workflowId = ?";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<Document> allDocuments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, workflowWanted);
            ResultSet data = preparedStatement.executeQuery();
            Document document;
            while(data.next()){
                document = new Document(type.readOneDocumentType(data.getString("type")), 
                    data.getInt("documentNumber"), 
                    data.getDate("date"), 
                    data.getFloat("priceVATExcluded"), 
                    data.getFloat("priceVATIncluded"), 
                    data.getDate("paymentDeadline"), 
                    workflow.readOneWorkflow(data.getInt("workflowId")), 
                    status.readOneDocumentStatus(data.getString("status")));
                allDocuments.add(document);
            }
        } catch (Exception e) {
            System.out.println("Error reading all documents of a workflow: " + e.getMessage());
        }
        return allDocuments;
    }

    public ArrayList<Document> readDocumentsOfAStatus(String statusWanted){
        String sqlInstruction = "select * from document where status = ?";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<Document> allDocuments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, statusWanted);
            ResultSet data = preparedStatement.executeQuery();
            Document document;
            while(data.next()){
                document = new Document(type.readOneDocumentType(data.getString("type")), 
                    data.getInt("documentNumber"), 
                    data.getDate("date"), 
                    data.getFloat("priceVATExcluded"), 
                    data.getFloat("priceVATIncluded"), 
                    data.getDate("paymentDeadline"), 
                    workflow.readOneWorkflow(data.getInt("workflowId")), 
                    status.readOneDocumentStatus(data.getString("status")));
                allDocuments.add(document);
            }
        } catch (Exception e) {
            System.out.println("Error reading all documents of a status: " + e.getMessage());
        }
        return allDocuments;
    }

    public ArrayList<Document> readDocumentsOfAType(String typeWanted){
        String sqlInstruction = "select * from document where type = ?";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<Document> allDocuments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, typeWanted);
            ResultSet data = preparedStatement.executeQuery();
            Document document;
            while(data.next()){
                document = new Document(type.readOneDocumentType(data.getString("type")), 
                    data.getInt("documentNumber"), 
                    data.getDate("date"), 
                    data.getFloat("priceVATExcluded"), 
                    data.getFloat("priceVATIncluded"), 
                    data.getDate("paymentDeadline"), 
                    workflow.readOneWorkflow(data.getInt("workflowId")), 
                    status.readOneDocumentStatus(data.getString("status")));
                allDocuments.add(document);
            }
        } catch (Exception e) {
            System.out.println("Error reading all documents of a type: " + e.getMessage());
        }
        return allDocuments;
    }

    public ArrayList<Document> readDocumentsPaymentDeadline(Date date){
        String sqlInstruction = "select * from document where paymentDeadline = ?";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<Document> allDocuments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setDate(1, date);
            ResultSet data = preparedStatement.executeQuery();
            Document document;
            while(data.next()){
                document = new Document(type.readOneDocumentType(data.getString("type")), 
                    data.getInt("documentNumber"), 
                    data.getDate("date"), 
                    data.getFloat("priceVATExcluded"), 
                    data.getFloat("priceVATIncluded"), 
                    data.getDate("paymentDeadline"), 
                    workflow.readOneWorkflow(data.getInt("workflowId")), 
                    status.readOneDocumentStatus(data.getString("status")));
                allDocuments.add(document);
            }
        } catch (Exception e) {
            System.out.println("Error reading all documents from the specific payment deadline: " + e.getMessage());
        }
        return allDocuments;
    }

    public ArrayList<Document> readDocumentsFromADate(Date date){
        String sqlInstruction = "select * from document where date = ?";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<Document> allDocuments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setDate(1, date);
            ResultSet data = preparedStatement.executeQuery();
            Document document;
            while(data.next()){
                document = new Document(type.readOneDocumentType(data.getString("type")), 
                    data.getInt("documentNumber"), 
                    data.getDate("date"), 
                    data.getFloat("priceVATExcluded"), 
                    data.getFloat("priceVATIncluded"), 
                    data.getDate("paymentDeadline"), 
                    workflow.readOneWorkflow(data.getInt("workflowId")), 
                    status.readOneDocumentStatus(data.getString("status")));
                allDocuments.add(document);
            }
        } catch (Exception e) {
            System.out.println("Error reading all documents from a specific date: " + e.getMessage());
        }
        return allDocuments;
    }

    public Document readOneDocument(String typeWanted, int numberWanted){
        StringBuilder sqlInstruction = new StringBuilder("select *");
        sqlInstruction.append(" from document");
        sqlInstruction.append(" where type = ? and documentNumber = ?");
        Connection connection = SingletonConnexion.getInstance();
        Document document = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setString(1, typeWanted);
            preparedStatement.setInt(2, numberWanted);
            ResultSet data = preparedStatement.executeQuery();
            if(data.next()){
                document = new Document(type.readOneDocumentType(data.getString("type")),
                    data.getInt("documentNumber"), 
                    data.getDate("date"), 
                    data.getFloat("priceVATExcluded"), 
                    data.getFloat("priceVATIncluded"), 
                    data.getDate("paymentDeadline"), 
                    workflow.readOneWorkflow(data.getInt("workflowId")), 
                    status.readOneDocumentStatus(data.getString("status")));
            }
        } catch (Exception e) {
            System.out.println("Error reading one document: " + e.getMessage());
        }
        return document;
    }

    public ArrayList<Document> readDocumentsMoreExpensiveThanVATExcluded(float price){
        String sqlInstruction = "select * from document where priceVATExcluded > ?";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<Document> allDocuments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setFloat(1, price);
            ResultSet data = preparedStatement.executeQuery();
            Document document;
            while(data.next()){
                document = new Document(type.readOneDocumentType(data.getString("type")), 
                    data.getInt("documentNumber"), 
                    data.getDate("date"), 
                    data.getFloat("priceVATExcluded"), 
                    data.getFloat("priceVATIncluded"), 
                    data.getDate("paymentDeadline"), 
                    workflow.readOneWorkflow(data.getInt("workflowId")), 
                    status.readOneDocumentStatus(data.getString("status")));
                allDocuments.add(document);
            }
        } catch (Exception e) {
            System.out.println("Error reading all documents expensiver than a specific amount: " + e.getMessage());
        }
        return allDocuments;
    }

    public ArrayList<Document> readDocumentsCheaperThanVATExcluded(float price){
        String sqlInstruction = "select * from document where priceVATExcluded < ?";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<Document> allDocuments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setFloat(1, price);
            ResultSet data = preparedStatement.executeQuery();
            Document document;
            while(data.next()){
                document = new Document(type.readOneDocumentType(data.getString("type")), 
                    data.getInt("documentNumber"), 
                    data.getDate("date"), 
                    data.getFloat("priceVATExcluded"), 
                    data.getFloat("priceVATIncluded"), 
                    data.getDate("paymentDeadline"), 
                    workflow.readOneWorkflow(data.getInt("workflowId")), 
                    status.readOneDocumentStatus(data.getString("status")));
                allDocuments.add(document);
            }
        } catch (Exception e) {
            System.out.println("Error reading all documents cheaper than the specific amount: " + e.getMessage());
        }
        return allDocuments;
    }

    public ArrayList<BankInformations> getBankAccountsOfEntityFromDocument(String typeWanted, int numberWanted){
        StringBuilder sqlInstruction = new StringBuilder("select accountUser, IBAN, BICCode from bank_informations");
        sqlInstruction.append(" inner join business_entity on bank_informations.accountUser = business_entity.serialNumber");
        sqlInstruction.append(" inner join workflow on business_entity.serialNumber = workflow.serialNumberEntity");
        sqlInstruction.append(" inner join document on workflow.id = document.workflowId");
        sqlInstruction.append(" where document.type = ? and document.documentNumber = ?");
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<BankInformations> accounts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setString(1, typeWanted);
            preparedStatement.setInt(2, numberWanted);
            ResultSet data = preparedStatement.executeQuery();
            BankInformations account;
            BusinessEntityDataAccess entity = new BusinessEntityDBAccess();
            while(data.next()){
                account = new BankInformations(entity.readBusinessEntity(data.getInt("accountUser")), 
                    data.getString("IBAN"), data.getString("BICCode"));
                accounts.add(account);
            }
        } catch (Exception e) {
            System.out.println("Error getting accounts from a document: " + e.getMessage());
        }
        return accounts;
    }
}
