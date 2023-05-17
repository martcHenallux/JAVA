package Business;

import java.sql.SQLException;
import java.util.ArrayList;

import DBAccess.WorkflowDBAccess;
import Model.Workflow;

public class WorkflowManager {
    
    WorkflowDBAccess dao;

    public WorkflowManager(){
        dao = new WorkflowDBAccess();
    }

    public void createWorkflow(int serialNumberEntity, String workflowType) throws SQLException{
        dao.createWorkflow(serialNumberEntity, workflowType);
    }

    public ArrayList<Workflow> readAllWorkflows() throws SQLException{
        return dao.readAllWorkflows();
    }

    public Workflow readOneWorkflow(int id) throws SQLException{
        return dao.readOneWorkflow(id);
    }

    public ArrayList<Workflow> readAllWorkflowsOfOneEntity(int serialNumber) throws SQLException{
        return dao.readAllWorkflowsOfOneEntity(serialNumber);
    }

    public ArrayList<Workflow> readAllWorkflowsOfAType(String code) throws SQLException{
        return dao.readAllWorkflowsOfAType(code);
    }
}
