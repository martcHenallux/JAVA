package DBAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DataAccess.CountryDataAccess;
import Model.Country;

public class CountryDBAccess implements CountryDataAccess{

    public CountryDBAccess(){}

    public void createCountry(String name) throws SQLException{
        String sqlInstruction = "insert into country (name) values (?)";
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setString(1, name.toUpperCase());
            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<Country> readCountries() throws SQLException{
        String sqlInstruction = "select * from country";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<Country> allCountries = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            try (ResultSet data = preparedStatement.executeQuery()){
                Country country;
                while(data.next()){
                    country = new Country(data.getString("name"));
                    allCountries.add(country);
                }   
            }
        }
        return allCountries;
    }
}
