package DataAccess;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.ContactType;

public interface ContactTypeDataAccess {
    void createContactType(String name) throws SQLException;
    ArrayList<ContactType> readContactTypes() throws SQLException;
    ContactType readOneContactType(String name) throws SQLException;
}
