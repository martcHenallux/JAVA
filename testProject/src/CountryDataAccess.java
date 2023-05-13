
import java.util.ArrayList;

public interface CountryDataAccess {
    void createCountry(String name);
    ArrayList<Country> readCountries();
}
