package DataAccess;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.BusinessEntity;

public interface BusinessEntityDataAccess {
    void createBusinessEntity(String lastName, String firstName, Date birthDate, Boolean isCustomer, 
        Boolean isSupplier, Integer addressId, String hashPassword, String salt) throws SQLException;
    void createPersonOfContact(int idCompany, int idPerson) throws SQLException;
    ArrayList<BusinessEntity> readBusinessEntities() throws SQLException;
    BusinessEntity readBusinessEntity(int serialNumber) throws SQLException;
    void deleteBusinessEntity(int serialNumber) throws SQLException;
    int numberDocumentPerEntity(int serialNumber) throws SQLException;
    ArrayList<BusinessEntity> readPeopleOfContact(int idCompany) throws SQLException;
    void updateBusinessEntity(int serialNumber, Boolean isCustomer, Boolean isSupplier, String codeStatus, 
        Integer addressId) throws SQLException;
    float amountToPay(int serialNumber) throws SQLException;
}
