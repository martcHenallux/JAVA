package DBAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DataAccess.BusinessEntityDataAccess;
import DataAccess.ContactDataAccess;
import Model.Contact;
import DataAccess.ContactTypeDataAccess;

public class ContactDBAccess implements ContactDataAccess{
    
    public ContactDBAccess(){}

    public void createContact(int entityId, String contactType, String data){
        StringBuilder sqlInstruction = new StringBuilder("insert into contact");
        sqlInstruction.append(" (entity, typeName, data)");
        sqlInstruction.append(" values (?, ?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setInt(1, entityId);
            preparedStatement.setString(2, contactType);
            preparedStatement.setString(3, data);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error creation contact: " + e.getMessage());
        }
        }

    public void deleteContact(int entityId, String contactType, String data){
        StringBuilder sqlInstruction = new StringBuilder("delete from contact");
        sqlInstruction.append(" where entity = ? and typeName = ? and data = ?");
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setInt(1, entityId);
            preparedStatement.setString(2, contactType);
            preparedStatement.setString(3, data);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error deleting contact: " + e.getMessage());
        }
    }

    public void deleteAllContacts(int entityId){
        String sqlInstruction = "delete from contact where entity = ?";
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, entityId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error deleting all contacts: " + e.getMessage());
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

    public ArrayList<Contact> readContactOfOnePerson(int entityId){
        ArrayList<Contact> allContactsOfOnePerson = new ArrayList<>();
        String sqlInstruction = "select * from contact where entity = ?";
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntityDataAccess person = new BusinessEntityDBAccess();
        ContactTypeDataAccess contactType = new ContactTypeDBAccess();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, entityId);
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

    public ArrayList<Contact> readSpecificContactsOfAPerson(int personId, String type){
        ArrayList<Contact> allSpecificContactsOfAPerson = new ArrayList<>();
        String sqlInstruction = "select * from contact where entity = ? and typeName = ?";
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntityDataAccess entity = new BusinessEntityDBAccess();
        ContactTypeDataAccess contactType = new ContactTypeDBAccess();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, personId);
            preparedStatement.setString(2, type);
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
