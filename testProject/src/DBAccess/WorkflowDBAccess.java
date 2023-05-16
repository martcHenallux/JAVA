package DBAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DataAccess.BusinessEntityDataAccess;
import DataAccess.WorkflowDataAccess;
import Model.Workflow;
import DataAccess.WorkflowTypeDataAccess;

public class WorkflowDBAccess implements WorkflowDataAccess{

    public WorkflowDBAccess(){}

    public void createWorkflow(int serialNumberEntity, String workflowType){
        StringBuilder sqlInstruction = new StringBuilder("insert into workflow");
        sqlInstruction.append(" (isOver, serialNumberEntity, type)");
        sqlInstruction.append(" values (?, ?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString());
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, serialNumberEntity);
            preparedStatement.setString(3, workflowType);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error creating workflow: " + e.getMessage());
        }
    }

    public ArrayList<Workflow> readAllWorkflows(){
        String sqlInstruction = "select * from workflow";
        ArrayList<Workflow> allWorkflows = new ArrayList<>();
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntityDataAccess entity = new BusinessEntityDBAccess();
        WorkflowTypeDataAccess type = new WorkflowTypeDBAccess();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            Workflow workflow;
            while(data.next()){
                workflow = new Workflow(data.getInt("id"), data.getBoolean("isOver"),
                    entity.readBusinessEntity(data.getInt("serialNumberEntity")),
                    type.readOneWorkflowType(data.getString("type")));
                allWorkflows.add(workflow);
            }
        } catch (Exception e) {
            System.out.println("Error reading all workflows: " + e.getMessage());
        }
        return allWorkflows;
    }

    public Workflow readOneWorkflow(int id){
        String sqlInstruction = "select * from workflow where id = ?";
        Workflow workflow = null;
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntityDataAccess entity = new BusinessEntityDBAccess();
        WorkflowTypeDataAccess type = new WorkflowTypeDBAccess();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, id);
            ResultSet data = preparedStatement.executeQuery();
            if(data.next()){
                workflow = new Workflow(data.getInt("id"), data.getBoolean("isOver"),
                    entity.readBusinessEntity(data.getInt("serialNumberEntity")),
                    type.readOneWorkflowType(data.getString("type")));
            }
        } catch (Exception e) {
            System.out.println("Error reading one workflow: " + e.getMessage());
        }
        return workflow;
    }

    public ArrayList<Workflow> readAllWorkflowsOfOneEntity(int serialNumber){
        String sqlInstruction = "select * from workflow where serialNumberEntity = ?";
        ArrayList<Workflow> allWorkflows = new ArrayList<>();
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntityDataAccess entity = new BusinessEntityDBAccess();
        WorkflowTypeDataAccess type = new WorkflowTypeDBAccess();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, serialNumber);
            ResultSet data = preparedStatement.executeQuery();
            Workflow workflow;
            while(data.next()){
                workflow = new Workflow(data.getInt("id"), data.getBoolean("isOver"),
                    entity.readBusinessEntity(data.getInt("serialNumberEntity")),
                    type.readOneWorkflowType(data.getString("type")));
                allWorkflows.add(workflow);
            }
        } catch (Exception e) {
            System.out.println("Error reading all workflows of one entity: " + e.getMessage());
        }
        return allWorkflows;
    }

    public ArrayList<Workflow> readAllWorkflowsOfAType(String code){
        String sqlInstruction = "select * from workflow where type = ?";
        ArrayList<Workflow> allWorkflows = new ArrayList<>();
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntityDataAccess entity = new BusinessEntityDBAccess();
        WorkflowTypeDataAccess type = new WorkflowTypeDBAccess();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, code);
            ResultSet data = preparedStatement.executeQuery();
            Workflow workflow;
            while(data.next()){
                workflow = new Workflow(data.getInt("id"), data.getBoolean("isOver"),
                    entity.readBusinessEntity(data.getInt("serialNumberEntity")),
                    type.readOneWorkflowType(data.getString("type")));
                allWorkflows.add(workflow);
            }
        } catch (Exception e) {
            System.out.println("Error reading all workflows of a type: " + e.getMessage());
        }
        return allWorkflows;
    }

    public void deleteAllWorkflowsOfPerson(int serialNumberEntity){
        String sqlInstruction = "delete from workflow where serialNumberEntity = ?";
        Connection connection = SingletonConnexion.getInstance();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, serialNumberEntity);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error deleting all workflows");
        }
    }
}
