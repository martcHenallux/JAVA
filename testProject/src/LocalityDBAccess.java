import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LocalityDBAccess implements LocalityDataAccess {

    private CountryDataAccess countryDataAccess = new CountryDBAccess();

    public LocalityDBAccess(){}

    public LocalityDBAccess(CountryDBAccess country){
        this.countryDataAccess = country;
    }

    public void createLocality(String country, String postalCode, String name){
        String sqlInstruction = "Insert into locality (country, postalCode, name) values (?, ?, ?)";
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            countryDataAccess.countryIn(country);
            preparedStatement.setString(1, country.toUpperCase());
            preparedStatement.setString(2, postalCode);
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
            data.next();
            locality = new Locality(null, new Country(data.getString("country")), data.getString("postalCode"), data.getString("name"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return locality;
    }

    public void localityIn(String country, String postalCode, String name){
        ArrayList<Locality> localities = new ArrayList<>();
        localities = readLocalities();
        boolean isIn = false;
        for (Locality locality : localities) {
            if(locality.getCountry().getName().equals(country.toUpperCase()) && locality.getPostalCode().equals(postalCode.toUpperCase()) && locality.getName().equals(name.toUpperCase())){
                isIn = true;
            }
        }
        if(!isIn){
            createLocality(country, postalCode, name);
            System.out.println("Localité ajouté");
        }
    }
}
