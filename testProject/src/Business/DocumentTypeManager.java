package Business;

import java.util.ArrayList;

import DBAccess.DocumentTypeDBAccess;
import Model.DocumentType;

public class DocumentTypeManager {
    
    DocumentTypeDBAccess dao;

    public DocumentTypeManager(){
        dao = new DocumentTypeDBAccess();
    }

    private void createDocumentType(String code, String description){
        dao.createDocumentType(code, description);
    }

    public ArrayList<DocumentType> readDocumentTypes(){
        return dao.readDocumentTypes();
    }

    public DocumentType readOneDocumentType(String code){
        return dao.readOneDocumentType(code);
    }

    public void tryCreateDocumentType(String code, String description){
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
