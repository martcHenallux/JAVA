package Model;
import java.sql.Date;
import java.util.ArrayList;

import Controllers.EntryFromTable;

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

    public ArrayList<BusinessEntity> getPeopleOfContact() {
        return peopleOfContact;
    }

    public EntryFromTable toTableEntry() {
        return new EntryFromTable(
            Integer.toString(serialNumber),
            firstName,
            lastName,
            Boolean.toString( isCustomer),
            Boolean.toString(isSupplier),
            (status == null ? "null" : status.getCode()),
            (address == null ? "null" : Integer.toString(address.getId()))
        );
    }
}
