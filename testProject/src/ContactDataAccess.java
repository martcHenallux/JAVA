import java.util.ArrayList;

public interface ContactDataAccess {
    void createContact(BusinessEntity entity, ContactType contactType, String data);
    void deleteContact(BusinessEntity entity, ContactType contactType, String data);
    ArrayList<Contact> readContacts();
    ArrayList<Contact> readContactOfOnePerson(BusinessEntity entity);
    ArrayList<Contact> readSpecificContactsOfAPerson(BusinessEntity entity, ContactType contactType);
}
