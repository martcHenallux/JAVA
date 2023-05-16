package DataAccess;
import java.util.ArrayList;

import Model.DocumentType;

public interface DocumentTypeDataAccess {
    void createDocumentType(String code, String description);
    ArrayList<DocumentType> readDocumentTypes();
    DocumentType readOneDocumentType(String code);
}
