import java.util.ArrayList;

public interface WorkflowTypeDataAccess {
    void createWorkflowType(String code, String description);
    WorkflowType readOneWorkflowType(String code);
    ArrayList<WorkflowType> readAllWorkflowTypes();
}
