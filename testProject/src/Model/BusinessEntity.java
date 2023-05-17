package Model;
import java.sql.Date;
import java.util.ArrayList;

public class BusinessEntity {
    private int serialNumber;
    private String lastName;
    private String firstName;
    private Date birthDate;
    private Boolean isCustomer;
    private Boolean isSupplier;
    private Float creditLimit;
    private EntityStatus status;
    private Address address;
    private ArrayList<BusinessEntity> peopleOfContact;

    public BusinessEntity(int serialNumber, String lastName, String firstName, Date birthDate, Boolean isCustomer, Boolean isSupplier, Float creditLimit,
        EntityStatus status, Address address){
            this.serialNumber = serialNumber;
            this.lastName = lastName;
            this.firstName = firstName;
            this.birthDate = birthDate;
            this.isCustomer = isCustomer;
            this.isSupplier = isSupplier;
            this.creditLimit = creditLimit;
            this.status = status;
            this.address = address;
            setPeopleOfContact();
    }

    private void setPeopleOfContact() {
        if (isSupplier){
            peopleOfContact = new ArrayList<>();
        }
    }

    public void addPersonOfContact(BusinessEntity person){
        peopleOfContact.add(person);
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Boolean getIsCustomer() {
        return isCustomer;
    }

    public Boolean getIsSupplier() {
        return isSupplier;
    }

    public Float getCreditLimit() {
        return creditLimit;
    }

    public String getStatus() {
        return status.getCode();
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        StringBuilder entity = new StringBuilder("Serial number: " + serialNumber + "\n");
        entity.append("Last name: " + lastName + "\n");
        if (isCustomer){
            entity.append("First name: " + firstName + "\n");
            entity.append("Birth date: " + birthDate.toString() + "\n");
            entity.append("Status: " + status.getCode() + "\n");
            if(!status.getCode().equals("occasionnel")){
                entity.append("Credit limit: " + creditLimit + "\n");
            }
            entity.append("Is a client.\n");
        }
        if (isSupplier){
            entity.append("Is a supplier.\n");
        }
        if(address!= null){
            entity.append("Address: " + address + "\n");
        }
        if (isSupplier){
            entity.append(displayPeopleOfContact());
        }
        return entity.toString();
    }

    public String displayPeopleOfContact(){
        StringBuilder people = new StringBuilder("People of contact: {");
        for (BusinessEntity person : peopleOfContact) {
            people.append("" + person);
        }
        people.append("}");
        return people.toString();
    }
}
