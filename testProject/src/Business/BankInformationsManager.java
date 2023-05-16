package Business;

import java.util.ArrayList;

import DBAccess.BankInformationsDBAccess;
import Model.BankInformations;
import Model.BusinessEntity;

public class BankInformationsManager {
    
    BankInformationsDBAccess dao;

    public BankInformationsManager(){
        dao = new BankInformationsDBAccess();
    }

    private void createBankInformations(BusinessEntity person, String iban, String bicCode){
        dao.createBankInformations(person.getSerialNumber(), iban, bicCode);
    }

    public void deleteBankInformations(BusinessEntity person, String iban){
        dao.deleteBankInformations(person.getSerialNumber(), iban);
    }

    public ArrayList<BankInformations> readBankInformations(BusinessEntity person){
        return dao.readBankInformations(person.getSerialNumber());
    }

    public void bank_informationsIsIn(BusinessEntity person, String iban, String bicCode){
        ArrayList<BankInformations> accounts = new ArrayList<>();
        accounts = readBankInformations(person);
        boolean isIn = false;
        for (BankInformations account : accounts) {
            if (account.getPerson().getSerialNumber() == person.getSerialNumber() &&
                account.getIban().equals(iban.toUpperCase())){
                    isIn = true;
            }
        }
        if(!isIn){
            createBankInformations(person, iban, bicCode);
        }
    }
}
