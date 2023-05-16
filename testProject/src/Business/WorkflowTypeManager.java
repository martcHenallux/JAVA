package Business;

import java.util.ArrayList;

import DBAccess.WorkflowTypeDBAccess;
import Model.WorkflowType;

public class WorkflowTypeManager {
    
    WorkflowTypeDBAccess dao;
    
    public WorkflowTypeManager(){
        dao = new WorkflowTypeDBAccess();
    }

    private void createWorkflowType(String code, String description){
        dao.createWorkflowType(code, description);
    }

    public WorkflowType readOneWorkflowType(String code){
        return dao.readOneWorkflowType(code);
    }

    public ArrayList<WorkflowType> readAllWorkflowTypes(){
        return dao.readAllWorkflowTypes();
    }

    public void tryCreateWorkflowType(String code, String description){
        ArrayList<WorkflowType> types = new ArrayList<>();
        types = readAllWorkflowTypes();
        Boolean isIn = false;
        for (WorkflowType type : types) {
            if(type.getCode().toLowerCase().equals(code.toLowerCase())){
                isIn = true;
            }
        }
        if(!isIn){
            createWorkflowType(code, description);
        }
    }
}
