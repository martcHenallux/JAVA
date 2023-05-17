package Business;

import java.sql.SQLException;
import java.util.ArrayList;

import DBAccess.CountryDBAccess;
import Model.Country;

public class CountryManager {
    
    CountryDBAccess dao;

    public CountryManager(){
        dao = new CountryDBAccess();
    }

    private void createCountry(String name) throws SQLException{
        dao.createCountry(name);
    }

    public ArrayList<Country> readCountries() throws SQLException{
        return dao.readCountries();
    }

    public void tryCreateCountry(String name) throws SQLException{
        ArrayList<Country> allCountries = new ArrayList<>();
        allCountries = readCountries();
        Boolean isIn = false;
        for (Country country : allCountries) {
            if (country.getName().equals(name.toUpperCase())){
                isIn = true;
            }
        }
        if(!isIn){
            createCountry(name);
        }
    }
}
