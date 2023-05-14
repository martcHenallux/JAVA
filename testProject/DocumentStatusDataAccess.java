import java.util.ArrayList;

public interface DocumentStatusDataAccess {
    void createDocumentStatus(String code, String description);
    ArrayList<DocumentStatus> readAllDocumentStatus();
    DocumentStatus readOneDocumentStatus(String code);
}
