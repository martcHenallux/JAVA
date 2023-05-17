package DataAccess;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.BankInformations;

public interface BankInformationsDataAccess {
    void createBankInformations(int personId, String iban, String bicCode) throws SQLException;
    void deleteBankInformations(int personId, String IBAN) throws SQLException;
    void deleteAllBankInformations(int personId) throws SQLException;
    ArrayList<BankInformations> readBankInformations(int personId) throws SQLException;
}
