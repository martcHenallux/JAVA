package DataAccess;
import java.util.ArrayList;

import Model.DocumentStatus;

public interface DocumentStatusDataAccess {
    void createDocumentStatus(String code, String description);
    ArrayList<DocumentStatus> readAllDocumentStatus();
    DocumentStatus readOneDocumentStatus(String code);
}
