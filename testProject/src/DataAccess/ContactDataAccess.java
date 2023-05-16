package DataAccess;
import java.util.ArrayList;

import Model.Contact;

public interface ContactDataAccess {
    void createContact(int entityId, String contactType, String data);
    void deleteContact(int entityId, String contactType, String data);
    void deleteAllContacts(int entityId);
    ArrayList<Contact> readContacts();
    ArrayList<Contact> readContactOfOnePerson(int entityId);
    ArrayList<Contact> readSpecificContactsOfAPerson(int entityId, String contactType);
}
