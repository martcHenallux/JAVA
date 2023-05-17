package DBAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DataAccess.BusinessEntityDataAccess;
import DataAccess.ContactDataAccess;
import Model.Contact;
import DataAccess.ContactTypeDataAccess;

public class ContactDBAccess implements ContactDataAccess{
    
    public ContactDBAccess(){}

    public void createContact(int entityId, String contactType, String data) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("insert into contact");
        sqlInstruction.append(" (entity, typeName, data)");
        sqlInstruction.append(" values (?, ?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setInt(1, entityId);
            preparedStatement.setString(2, contactType);
            preparedStatement.setString(3, data);
            preparedStatement.executeUpdate();
        }
    }

    public void deleteContact(int entityId, String contactType, String data) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("delete from contact");
        sqlInstruction.append(" where entity = ? and typeName = ? and data = ?");
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setInt(1, entityId);
            preparedStatement.setString(2, contactType);
            preparedStatement.setString(3, data);
            preparedStatement.executeUpdate();
        }
    }

    public void deleteAllContacts(int entityId) throws SQLException{
        String sqlInstruction = "delete from contact where entity = ?";
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setInt(1, entityId);
            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<Contact> readContacts() throws SQLException{
        ArrayList<Contact> allContacts = new ArrayList<>();
        String sqlInstruction = "select * from contact";
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntityDataAccess entity = new BusinessEntityDBAccess();
        ContactTypeDataAccess contactType = new ContactTypeDBAccess();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            try (ResultSet data = preparedStatement.executeQuery()){
                Contact contact;
                while(data.next()){
                    contact = new Contact(entity.readBusinessEntity(data.getInt("entity")),
                        contactType.readOneContactType(data.getString("typeName")), 
                        data.getString("data"));
                    allContacts.add(contact);
                }  
            }
        }
        return allContacts;
    }

    public ArrayList<Contact> readContactOfOnePerson(int entityId) throws SQLException{
        ArrayList<Contact> allContactsOfOnePerson = new ArrayList<>();
        String sqlInstruction = "select * from contact where entity = ?";
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntityDataAccess person = new BusinessEntityDBAccess();
        ContactTypeDataAccess contactType = new ContactTypeDBAccess();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setInt(1, entityId);
            try (ResultSet data = preparedStatement.executeQuery()){
                Contact contact;
                while(data.next()){
                    contact = new Contact(person.readBusinessEntity(data.getInt("entity")),
                        contactType.readOneContactType(data.getString("typeName")), 
                        data.getString("data"));
                    allContactsOfOnePerson.add(contact);
                }   
            }
        }
        return allContactsOfOnePerson;
    }

    public ArrayList<Contact> readSpecificContactsOfAPerson(int personId, String type) throws SQLException{
        ArrayList<Contact> allSpecificContactsOfAPerson = new ArrayList<>();
        String sqlInstruction = "select * from contact where entity = ? and typeName = ?";
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntityDataAccess entity = new BusinessEntityDBAccess();
        ContactTypeDataAccess contactType = new ContactTypeDBAccess();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setInt(1, personId);
            preparedStatement.setString(2, type);
            try (ResultSet data = preparedStatement.executeQuery()){
                Contact contact;
                while(data.next()){
                    contact = new Contact(entity.readBusinessEntity(data.getInt("entity")),
                        contactType.readOneContactType(data.getString("typeName")), 
                        data.getString("data"));
                    allSpecificContactsOfAPerson.add(contact);
                }   
            }
        }
        return allSpecificContactsOfAPerson;
    }
}
