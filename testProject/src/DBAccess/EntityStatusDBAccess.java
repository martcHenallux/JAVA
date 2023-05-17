package DBAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DataAccess.EntityStatusDataAccess;
import Model.EntityStatus;

public class EntityStatusDBAccess implements EntityStatusDataAccess{
    
    public EntityStatusDBAccess(){}

    public ArrayList<EntityStatus> readEntityStatus() throws SQLException{
        String sqlInstruction = "select * from entity_status";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<EntityStatus> allEntityStatus = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            try (ResultSet data = preparedStatement.executeQuery()){
                EntityStatus entityStatus;
                while(data.next()){
                    entityStatus = new EntityStatus(data.getString("code"), 
                        data.getString("description"));
                    allEntityStatus.add(entityStatus);
                }
            }
        }
        return allEntityStatus;
    }

    public EntityStatus readOneEntityStatus(String code) throws SQLException{
        String sqlInstruction = "select * from entity_status where code = ?";
        Connection connection = SingletonConnexion.getInstance();
        EntityStatus status = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setString(1, code);
            try (ResultSet data = preparedStatement.executeQuery()){
                if(data.next()){
                    status = new EntityStatus(data.getString("code"), 
                        data.getString("description"));
                }
            }
        }
        return status;
    }
}
