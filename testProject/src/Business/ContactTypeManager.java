package Business;

import java.util.ArrayList;

import DBAccess.ContactTypeDBAccess;
import Model.ContactType;

public class ContactTypeManager {
    
    ContactTypeDBAccess dao;

    public ContactTypeManager (){
        dao = new ContactTypeDBAccess();
    }

    private void createContactType(String name){
        dao.createContactType(name);
    }

    public ArrayList<ContactType> readContactTypes(){
        return dao.readContactTypes();
    }

    public ContactType readOneContactType(String name){
        return dao.readOneContactType(name);
    }

    public void tryCreateContactType(String name){
        ArrayList<ContactType> types = new ArrayList<>();
        types = readContactTypes();
        Boolean isIn = false;
        for (ContactType contactType : types) {
            if (contactType.getName().toLowerCase().equals(name.toLowerCase())){
                isIn = true;
            }
        }
        if(!isIn){
            createContactType(name);
        }
    }
}
