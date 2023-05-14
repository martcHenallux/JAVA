import java.sql.Connection;
import java.sql.DriverManager;

public class SingletonConnexion {

    public static Connection uniqueConnection;

    public static Connection getInstance(){
        if (uniqueConnection == null){
            try {
                uniqueConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/drakkarys", "root", "Ravenclaw178th");
                System.out.println("I'm connected");
            } catch (Exception e) {
                System.out.println("erreur: " + e.getMessage());
            }
        }
        return uniqueConnection;
    }
    
}
