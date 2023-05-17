package Business;

import java.sql.SQLException;
import java.util.ArrayList;

import DBAccess.DocumentStatusDBAccess;
import Model.DocumentStatus;

public class DocumentStatusManager {
    
    DocumentStatusDBAccess dao;

    public DocumentStatusManager(){
        dao = new DocumentStatusDBAccess();
    }

    private void createDocumentStatus(String code, String description) throws SQLException{
        dao.createDocumentStatus(code, description);
    }

    public ArrayList<DocumentStatus> readAllDocumentStatus() throws SQLException{
        return dao.readAllDocumentStatus();
    }

    public DocumentStatus readOneDocumentStatus(String code) throws SQLException{
        return dao.readOneDocumentStatus(code);
    }
    
    public void tryCreateDocumentStatus(String code, String description) throws SQLException{
        ArrayList<DocumentStatus> allStatus = new ArrayList<>();
        allStatus = readAllDocumentStatus();
        Boolean isIn = false;
        for (DocumentStatus documentStatus : allStatus) {
            if(documentStatus.getCode().toLowerCase().equals(code.toLowerCase())){
                isIn = true;
            }
        }
        if(!isIn){
            createDocumentStatus(code, description);
        }
    }
}
