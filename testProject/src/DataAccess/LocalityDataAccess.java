package DataAccess;
import java.util.ArrayList;

import Model.Locality;

public interface LocalityDataAccess {
    void createLocality(String country, String postalCode, String name);
    ArrayList<Locality> readLocalities();
    Locality readOneLocality(int id);
}
