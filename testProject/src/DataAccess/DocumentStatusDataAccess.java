package DataAccess;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.DocumentStatus;

public interface DocumentStatusDataAccess {
    void createDocumentStatus(String code, String description) throws SQLException;
    ArrayList<DocumentStatus> readAllDocumentStatus() throws SQLException;
    DocumentStatus readOneDocumentStatus(String code) throws SQLException;
}
