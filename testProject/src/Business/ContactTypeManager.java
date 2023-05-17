package Business;

import java.sql.SQLException;
import java.util.ArrayList;

import DBAccess.ContactTypeDBAccess;
import Model.ContactType;

public class ContactTypeManager {
    
    ContactTypeDBAccess dao;

    public ContactTypeManager (){
        dao = new ContactTypeDBAccess();
    }

    private void createContactType(String name) throws SQLException{
        dao.createContactType(name);
    }

    public ArrayList<ContactType> readContactTypes() throws SQLException{
        return dao.readContactTypes();
    }

    public ContactType readOneContactType(String name) throws SQLException{
        return dao.readOneContactType(name);
    }

    public void tryCreateContactType(String name) throws SQLException{
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
