package DBAccess;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    Boolean isSupplier, Integer addressId, String hashPassword, String salt) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder();
        sqlInstruction.append("insert into business_entity ");
        sqlInstruction.append("(lastName, firstName, birthDate, isCustomer, isSupplier, codeStatus, idAddress,");
        sqlInstruction.append(" hashPassword, salt)");
        sqlInstruction.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setString(1, lastName);
            if(firstName == null){
                preparedStatement.setNull(2, java.sql.Types.VARCHAR);
            }
            else{
                preparedStatement.setString(2, firstName);
            }
            if(birthDate == null){
                preparedStatement.setNull(3, java.sql.Types.DATE);
            }
            else{
                preparedStatement.setDate(3, birthDate);
            }
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
        }
    }

    public void createPersonOfContact(int idCompany, int idPerson) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("insert into person_of_contact");
        sqlInstruction.append(" (serialNumberCompany, serialNumberPerson)");
        sqlInstruction.append(" values (?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setInt(1, idCompany);
            preparedStatement.setInt(2, idPerson);
            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<BusinessEntity> readBusinessEntities() throws SQLException{
        String sqlInstruction = "select * from business_entity";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<BusinessEntity> allBusinessEntities = new ArrayList<>();
        AddressDataAccess addressDataAccess = new AddressDBAccess();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            try (ResultSet data = preparedStatement.executeQuery()){
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
            }
        }
        return allBusinessEntities;
    }

    public BusinessEntity readBusinessEntity(int serialNumber) throws SQLException{
        String sqlInstruction = "select * from business_entity where serialNumber = ?";
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntity entity = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setInt(1, serialNumber);
            try (ResultSet data = preparedStatement.executeQuery()){
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
            }          
        }
        return entity;
    }

    public void deleteBusinessEntity(int serialNumber) throws SQLException{
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
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
                preparedStatement.setInt(1, serialNumber);
                preparedStatement.executeUpdate();
            }
        }
    }

    public void deletePersonOfContact(int serialNumberEntity) throws SQLException{
        String sqlInstruction = "delete from person_of_contact where serialNumberPerson = ?";
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setInt(1, serialNumberEntity);
            preparedStatement.executeUpdate();
        }
    }

    public int numberDocumentPerEntity(int serialNumber) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("select count(document.documentNumber) as number from business_entity");
        sqlInstruction.append(" inner join workflow on business_entity.serialNumber = workflow.serialNumberEntity");
        sqlInstruction.append(" inner join document on workflow.id = document.workflowId");
        sqlInstruction.append(" where serialNumber = ?");
        Connection connection = SingletonConnexion.getInstance();
        Integer count = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setInt(1, serialNumber);
            try (ResultSet data = preparedStatement.executeQuery()){
                if(data.next()){
                    count = data.getInt("number");
                }  
            }
        }
        return count;
    }

    public ArrayList<BusinessEntity> readPeopleOfContact(int idCompany) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("select serialNumberPerson");
        sqlInstruction.append(" from person_of_contact");
        sqlInstruction.append(" where serialNumberCompany = ?");
        ArrayList<BusinessEntity> peopleOfContact = new ArrayList<>();
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setInt(1, idCompany);
            try (ResultSet data = preparedStatement.executeQuery()){
                BusinessEntity person;
                while(data.next()){
                    person = readBusinessEntity(data.getInt("serialNumberPerson"));
                    peopleOfContact.add(person);
                }   
            }
        }
        return peopleOfContact;
    }

    public void updateBusinessEntity(int serialNumber, Boolean isCustomer, Boolean isSupplier, String codeStatus, 
            Integer addressId) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("update business_entity");
        sqlInstruction.append(" set isCustomer = ?, isSupplier = ?, codeStatus = ?,idAddress = ?");
        sqlInstruction.append(" where serialNumber = ?");
        Connection connection = SingletonConnexion.getInstance();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setBoolean(1, isCustomer);
            preparedStatement.setBoolean(2, isSupplier);
            if(isCustomer == false){
                preparedStatement.setNull(3, java.sql.Types.VARCHAR);
            }
            else{
                preparedStatement.setString(3, codeStatus);
            }
            if (addressId == null){
                preparedStatement.setNull(4, java.sql.Types.INTEGER);
            }
            else{
                preparedStatement.setInt(4, addressId);
            }
            System.out.println("test fin");
            preparedStatement.setInt(5, serialNumber);
            preparedStatement.executeUpdate();
        }
    }

    public float amountToPay(int serialNumber) throws SQLException{
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setInt(1, serialNumber);
            try (ResultSet data = preparedStatement.executeQuery()){
                if(data.next()){
                    amount = data.getFloat("amount");
                }   
            }
        }
        return amount;
    }
}
