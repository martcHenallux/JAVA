import java.util.ArrayList;

public interface EntityStatusDataAccess {
    ArrayList<EntityStatus> readEntityStatus();
    EntityStatus readOneEntityStatus(String code);
}
