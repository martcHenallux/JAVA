package Business;

import java.util.ArrayList;

import DBAccess.WorkflowDBAccess;
import Model.Workflow;

public class WorkflowManager {
    
    WorkflowDBAccess dao;

    public WorkflowManager(){
        dao = new WorkflowDBAccess();
    }

    public void createWorkflow(int serialNumberEntity, String workflowType){
        dao.createWorkflow(serialNumberEntity, workflowType);
    }

    public ArrayList<Workflow> readAllWorkflows(){
        return dao.readAllWorkflows();
    }

    public Workflow readOneWorkflow(int id){
        return dao.readOneWorkflow(id);
    }

    public ArrayList<Workflow> readAllWorkflowsOfOneEntity(int serialNumber){
        return dao.readAllWorkflowsOfOneEntity(serialNumber);
    }

    public ArrayList<Workflow> readAllWorkflowsOfAType(String code){
        return dao.readAllWorkflowsOfAType(code);
    }
}
