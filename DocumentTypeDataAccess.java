import java.util.ArrayList;

public interface DocumentTypeDataAccess {
    void createDocumentType(String code, String description);
    ArrayList<DocumentType> readDocumentTypes();
    DocumentType readOneDocumentType(String code);
}
