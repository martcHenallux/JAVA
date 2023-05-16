package DataAccess;

import java.util.ArrayList;

import Model.Country;

public interface CountryDataAccess {
    void createCountry(String name);
    ArrayList<Country> readCountries();
}
