package Business;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import DBAccess.BusinessEntityDBAccess;
import Model.Address;
import Model.BusinessEntity;

public class BusinessEntityManager {
    
    BusinessEntityDBAccess dao;

    public BusinessEntityManager(){
        dao = new BusinessEntityDBAccess();
    }
    
    private void createBusinessEntity(String lastName, String firstName, Date birthDate, Boolean isCustomer, 
    Boolean isSupplier, Address address, String hashPassword, String salt) throws SQLException{
        Integer addressId;
        if(address == null){
            addressId = null;
        }
        else{
            addressId = address.getId();
        }
        dao.createBusinessEntity(lastName, firstName, birthDate, isCustomer, isSupplier, addressId,
            hashPassword, salt);
    }

    public void createPersonOfContact(int idCompany, int idPerson) throws SQLException{
        dao.createPersonOfContact(idCompany, idPerson);
    }

    public ArrayList<BusinessEntity> readBusinessEntities() throws SQLException{
        return dao.readBusinessEntities();
    }

    public BusinessEntity readBusinessEntity(int serialNumber) throws SQLException{
        return dao.readBusinessEntity(serialNumber);
    }

    public void deleteBusinessEntity(int serialNumber) throws SQLException{
        dao.deleteBusinessEntity(serialNumber);
    }

    public ArrayList<BusinessEntity> readPeopleOfContact(int idCompany) throws SQLException{
        return dao.readPeopleOfContact(idCompany);
    }

    public void updateBusinessEntity(int serialNumber, Boolean isCustomer, Boolean isSupplier, String status, 
    Address address, String hashPassword) throws SQLException{
        dao.updateBusinessEntity(serialNumber, isCustomer, isSupplier, status, address.getId());
    }

    public float amountToPay(BusinessEntity entity) throws SQLException{
        return dao.amountToPay(entity.getSerialNumber());
    }

    public void tryCreateBusinessEntity(String lastName, String firstName, Date birthDate, Boolean isCustomer, 
        Boolean isSupplier, Address address, String hashPassword, String salt) throws SQLException{
            ArrayList<BusinessEntity> entities = new ArrayList<>();
            entities = readBusinessEntities();
            boolean isIn = false;
            Boolean sameFirstName = false;
            for (BusinessEntity entity : entities) {
                if((entity.getFirstName() == firstName)){
                    sameFirstName = true;
                }
                else if(entity.getFirstName() == null || firstName == null){
                    sameFirstName = false;
                }
                else{
                    if(entity.getFirstName().toUpperCase().equals(firstName.toUpperCase())){
                        sameFirstName = true;
                    }
                    else{
                        sameFirstName = false;
                    }
                }
                if (entity.getLastName().toUpperCase().equals(lastName.toUpperCase()) && sameFirstName){
                        isIn = true;
                }
            }
            if(!isIn){
                createBusinessEntity(lastName, firstName, birthDate, isCustomer, isSupplier, address, 
                    hashPassword, salt);
            }
            else{
                //throw exception
            }
    }
}
