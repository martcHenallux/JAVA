import java.util.ArrayList;

public interface LocalityDataAccess {
    void createLocality(String country, String postalCode, String name);
    ArrayList<Locality> readLocalities();
    Locality readOneLocality(int id);
    void localityIn(String country, String postalCode, String name);
}
