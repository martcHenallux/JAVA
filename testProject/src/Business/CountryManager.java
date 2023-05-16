package Business;

import java.util.ArrayList;

import DBAccess.CountryDBAccess;
import Model.Country;

public class CountryManager {
    
    CountryDBAccess dao;

    public CountryManager(){
        dao = new CountryDBAccess();
    }

    private void createCountry(String name){
        dao.createCountry(name);
    }

    public ArrayList<Country> readCountries(){
        return dao.readCountries();
    }

    public void tryCreateCountry(String name){
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
