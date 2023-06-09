import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AddressDBAccess implements AddressDataAccess{
    
    private LocalityDataAccess localityDataAccess = new LocalityDBAccess();

    public AddressDBAccess(){}

    public void createAddress(int idLocality, String street, int number){
        String sqlInstruction = "Insert into address (idLocality, street, number) values (?, ?, ?)";
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, idLocality);
            preparedStatement.setString(2, street.toLowerCase());
            preparedStatement.setInt(3, number);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public ArrayList<Address> readAddresses(){
        String sqlInstruction = "select * from address";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<Address> allAddresses = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            Address address;
            Locality locality;
            while(data.next()){
                locality = localityDataAccess.readOneLocality(data.getInt("idLocality"));
                address = new Address(null, locality, data.getString("street"), data.getInt("number"));
                allAddresses.add(address);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return allAddresses;
    }

    public Address readOneAddress(int id){
        String sqlInstruction = "select * from address where id = ?";
        Connection connection = SingletonConnexion.getInstance();
        Address address = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, id);
            ResultSet data = preparedStatement.executeQuery();
            data.next();
            address = new Address(null, localityDataAccess.readOneLocality(data.getInt("idLocality")), data.getString("street"), data.getInt("number"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return address;
    }

    public void addressIn(Locality locality, String street, int number){
        ArrayList<Address> addresses = new ArrayList<>();
        addresses = readAddresses();
        boolean isIn = false;
        for (Address address : addresses) {
            if(address.getLocality().getId() == locality.getId() && address.getStreet().equals(street.toLowerCase()) && address.getNumber() == number){
                isIn = true;
            }
        }
        if(!isIn){
            createAddress(locality.getId(), street, number);;
            System.out.println("Adresse ajouté");
        }
    }

}
