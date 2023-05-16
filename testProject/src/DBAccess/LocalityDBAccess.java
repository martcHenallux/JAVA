package DBAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DataAccess.LocalityDataAccess;
import Model.Country;
import Model.Locality;

public class LocalityDBAccess implements LocalityDataAccess {

    public LocalityDBAccess(){}

    public void createLocality(String country, String postalCode, String name){
        String sqlInstruction = "Insert into locality (country, postalCode, name) values (?, ?, ?)";
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, country.toUpperCase());
            preparedStatement.setString(2, postalCode.toUpperCase());
            preparedStatement.setString(3, name.toUpperCase());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public ArrayList<Locality> readLocalities(){
        String sqlInstruction = "select * from locality";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<Locality> allLocalities = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            Locality locality;
            while(data.next()){
                locality = new Locality(data.getInt("id"), new Country(data.getString("country")), data.getString("postalCode"), data.getString("name"));
                allLocalities.add(locality);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return allLocalities;
    }

    public Locality readOneLocality(int id){
        String sqlInstruction = "select * from locality where id = ?";
        Connection connection = SingletonConnexion.getInstance();
        Locality locality = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, id);
            ResultSet data = preparedStatement.executeQuery();
            if (data.next()){
                locality = new Locality(null, new Country(data.getString("country")), data.getString("postalCode"), data.getString("name"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return locality;
    }

    public Locality readOneLocality(String postalCode, String country, String name){
        StringBuilder sqlInstruction = new StringBuilder("Select *");
        sqlInstruction.append(" from locality");
        sqlInstruction.append(" where postalCode = ? and country = ? and name = ?");
        Connection connection = SingletonConnexion.getInstance();
        Locality locality = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setString(1, postalCode);
            preparedStatement.setString(2, country);
            preparedStatement.setString(3, name);
            ResultSet data = preparedStatement.executeQuery();
            if(data.next()){
                locality = new Locality(data.getInt("id"), 
                    new Country(data.getString("country")), data.getString("postalCode"), 
                    data.getString("name"));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return locality;
    }
}
