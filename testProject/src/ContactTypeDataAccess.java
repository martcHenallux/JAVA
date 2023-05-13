import java.util.ArrayList;

public interface ContactTypeDataAccess {
    void createContactType(String name);
    ArrayList<ContactType> readContactTypes();
    ContactType readOneContactType(String name);
}
