import java.util.ArrayList;

public interface WorkflowDataAccess {
    void createWorkflow(int serialNumberEntity, String workflowType);
    ArrayList<Workflow> readAllWorkflows();
    Workflow readOneWorkflow(int id);
    ArrayList<Workflow> readAllWorkflowsOfOneEntity(int serialNumber);
    ArrayList<Workflow> readAllWorkflowsOfAType(String code);
}
