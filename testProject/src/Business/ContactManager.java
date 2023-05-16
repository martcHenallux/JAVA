package Business;

import java.util.ArrayList;

import DBAccess.ContactDBAccess;
import Model.BusinessEntity;
import Model.Contact;
import Model.ContactType;

public class ContactManager {
    
    ContactDBAccess dao;

    public ContactManager(){
        dao = new ContactDBAccess();
    }

    private void createContact(BusinessEntity entity, ContactType contactType, String data){
        dao.createContact(entity.getSerialNumber(), contactType.getName(), data);
    }

    public void deleteContact(BusinessEntity entity, ContactType contactType, String data){
        dao.deleteContact(entity.getSerialNumber(), contactType.getName(), data);
    }

    public ArrayList<Contact> readContacts(){
        return dao.readContacts();
    }

    public ArrayList<Contact> readContactOfOnePerson(BusinessEntity entity){
        return dao.readContactOfOnePerson(entity.getSerialNumber());
    }

    public ArrayList<Contact> readSpecificContactsOfAPerson(BusinessEntity entity, ContactType contactType){
        return dao.readSpecificContactsOfAPerson(entity.getSerialNumber(), contactType.getName());
    }

    public void tryCreateContact(BusinessEntity entity, ContactType contactType, String data){
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts = readContacts();
        Boolean isIn = false;
        for (Contact contact : contacts) {
            if(contact.getContactType().getName().toLowerCase().equals(contactType.getName().toLowerCase()) && 
                contact.getData().toLowerCase().equals(data.toLowerCase())){
                    isIn = true;
            }
        }
        if(!isIn){
            createContact(entity, contactType, data);
        }
    }
}
