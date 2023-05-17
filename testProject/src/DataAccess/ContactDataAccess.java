package DataAccess;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Contact;

public interface ContactDataAccess {
    void createContact(int entityId, String contactType, String data) throws SQLException;
    void deleteContact(int entityId, String contactType, String data) throws SQLException;
    void deleteAllContacts(int entityId) throws SQLException;
    ArrayList<Contact> readContacts() throws SQLException;
    ArrayList<Contact> readContactOfOnePerson(int entityId) throws SQLException;
    ArrayList<Contact> readSpecificContactsOfAPerson(int entityId, String contactType) throws SQLException;
}
