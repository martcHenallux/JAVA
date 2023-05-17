package DBAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DataAccess.LocalityDataAccess;
import Model.Country;
import Model.Locality;

public class LocalityDBAccess implements LocalityDataAccess {

    public LocalityDBAccess(){}

    public void createLocality(String country, String postalCode, String name) throws SQLException{
        String sqlInstruction = "Insert into locality (country, postalCode, name) values (?, ?, ?)";
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setString(1, country.toUpperCase());
            preparedStatement.setString(2, postalCode.toUpperCase());
            preparedStatement.setString(3, name.toUpperCase());
            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<Locality> readLocalities() throws SQLException{
        String sqlInstruction = "select * from locality";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<Locality> allLocalities = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            try (ResultSet data = preparedStatement.executeQuery()){
                Locality locality;
                while(data.next()){
                    locality = new Locality(data.getInt("id"), 
                        new Country(data.getString("country")), 
                        data.getString("postalCode"), data.getString("name"));
                    allLocalities.add(locality);
                }
            }
        }
        return allLocalities;
    }

    public Locality readOneLocality(int id) throws SQLException{
        String sqlInstruction = "select * from locality where id = ?";
        Connection connection = SingletonConnexion.getInstance();
        Locality locality = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setInt(1, id);
            try (ResultSet data = preparedStatement.executeQuery()){
                if (data.next()){
                    locality = new Locality(null, 
                        new Country(data.getString("country")), 
                        data.getString("postalCode"), data.getString("name"));
                }
            }
        }
        return locality;
    }

    public Locality readOneLocality(String postalCode, String country, String name) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("Select *");
        sqlInstruction.append(" from locality");
        sqlInstruction.append(" where postalCode = ? and country = ? and name = ?");
        Connection connection = SingletonConnexion.getInstance();
        Locality locality = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setString(1, postalCode);
            preparedStatement.setString(2, country);
            preparedStatement.setString(3, name);
            try (ResultSet data = preparedStatement.executeQuery()){
                if(data.next()){
                    locality = new Locality(data.getInt("id"), 
                        new Country(data.getString("country")), data.getString("postalCode"), 
                        data.getString("name"));
                }
            }
        }
        return locality;
    }
}
