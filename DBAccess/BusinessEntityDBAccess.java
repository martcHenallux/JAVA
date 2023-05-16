package DBAccess;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DataAccess.AddressDataAccess;
import DataAccess.BankInformationsDataAccess;
import DataAccess.BusinessEntityDataAccess;
import DataAccess.ContactDataAccess;
import Model.BusinessEntity;
import DataAccess.EntityStatusDataAccess;
import DataAccess.WorkflowDataAccess;

public class BusinessEntityDBAccess implements BusinessEntityDataAccess{
    
    private EntityStatusDataAccess entityStatusDataAccess = new EntityStatusDBAccess();

    public BusinessEntityDBAccess(){}

    public void createBusinessEntity(String lastName, String firstName, Date birthDate, Boolean isCustomer, 
    Boolean isSupplier, Integer addressId, String hashPassword, String salt){
        StringBuilder sqlInstruction = new StringBuilder();
        sqlInstruction.append("insert into business_entity ");
        sqlInstruction.append("(lastName, firstName, birthDate, isCustomer, isSupplier, codeStatus, idAddress,");
        sqlInstruction.append(" hashPassword, salt)");
        sqlInstruction.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.setDate(3, birthDate);
            preparedStatement.setBoolean(4, isCustomer);
            preparedStatement.setBoolean(5, isSupplier);
            if(isCustomer == false){
                preparedStatement.setNull(6, java.sql.Types.VARCHAR);
            }
            else{
                preparedStatement.setString(6, entityStatusDataAccess.readOneEntityStatus("occasionnel").getCode());
            }
            if (addressId == null){
                preparedStatement.setNull(7, java.sql.Types.INTEGER);
            }
            else{
                preparedStatement.setInt(7, addressId);
            }
            preparedStatement.setString(8, hashPassword);
            preparedStatement.setString(9, salt);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error creation: " + e.getMessage());
        }
    }

    public void createPersonOfContact(int idCompany, int idPerson){
        StringBuilder sqlInstruction = new StringBuilder("insert into person_of_contact");
        sqlInstruction.append(" (serialNumberCompany, serialNumberPerson)");
        sqlInstruction.append(" values (?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setInt(1, idCompany);
            preparedStatement.setInt(2, idPerson);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error link person company: " + e.getMessage());
        }
    }

    public ArrayList<BusinessEntity> readBusinessEntities(){
        String sqlInstruction = "select * from business_entity";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<BusinessEntity> allBusinessEntities = new ArrayList<>();
        AddressDataAccess addressDataAccess = new AddressDBAccess();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            BusinessEntity businessEntity;
            while(data.next()){
                businessEntity = new BusinessEntity(data.getInt("serialNumber"),
                    data.getString("lastName"),
                    data.getString("firstName"),
                    data.getDate("birthDate"),
                    data.getBoolean("isCustomer"),
                    data.getBoolean("isSupplier"),
                    data.getFloat("creditLimit"),
                    entityStatusDataAccess.readOneEntityStatus(data.getString("codeStatus")),
                    addressDataAccess.readOneAddress(data.getInt("idAddress")));
                allBusinessEntities.add(businessEntity);
                if (data.getBoolean("isSupplier")){
                    ArrayList<BusinessEntity> entities = new ArrayList<>();
                    entities = readPeopleOfContact(data.getInt("serialNumber"));
                    for (BusinessEntity person : entities) {
                        businessEntity.addPersonOfContact(person);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading entities: " + e.getMessage());
        }
        return allBusinessEntities;
    }

    public BusinessEntity readBusinessEntity(int serialNumber){
        String sqlInstruction = "select * from business_entity where serialNumber = ?";
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntity entity = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, serialNumber);
            ResultSet data = preparedStatement.executeQuery();
            if (data.next()){
                AddressDataAccess addressDataAccess = new AddressDBAccess();
                entity = new BusinessEntity(data.getInt("serialNumber"),
                    data.getString("lastName"), 
                    data.getString("firstName"),
                    data.getDate("birthDate"),
                    data.getBoolean("isCustomer"),
                    data.getBoolean("isSupplier"),
                    data.getFloat("creditLimit"),
                    entityStatusDataAccess.readOneEntityStatus(data.getString("codeStatus")),
                    addressDataAccess.readOneAddress(data.getInt("idAddress")));
                if (data.getBoolean("isSupplier")){
                    ArrayList<BusinessEntity> entities = new ArrayList<>();
                    entities = readPeopleOfContact(serialNumber);
                    for (BusinessEntity person : entities) {
                        entity.addPersonOfContact(person);
                    }
                }
            }            
        } catch (Exception e) {
            System.out.println("Error reading 1 entity: " + e.getMessage());
        }
        return entity;
    }

    public void deleteBusinessEntity(int serialNumber){
        if(!(numberDocumentPerEntity(serialNumber) > 0)){
            ContactDataAccess contactDataAccess = new ContactDBAccess();
            contactDataAccess.deleteAllContacts(serialNumber);
            BankInformationsDataAccess bankInformationsDataAccess = new BankInformationsDBAccess();
            bankInformationsDataAccess.deleteAllBankInformations(serialNumber);
            WorkflowDataAccess workflowDataAccess = new WorkflowDBAccess();
            workflowDataAccess.deleteAllWorkflowsOfPerson(serialNumber);
            deletePersonOfContact(serialNumber);
            String sqlInstruction = "delete from business_entity where serialNumber = ?";
            Connection connection = SingletonConnexion.getInstance();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
                preparedStatement.setInt(1, serialNumber);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                System.out.println("Error deleting entity: " + e.getMessage());
            }
        }
        else{
            System.out.println("Error has documents");
        }
    }

    public void deletePersonOfContact(int serialNumberEntity){
        String sqlInstruction = "delete from person_of_contact where serialNumberPerson = ?";
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, serialNumberEntity);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error deleting person of contact: " + e.getMessage());
        }
    }

    public int numberDocumentPerEntity(int serialNumber){
        StringBuilder sqlInstruction = new StringBuilder("select count(document.documentNumber) as number from business_entity");
        sqlInstruction.append(" inner join workflow on business_entity.serialNumber = workflow.serialNumberEntity");
        sqlInstruction.append(" inner join document on workflow.id = document.workflowId");
        sqlInstruction.append(" where serialNumber = ?");
        Connection connection = SingletonConnexion.getInstance();
        Integer count = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setInt(1, serialNumber);
            ResultSet data = preparedStatement.executeQuery();
            if(data.next()){
                count = data.getInt("number");
            }
        } catch (Exception e) {
            System.out.println("Error number document: " + e.getMessage());
        }
        return count;
    }

    public ArrayList<BusinessEntity> readPeopleOfContact(int idCompany){
        StringBuilder sqlInstruction = new StringBuilder("select serialNumberPerson");
        sqlInstruction.append(" from person_of_contact");
        sqlInstruction.append(" where serialNumberCompany = ?");
        ArrayList<BusinessEntity> peopleOfContact = new ArrayList<>();
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setInt(1, idCompany);
            ResultSet data = preparedStatement.executeQuery();
            BusinessEntity person;
            while(data.next()){
                person = readBusinessEntity(data.getInt("serialNumberPerson"));
                peopleOfContact.add(person);
            }
        } catch (Exception e) {
            System.out.println("Error read people of contact: " + e.getMessage());
        }
        return peopleOfContact;
    }

    public void updateBusinessEntity(int serialNumber, Boolean isCustomer, Boolean isSupplier, String codeStatus, 
            Integer addressId, String hashPassword){
        StringBuilder sqlInstruction = new StringBuilder("update businessEntity");
        sqlInstruction.append(" set isCustomer = ?, isSupplier = ?, codeStatus = ?,idAddress = ?, hashPassword = ?");
        sqlInstruction.append(" where serialNumber = ?");
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setBoolean(1, isCustomer);
            preparedStatement.setBoolean(2, isSupplier);
            if(isCustomer == false){
                preparedStatement.setNull(3, java.sql.Types.VARCHAR);
            }
            else{
                preparedStatement.setString(3, hashPassword);
            }
            preparedStatement.setInt(4, addressId);
            preparedStatement.setString(5, hashPassword);
            preparedStatement.setInt(6, serialNumber);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error updating entity: " + e.getMessage());
        }
    }

    public float amountToPay(int serialNumber){
        StringBuilder sqlInstruction = new StringBuilder("select sum(document.priceVATIncluded)");
        sqlInstruction.append(" + ifnull(business_entity.creditLimit, 0) as amount");
        sqlInstruction.append(" from document");
        sqlInstruction.append(" inner join workflow on document.workflowId = workflow.id");
        sqlInstruction.append(" inner join business_entity on");
        sqlInstruction.append(" workflow.serialNumberEntity = business_entity.serialNumber");
        sqlInstruction.append(" where business_entity.serialNumber = ? and workflow.type = 'Client'");
        sqlInstruction.append(" and document.status <> 'Fini'");
        Connection connection = SingletonConnexion.getInstance();
        Float amount = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setInt(1, serialNumber);
            ResultSet data = preparedStatement.executeQuery();
            if(data.next()){
                amount = data.getFloat("amount");
            }
        } catch (Exception e) {
            System.out.println("Error calculling amount to pay: " + e.getMessage());
        }
        return amount;
    }
}
