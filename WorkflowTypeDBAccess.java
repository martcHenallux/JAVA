import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class WorkflowTypeDBAccess implements WorkflowTypeDataAccess{

    public WorkflowTypeDBAccess(){}
    
    public void createWorkflowType(String code, String description){
        StringBuilder sqlInstruction = new StringBuilder("insert into workflow_type");
        sqlInstruction.append(" (code, description)");
        sqlInstruction.append(" values (?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, description);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error creating workflow type: " + e.getMessage());
        }
    }

    public WorkflowType readOneWorkflowType(String code){
        StringBuilder sqlInstruction = new StringBuilder("select *");
        sqlInstruction.append(" from workflow_type");
        sqlInstruction.append(" where code = ?");
        Connection connection = SingletonConnexion.getInstance();
        WorkflowType workflowType = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setString(1, code);
            ResultSet data = preparedStatement.executeQuery();
            if(data.next()){
                workflowType = new WorkflowType(data.getString("code"), data.getString("description"));
            }
        } catch (Exception e) {
            System.out.println("Error reading one workflow type: " + e.getMessage());
        }
        return workflowType;
    }

    public ArrayList<WorkflowType> readAllWorkflowTypes(){
        String sqlInstruction = "select * from workflow_type";
        Connection connection = SingletonConnexion.getInstance();
        ArrayList<WorkflowType> allWorkflowTypes = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            WorkflowType workflowType;
            while(data.next()){
                workflowType = new WorkflowType(data.getString("code"), data.getString("description"));
                allWorkflowTypes.add(workflowType);
            }
        } catch (Exception e) {
            System.out.println("Error reading all the workflow types: " + e.getMessage());
        }
        return allWorkflowTypes;
    }
}
