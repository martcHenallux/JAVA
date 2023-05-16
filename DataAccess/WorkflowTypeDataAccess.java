package DataAccess;
import java.util.ArrayList;

import Model.WorkflowType;

public interface WorkflowTypeDataAccess {
    void createWorkflowType(String code, String description);
    WorkflowType readOneWorkflowType(String code);
    ArrayList<WorkflowType> readAllWorkflowTypes();
}
