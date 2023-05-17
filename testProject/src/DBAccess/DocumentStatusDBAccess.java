package DBAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DataAccess.DocumentStatusDataAccess;
import Model.DocumentStatus;

public class DocumentStatusDBAccess implements DocumentStatusDataAccess{

    public DocumentStatusDBAccess(){}

    public void createDocumentStatus(String code, String description) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("insert into document_status");
        sqlInstruction.append(" (code, description)");
        sqlInstruction.append(" values (?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, description);
            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<DocumentStatus> readAllDocumentStatus() throws SQLException{
        String sqlInstruction = "select * from document_status";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<DocumentStatus> allStatus = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            try (ResultSet data = preparedStatement.executeQuery()){
                DocumentStatus status;
                while(data.next()){
                    status = new DocumentStatus(data.getString("code"), 
                        data.getString("description"));
                    allStatus.add(status);
                }  
            }
        }
        return allStatus;
    }

    public DocumentStatus readOneDocumentStatus(String code) throws SQLException{
        String sqlInstruction = "select * from document_status where code = ?";
        Connection connection = SingletonConnexion.getInstance();
        DocumentStatus status = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setString(1, code);
            try (ResultSet data = preparedStatement.executeQuery()){
                if(data.next()){
                    status = new DocumentStatus(data.getString("code"), 
                        data.getString("description"));
                }   
            }
        }
        return status;
    }
    
}
