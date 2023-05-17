package DBAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DataAccess.AddressDataAccess;
import Model.Address;
import Model.Locality;
import DataAccess.LocalityDataAccess;

public class AddressDBAccess implements AddressDataAccess{
    
    private LocalityDataAccess localityDataAccess = new LocalityDBAccess();

    public AddressDBAccess(){}

    public void createAddress(int idLocality, String street, int number) throws SQLException{
        String sqlInstruction = "Insert into address (idLocality, street, number) values (?, ?, ?)";
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setInt(1, idLocality);
            preparedStatement.setString(2, street.toLowerCase());
            preparedStatement.setInt(3, number);
            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<Address> readAddresses() throws SQLException{
        String sqlInstruction = "select * from address";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<Address> allAddresses = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            try (ResultSet data = preparedStatement.executeQuery()){
                Address address;
                Locality locality;
                while(data.next()){
                    locality = localityDataAccess.readOneLocality(data.getInt("idLocality"));
                    address = new Address(data.getInt("id"), locality, data.getString("street"), data.getInt("number"));
                    allAddresses.add(address);
                }   
            }
        }
        return allAddresses;
    }

    public Address readOneAddress(int id) throws SQLException{
        String sqlInstruction = "select * from address where id = ?";
        Connection connection = SingletonConnexion.getInstance();
        Address address = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setInt(1, id);
            try (ResultSet data = preparedStatement.executeQuery()){
                if(data.next()){
                    address = new Address(data.getInt("id"),
                    localityDataAccess.readOneLocality(data.getInt("idLocality")),
                    data.getString("street"), 
                    data.getInt("number"));
                }   
            }
        }
        return address;
    }

    public Address readOneAddress(int localityId, String street, int number) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("select * from address");
        sqlInstruction.append(" where idLocality = ? and street = ? and number = ?");
        Connection connection = SingletonConnexion.getInstance();
        Address address = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setInt(1, localityId);
            preparedStatement.setString(2, street);
            preparedStatement.setInt(3, number);
            try (ResultSet data = preparedStatement.executeQuery()){
                if(data.next()){
                    address = new Address(data.getInt("id"), 
                        localityDataAccess.readOneLocality(data.getInt("idLocality")), 
                        data.getString("street"), data.getInt("number"));
                }   
            }
        }
        return address;
    }

    public ArrayList<Address> readAddressesByLocality(int idLocality) throws SQLException{
        String sqlInstruction = "select * from address where idLocality = ?";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<Address> allAddresses = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setInt(1, idLocality);
            try (ResultSet data = preparedStatement.executeQuery()){
                Address address;
                Locality locality;
                while(data.next()){
                    locality = localityDataAccess.readOneLocality(data.getInt("idLocality"));
                    address = new Address(data.getInt("id"), locality, data.getString("street"), data.getInt("number"));
                    allAddresses.add(address);
                }   
            }
        }
        return allAddresses;
    }
}
