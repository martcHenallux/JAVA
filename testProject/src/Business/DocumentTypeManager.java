package Business;

import java.sql.SQLException;
import java.util.ArrayList;

import DBAccess.DocumentTypeDBAccess;
import Model.DocumentType;

public class DocumentTypeManager {
    
    DocumentTypeDBAccess dao;

    public DocumentTypeManager(){
        dao = new DocumentTypeDBAccess();
    }

    private void createDocumentType(String code, String description) throws SQLException{
        dao.createDocumentType(code, description);
    }

    public ArrayList<DocumentType> readDocumentTypes() throws SQLException{
        return dao.readDocumentTypes();
    }

    public DocumentType readOneDocumentType(String code) throws SQLException{
        return dao.readOneDocumentType(code);
    }

    public void tryCreateDocumentType(String code, String description) throws SQLException{
        ArrayList<DocumentType> allTypes = new ArrayList<>();
        allTypes = readDocumentTypes();
        Boolean isIn = false;
        for (DocumentType documentType : allTypes) {
            if(documentType.getCode().toLowerCase().equals(code.toLowerCase())){
                isIn = true;
            }
        }
        if(!isIn){
            createDocumentType(code, description);
        }
    }
}
