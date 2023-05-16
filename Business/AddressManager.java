package Business;

import java.util.ArrayList;

import DBAccess.AddressDBAccess;
import Model.Address;
import Model.Locality;

public class AddressManager {
    AddressDBAccess dao;

    public AddressManager(){
        dao = new AddressDBAccess();
    };

    private void createNewAddress(Locality locality, String street, int number){
        dao.createAddress(locality.getId(), street, number);
    }

    public Address readOneAddress(int id){
        return dao.readOneAddress(id);
    }

    public ArrayList<Address> readAddresses(){
        return dao.readAddresses();
    }

    public ArrayList<Address> readAddressesByLocality(Locality locality){
        ArrayList<Address> addresses = new ArrayList<>();
        addresses = dao.readAddressesByLocality(locality.getId());
        if(addresses.isEmpty()){
            System.out.println("The locality doesn't have any address");
        }
        return addresses;
    }

    public void addressIsIn(Locality locality, String street, int number){
        ArrayList<Address> addresses = new ArrayList<>();
        addresses = readAddresses();
        boolean isIn = false;
        for (Address address : addresses) {
            if (address.getLocality().getId() == locality.getId() && 
                address.getStreet().equals(street.toLowerCase()) && address.getNumber() == number){
                    isIn = true;
            }
        }
        if(!isIn){
            createNewAddress(locality, street, number);
        }
    }
}
