package Business;

import java.sql.SQLException;
import java.util.ArrayList;

import DBAccess.LocalityDBAccess;
import Model.Country;
import Model.Locality;

public class LocalityManager {
    
    LocalityDBAccess dao;

    public LocalityManager(){
        dao = new LocalityDBAccess();
    }

    private void createLocality(String country, String postalCode, String name) throws SQLException{
        dao.createLocality(country, postalCode, name);
    }

    public ArrayList<Locality> readLocalities() throws SQLException{
        return dao.readLocalities();
    }

    public Locality readOneLocality(int id) throws SQLException{
        return dao.readOneLocality(id);
    }

    public Locality readOnLocality(String postalCode, Country country, String name) throws SQLException{
        return dao.readOneLocality(postalCode, country.getName(), name);
    }

    public void tryCreateLocality(String country, String postalCode, String name) throws SQLException{
        ArrayList<Locality> localities = new ArrayList<>();
        localities = readLocalities();
        Boolean isIn = false;
        for (Locality locality : localities) {
            if(locality.getCountry().getName().equals(country.toUpperCase()) && 
                locality.getName().equals(name.toUpperCase()) && 
                locality.getPostalCode().equals(postalCode.toUpperCase())){
                    isIn = true;
            }
        }
        if(!isIn){
            createLocality(country, postalCode, name);
        }
    }
}