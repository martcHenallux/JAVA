public class BankInformations {
    
    private BusinessEntity person;
    private String iban;
    private String bicCode;

    public BankInformations(BusinessEntity person, String iban, String bicCode){
        this.person = person;
        this.iban = iban;
        this.bicCode = bicCode;
    }

    public BusinessEntity getPerson() {
        return person;
    }

    public String getIban() {
        return iban;
    }

    public String getBicCode() {
        return bicCode;
    }

    @Override
    public String toString() {
        StringBuilder informations = new StringBuilder("Bank informations of " + person.getLastName() + (person.getFirstName().isEmpty()? person.getFirstName() : "") + ":\n");
        informations.append("Iban: " + iban + "\n");
        informations.append("BIC Code: " + bicCode + "\n");
        return informations.toString();
    }
}
