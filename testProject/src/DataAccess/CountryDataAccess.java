package DataAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import Model.Country;

public interface CountryDataAccess {
    void createCountry(String name) throws SQLException;
    ArrayList<Country> readCountries() throws SQLException;
}
