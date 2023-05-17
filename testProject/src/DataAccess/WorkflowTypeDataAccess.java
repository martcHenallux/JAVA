package DataAccess;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.WorkflowType;

public interface WorkflowTypeDataAccess {
    void createWorkflowType(String code, String description) throws SQLException;
    WorkflowType readOneWorkflowType(String code) throws SQLException;
    ArrayList<WorkflowType> readAllWorkflowTypes() throws SQLException;
}
