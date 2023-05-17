package Model;

public class Workflow {
    
    private int id;
    private Boolean isOver;
    private BusinessEntity entity;
    private WorkflowType type;

    public Workflow(int id, Boolean isOver, BusinessEntity entity, WorkflowType type){
        this.id = id;
        this.isOver = isOver;
        this.entity = entity;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public Boolean getIsOver() {
        return isOver;
    }

    public BusinessEntity getEntity() {
        return entity;
    }

    public WorkflowType getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder information = new StringBuilder("Workflow number " + id + ":\n");
        information.append("    " + entity.getLastName());
        if (entity.getFirstName() != null){
            information.append(" " + entity.getFirstName());
        }
        information.append("\n    " + type.getCode());
        information.append("\n    " + isOver);
        return information.toString();
    }
}
