package DataAccess;

import java.util.ArrayList;

import Model.Address;

public interface AddressDataAccess {
    void createAddress(int idLocality, String street, int number);
    ArrayList<Address> readAddresses();
    Address readOneAddress(int id);
    ArrayList<Address> readAddressesByLocality(int idLocality);
}
