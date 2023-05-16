package DBAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DataAccess.ContactTypeDataAccess;
import Model.ContactType;

public class ContactTypeDBAccess implements ContactTypeDataAccess{
    
    public ContactTypeDBAccess(){}

    public void createContactType(String name){
        String sqlInstruction = "insert into contact_type (name) values (?)";
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, name.toUpperCase());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public ArrayList<ContactType> readContactTypes(){
        String sqlInstruction = "select * from contact_type";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<ContactType> allContactTypes = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            ContactType contactType;
            while(data.next()){
                contactType = new ContactType(data.getString("name"));
                allContactTypes.add(contactType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return allContactTypes;
    }

    public ContactType readOneContactType(String name){
        StringBuilder sqlInstruction = new StringBuilder("select * from contact_type");
        sqlInstruction.append(" where name = ?");
        Connection connection = SingletonConnexion.getInstance();
        ContactType type = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setString(1, name);
            ResultSet data = preparedStatement.executeQuery();
            if (data.next()){
                type = new ContactType(name);
            }
        } catch (Exception e) {
            System.out.println("Error reading one contact type: " + e.getMessage());
        }
        return type;
    }
}
