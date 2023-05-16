package DataAccess;
import java.util.ArrayList;

import Model.EntityStatus;

public interface EntityStatusDataAccess {
    ArrayList<EntityStatus> readEntityStatus();
    EntityStatus readOneEntityStatus(String code);
}
