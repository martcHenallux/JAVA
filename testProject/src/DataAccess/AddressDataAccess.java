package DataAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import Model.Address;

public interface AddressDataAccess {
    void createAddress(int idLocality, String street, int number) throws SQLException;
    ArrayList<Address> readAddresses() throws SQLException;
    Address readOneAddress(int id) throws SQLException;
    Address readOneAddress(int localityId, String street, int number) throws SQLException;
    ArrayList<Address> readAddressesByLocality(int idLocality) throws SQLException;
}
