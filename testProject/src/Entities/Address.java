package Entities;
public class Address {

    private Integer id;
    private Locality locality;
    private String street;
    private int number;

    public Address(Integer id, Locality locality, String street, int number){
        this.id = id;
        this.locality = locality;
        this.street = street;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public Locality getLocality() {
        return locality;
    }

    public String getStreet() {
        return street;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return getNumber() + " " + getStreet() + " " + getLocality(); 
    }
}
