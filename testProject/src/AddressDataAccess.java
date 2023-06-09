
import java.util.ArrayList;

public interface AddressDataAccess {
    void createAddress(int idLocality, String street, int number);
    ArrayList<Address> readAddresses();
    Address readOneAddress(int id);
    void addressIn(Locality locality, String street, int number);
}
