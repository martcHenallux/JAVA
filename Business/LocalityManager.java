package Business;

import java.util.ArrayList;

import DBAccess.LocalityDBAccess;
import Model.Locality;

public class LocalityManager {
    
    LocalityDBAccess dao;

    public LocalityManager(){
        dao = new LocalityDBAccess();
    }

    private void createLocality(String country, String postalCode, String name){
        dao.createLocality(country, postalCode, name);
    }

    public ArrayList<Locality> readLocalities(){
        return dao.readLocalities();
    }

    public Locality readOneLocality(int id){
        return dao.readOneLocality(id);
    }

    public void localityIsIn(String country, String postalCode, String name){
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
