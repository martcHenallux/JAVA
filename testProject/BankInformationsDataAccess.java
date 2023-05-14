import java.util.ArrayList;

public interface BankInformationsDataAccess {
    void createBankInformations(BusinessEntity person, String iban, String bicCode);
    void deleteBankInformations(int personId, String IBAN);
    ArrayList<BankInformations> readBankInformations(int personId);
}
