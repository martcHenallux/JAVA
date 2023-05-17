package Business;

import java.sql.SQLException;
import java.util.ArrayList;

import DBAccess.WorkflowTypeDBAccess;
import Model.WorkflowType;

public class WorkflowTypeManager {
    
    WorkflowTypeDBAccess dao;
    
    public WorkflowTypeManager(){
        dao = new WorkflowTypeDBAccess();
    }

    private void createWorkflowType(String code, String description) throws SQLException{
        dao.createWorkflowType(code, description);
    }

    public WorkflowType readOneWorkflowType(String code) throws SQLException{
        return dao.readOneWorkflowType(code);
    }

    public ArrayList<WorkflowType> readAllWorkflowTypes() throws SQLException{
        return dao.readAllWorkflowTypes();
    }

    public void tryCreateWorkflowType(String code, String description) throws SQLException{
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
