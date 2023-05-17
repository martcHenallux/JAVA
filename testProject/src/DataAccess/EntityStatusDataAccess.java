package DataAccess;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.EntityStatus;

public interface EntityStatusDataAccess {
    ArrayList<EntityStatus> readEntityStatus() throws SQLException;
    EntityStatus readOneEntityStatus(String code) throws SQLException;
}
