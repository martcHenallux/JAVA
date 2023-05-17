package Business;

import java.sql.SQLException;
import java.util.ArrayList;

import DBAccess.EntityStatusDBAccess;
import Model.EntityStatus;

public class EntityStatusManager {
    
    EntityStatusDBAccess dao;

    public EntityStatusManager(){
        dao = new EntityStatusDBAccess();
    }

    public ArrayList<EntityStatus> readEntityStatus() throws SQLException{
        return dao.readEntityStatus();
    }

    public EntityStatus readOneEntityStatus(String code) throws SQLException{
        return dao.readOneEntityStatus(code);
    }
}
