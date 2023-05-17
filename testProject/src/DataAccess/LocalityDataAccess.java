package DataAccess;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Locality;

public interface LocalityDataAccess {
    void createLocality(String country, String postalCode, String name) throws SQLException;
    ArrayList<Locality> readLocalities() throws SQLException;
    Locality readOneLocality(int id) throws SQLException;
    Locality readOneLocality(String postalCode, String country, String name) throws SQLException;
}
