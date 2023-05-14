import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CountryDBAccess implements CountryDataAccess{

    public CountryDBAccess(){}

    @Override
    public void createCountry(String name){
        String sqlInstruction = "insert into country (name) values (?)";
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, name.toUpperCase());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Country> readCountries(){
        String sqlInstruction = "select * from country";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<Country> allCountries = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            Country country;
            while(data.next()){
                country = new Country(data.getString("name"));
                allCountries.add(country);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return allCountries;
    }
}
