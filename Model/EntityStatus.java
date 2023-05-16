package Model;
public class EntityStatus {
    private String code;
    private String description;
    
    public EntityStatus(String code, String description){
        this.code = code;
        this.description = description;
    }    

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getCode() + ": " + getDescription();
    }
}
