package DataAccess;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.DocumentType;

public interface DocumentTypeDataAccess {
    void createDocumentType(String code, String description) throws SQLException;
    ArrayList<DocumentType> readDocumentTypes() throws SQLException;
    DocumentType readOneDocumentType(String code) throws SQLException;
}
