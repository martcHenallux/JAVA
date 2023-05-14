import java.sql.Date;
import java.util.ArrayList;

public interface BusinessEntityDataAccess {
    void createBusinessEntity(String lastName, String firstName, Date birthDate, Boolean isCustomer, 
        Boolean isSupplier, Address address, String hashPassword, String salt);
    void createPersonOfContact(int idCompany, int idPerson);
    ArrayList<BusinessEntity> readBusinessEntities();
    BusinessEntity readBusinessEntity(int serialNumber);
    void deleteBusinessEntity(int serialNumber);
    ArrayList<BusinessEntity> readPeopleOfContact(int idCompany);
    // nouveau
    void updateBusinessEntity(int serialNumber, Boolean isCustomer, Boolean isSupplier, Address address, 
        String hashPassword);
    
}
