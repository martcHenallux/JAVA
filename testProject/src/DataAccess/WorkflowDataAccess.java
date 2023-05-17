package DataAccess;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Workflow;

public interface WorkflowDataAccess {
    void createWorkflow(int serialNumberEntity, String workflowType) throws SQLException;
    ArrayList<Workflow> readAllWorkflows() throws SQLException;
    Workflow readOneWorkflow(int id) throws SQLException;
    ArrayList<Workflow> readAllWorkflowsOfOneEntity(int serialNumber) throws SQLException;
    ArrayList<Workflow> readAllWorkflowsOfAType(String code) throws SQLException;
    void deleteAllWorkflowsOfPerson(int serialNumberEntity) throws SQLException;
}
