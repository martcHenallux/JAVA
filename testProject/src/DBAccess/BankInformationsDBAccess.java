package DBAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DataAccess.BankInformationsDataAccess;
import Model.BankInformations;
import DataAccess.BusinessEntityDataAccess;

public class BankInformationsDBAccess implements BankInformationsDataAccess{
    
    public BankInformationsDBAccess(){}

    public void createBankInformations(int personId, String iban, String bicCode) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("insert into bank_informations (accountUser, IBAN, BICCode)");
        sqlInstruction.append(" values (?, ?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setInt(1, personId);
            preparedStatement.setString(2, iban.toUpperCase());
            preparedStatement.setString(3, bicCode);
            preparedStatement.executeUpdate();
        }
    }

    public void deleteBankInformations(int personId, String iban) throws SQLException{
        String sqlInstruction = "delete from bank_informations where accountUser = ? and IBAN = ?";
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setInt(1, personId);
            preparedStatement.setString(2, iban);
            preparedStatement.executeUpdate();
        }
    }

    public void deleteAllBankInformations(int personId) throws SQLException{
        String sqlInstruction = "delete from bank_informations where accountUser = ?";
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setInt(1, personId);
            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<BankInformations> readBankInformations(int personId) throws SQLException{
        String sqlInstruction = "select * from bank_informations where accountUser = ?";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<BankInformations> allInformations = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setInt(1, personId);
            try (ResultSet data = preparedStatement.executeQuery()){
                BankInformations information;
                BusinessEntityDataAccess businessEntityDataAccess = new BusinessEntityDBAccess();
                while(data.next()){
                    information = new BankInformations(businessEntityDataAccess.readBusinessEntity(data.getInt("accountUser")), data.getString("IBAN"), data.getString("BICCode"));
                    allInformations.add(information);
                }   
            }
        }
        return allInformations;
    }
}
