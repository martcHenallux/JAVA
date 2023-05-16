package DataAccess;
import java.sql.Date;
import java.util.ArrayList;

import Model.BusinessEntity;

public interface BusinessEntityDataAccess {
    void createBusinessEntity(String lastName, String firstName, Date birthDate, Boolean isCustomer, 
        Boolean isSupplier, Integer addressId, String hashPassword, String salt);
    void createPersonOfContact(int idCompany, int idPerson);
    ArrayList<BusinessEntity> readBusinessEntities();
    BusinessEntity readBusinessEntity(int serialNumber);
    void deleteBusinessEntity(int serialNumber);
    int numberDocumentPerEntity(int serialNumber);
    ArrayList<BusinessEntity> readPeopleOfContact(int idCompany);
    // nouveau
    void updateBusinessEntity(int serialNumber, Boolean isCustomer, Boolean isSupplier, String codeStatus, Integer addressId, 
        String hashPassword);
    float amountToPay(int serialNumber);
}
