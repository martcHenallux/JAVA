package Model;
public class Locality {

    private Integer id;
    private Country country;
    private  String postalCode;
    private String name;

    public Locality(Integer id, Country country, String postalCode, String name){
        this.id = id;
        this.country = country;
        this.postalCode = postalCode;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public Country getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return getName() + " " + getPostalCode() + " " + getCountry().getName();
    }
}
