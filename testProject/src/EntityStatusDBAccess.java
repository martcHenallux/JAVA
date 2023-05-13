import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EntityStatusDBAccess implements EntityStatusDataAccess{
    
    public EntityStatusDBAccess(){}

    public ArrayList<EntityStatus> readEntityStatus(){
        String sqlInstruction = "select * from entity_status";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<EntityStatus> allEntityStatus = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            EntityStatus entityStatus;
            while(data.next()){
                entityStatus = new EntityStatus(data.getString("code"), data.getString("description"));
                allEntityStatus.add(entityStatus);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return allEntityStatus;
    }

    public EntityStatus readOneEntityStatus(String code){
        String sqlInstruction = "select * from entity_status where code = ?";
        Connection connection = SingletonConnexion.getInstance();
        EntityStatus status = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, code);
            ResultSet data = preparedStatement.executeQuery();
            if(data.next()){
                status = new EntityStatus(data.getString("code"), data.getString("description"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return status;
    }
}
