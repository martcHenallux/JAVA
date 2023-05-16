package DataAccess;
import java.util.ArrayList;

import Model.ContactType;

public interface ContactTypeDataAccess {
    void createContactType(String name);
    ArrayList<ContactType> readContactTypes();
    ContactType readOneContactType(String name);
}
