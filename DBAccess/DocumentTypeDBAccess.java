package DBAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DataAccess.DocumentTypeDataAccess;
import Model.DocumentType;

public class DocumentTypeDBAccess implements DocumentTypeDataAccess{
    
    public DocumentTypeDBAccess(){}

    public void createDocumentType(String code, String description){
        StringBuilder sqlInstruction = new StringBuilder("insert into document_type");
        sqlInstruction.append(" (code, description)");
        sqlInstruction.append(" values (?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, description);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error creating document type: " + e.getMessage());
        }
    }

    public ArrayList<DocumentType> readDocumentTypes(){
        String sqlInstruction = "select * from document_type";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<DocumentType> allTypes = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            DocumentType type;
            while(data.next()){
                type = new DocumentType(data.getString("code"), data.getString("description"));
                allTypes.add(type);
            }
        } catch (Exception e) {
            System.out.println("Error reading all document types: " + e.getMessage());
        }
        return allTypes;
    }

    public DocumentType readOneDocumentType(String code){
        String sqlInstruction = "select * from document_type where code = ?";
        Connection connection = SingletonConnexion.getInstance();
        DocumentType type = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, code);
            ResultSet data = preparedStatement.executeQuery();
            if(data.next()){
                type = new DocumentType(data.getString("code"), data.getString("description"));
            }
        } catch (Exception e) {
            System.out.println("Error reading one document type: " + e.getMessage());
        }
        return type;
    }
}
