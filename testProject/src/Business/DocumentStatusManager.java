package Business;

import java.util.ArrayList;

import DBAccess.DocumentStatusDBAccess;
import Model.DocumentStatus;

public class DocumentStatusManager {
    
    DocumentStatusDBAccess dao;

    public DocumentStatusManager(){
        dao = new DocumentStatusDBAccess();
    }

    private void createDocumentStatus(String code, String description){
        dao.createDocumentStatus(code, description);
    }

    public ArrayList<DocumentStatus> readAllDocumentStatus(){
        return dao.readAllDocumentStatus();
    }

    public DocumentStatus readOneDocumentStatus(String code){
        return dao.readOneDocumentStatus(code);
    }
    
    public void DocumentStatusIsIn(String code, String description){
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
