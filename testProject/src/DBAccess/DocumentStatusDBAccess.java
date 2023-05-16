package DBAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DataAccess.DocumentStatusDataAccess;
import Model.DocumentStatus;

public class DocumentStatusDBAccess implements DocumentStatusDataAccess{

    public DocumentStatusDBAccess(){}

    public void createDocumentStatus(String code, String description){
        StringBuilder sqlInstruction = new StringBuilder("insert into document_status");
        sqlInstruction.append(" (code, description)");
        sqlInstruction.append(" values (?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, description);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error creating document status: " + e.getMessage());
        }
    }

    public ArrayList<DocumentStatus> readAllDocumentStatus(){
        String sqlInstruction = "select * from document_status";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<DocumentStatus> allStatus = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            DocumentStatus status;
            while(data.next()){
                status = new DocumentStatus(data.getString("code"), data.getString("description"));
                allStatus.add(status);
            }
        } catch (Exception e) {
            System.out.println("Error reading all document status: " + e.getMessage());
        }
        return allStatus;
    }

    public DocumentStatus readOneDocumentStatus(String code){
        String sqlInstruction = "select * from document_status where code = ?";
        Connection connection = SingletonConnexion.getInstance();
        DocumentStatus status = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, code);
            ResultSet data = preparedStatement.executeQuery();
            if(data.next()){
                status = new DocumentStatus(data.getString("code"), data.getString("description"));
            }
        } catch (Exception e) {
            System.out.println("Error reading one document status: " + e.getMessage());
        }
        return status;
    }
    
}
