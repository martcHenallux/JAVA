package Business;

import java.util.ArrayList;

import DBAccess.EntityStatusDBAccess;
import Model.EntityStatus;

public class EntityStatusManager {
    
    EntityStatusDBAccess dao;

    public EntityStatusManager(){
        dao = new EntityStatusDBAccess();
    }

    public ArrayList<EntityStatus> readEntityStatus(){
        return dao.readEntityStatus();
    }

    public EntityStatus readOneEntityStatus(String code){
        return dao.readOneEntityStatus(code);
    }
}
