package DataAccess;
import java.util.ArrayList;

import Model.BankInformations;

public interface BankInformationsDataAccess {
    void createBankInformations(int personId, String iban, String bicCode);
    void deleteBankInformations(int personId, String IBAN);
    void deleteAllBankInformations(int personId);
    ArrayList<BankInformations> readBankInformations(int personId);
}
