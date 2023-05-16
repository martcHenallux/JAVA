package DBAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DataAccess.BankInformationsDataAccess;
import Model.BankInformations;
import DataAccess.BusinessEntityDataAccess;

public class BankInformationsDBAccess implements BankInformationsDataAccess{
    
    public BankInformationsDBAccess(){}

    public void createBankInformations(int personId, String iban, String bicCode){
        StringBuilder sqlInstruction = new StringBuilder("insert into bank_informations (accountUser, IBAN, BICCode)");
        sqlInstruction.append(" values (?, ?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setInt(1, personId);
            preparedStatement.setString(2, iban.toUpperCase());
            preparedStatement.setString(3, bicCode);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error creating bank informations: " + e.getMessage());
        }
    }

    public void deleteBankInformations(int personId, String iban){
        String sqlInstruction = "delete from bank_informations where accountUser = ? and IBAN = ?";
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, personId);
            preparedStatement.setString(2, iban);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error delete bank information: " + e.getMessage());
        }
    }

    public void deleteAllBankInformations(int personId){
        String sqlInstruction = "delete from bank_informations where accountUser = ?";
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, personId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error deleting all bank informations");
        }
    }

    public ArrayList<BankInformations> readBankInformations(int personId){
        String sqlInstruction = "select * from bank_informations where accountUser = ?";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<BankInformations> allInformations = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, personId);
            ResultSet data = preparedStatement.executeQuery();
            BankInformations information;
            BusinessEntityDataAccess businessEntityDataAccess = new BusinessEntityDBAccess();
            while(data.next()){
                information = new BankInformations(businessEntityDataAccess.readBusinessEntity(data.getInt("accountUser")), data.getString("IBAN"), data.getString("BICCode"));
                allInformations.add(information);
            }
        } catch (Exception e) {
            System.out.println("Error reading bank informations: " + e.getMessage());
        }
        return allInformations;
    }
}
