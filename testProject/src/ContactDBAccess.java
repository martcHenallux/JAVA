import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ContactDBAccess implements ContactDataAccess{
    
    public ContactDBAccess(){}

    public void createContact(BusinessEntity entity, ContactType contactType, String data){
        StringBuilder sqlInstruction = new StringBuilder("insert into contact");
        sqlInstruction.append(" (entity, typeName, data)");
        sqlInstruction.append(" values (?, ?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setInt(1, entity.getSerialNumber());
            preparedStatement.setString(2, contactType.getName());
            preparedStatement.setString(3, data);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error creation contact: " + e.getMessage());
        }
        }

    public void deleteContact(BusinessEntity entity, ContactType contactType, String data){
        StringBuilder sqlInstruction = new StringBuilder("delete from contact");
        sqlInstruction.append(" where entity = ? and typeName = ? and data = ?");
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setInt(1, entity.getSerialNumber());
            preparedStatement.setString(2, contactType.getName());
            preparedStatement.setString(3, data);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error deleting contact: " + e.getMessage());
        }
    }

    public ArrayList<Contact> readContacts(){
        ArrayList<Contact> allContacts = new ArrayList<>();
        String sqlInstruction = "select * from contact";
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntityDataAccess entity = new BusinessEntityDBAccess();
        ContactTypeDataAccess contactType = new ContactTypeDBAccess();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            Contact contact;
            while(data.next()){
                contact = new Contact(entity.readBusinessEntity(data.getInt("entity")),
                    contactType.readOneContactType(data.getString("typeName")), 
                    data.getString("data"));
                allContacts.add(contact);
            }
        } catch (Exception e) {
            System.out.println("Error reading contacts: " + e.getMessage());
        }
        return allContacts;
    }

    public ArrayList<Contact> readContactOfOnePerson(BusinessEntity entity){
        ArrayList<Contact> allContactsOfOnePerson = new ArrayList<>();
        String sqlInstruction = "select * from contact where entity = ?";
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntityDataAccess person = new BusinessEntityDBAccess();
        ContactTypeDataAccess contactType = new ContactTypeDBAccess();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, entity.getSerialNumber());
            ResultSet data = preparedStatement.executeQuery();
            Contact contact;
            while(data.next()){
                contact = new Contact(person.readBusinessEntity(data.getInt("entity")),
                    contactType.readOneContactType(data.getString("typeName")), 
                    data.getString("data"));
                allContactsOfOnePerson.add(contact);
            }
        } catch (Exception e) {
            System.out.println("Error reading contacts: " + e.getMessage());
        }
        return allContactsOfOnePerson;
    }

    public ArrayList<Contact> readSpecificContactsOfAPerson(BusinessEntity person, ContactType type){
        ArrayList<Contact> allSpecificContactsOfAPerson = new ArrayList<>();
        String sqlInstruction = "select * from contact where entity = ? and typeName = ?";
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntityDataAccess entity = new BusinessEntityDBAccess();
        ContactTypeDataAccess contactType = new ContactTypeDBAccess();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, person.getSerialNumber());
            preparedStatement.setString(2, type.getName());
            ResultSet data = preparedStatement.executeQuery();
            Contact contact;
            while(data.next()){
                contact = new Contact(entity.readBusinessEntity(data.getInt("entity")),
                    contactType.readOneContactType(data.getString("typeName")), 
                    data.getString("data"));
                allSpecificContactsOfAPerson.add(contact);
            }
        } catch (Exception e) {
            System.out.println("Error reading contacts: " + e.getMessage());
        }
        return allSpecificContactsOfAPerson;
    }
}
