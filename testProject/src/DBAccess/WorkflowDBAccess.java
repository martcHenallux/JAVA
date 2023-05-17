package DBAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DataAccess.BusinessEntityDataAccess;
import DataAccess.WorkflowDataAccess;
import Model.Workflow;
import DataAccess.WorkflowTypeDataAccess;

public class WorkflowDBAccess implements WorkflowDataAccess{

    public WorkflowDBAccess(){}

    public void createWorkflow(int serialNumberEntity, String workflowType) throws SQLException{
        StringBuilder sqlInstruction = new StringBuilder("insert into workflow");
        sqlInstruction.append(" (isOver, serialNumberEntity, type)");
        sqlInstruction.append(" values (?, ?, ?)");
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction.toString())){
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, serialNumberEntity);
            preparedStatement.setString(3, workflowType);
            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<Workflow> readAllWorkflows() throws SQLException{
        String sqlInstruction = "select * from workflow";
        ArrayList<Workflow> allWorkflows = new ArrayList<>();
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntityDataAccess entity = new BusinessEntityDBAccess();
        WorkflowTypeDataAccess type = new WorkflowTypeDBAccess();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            try (ResultSet data = preparedStatement.executeQuery()){
                Workflow workflow;
                while(data.next()){
                    workflow = new Workflow(data.getInt("id"), data.getBoolean("isOver"),
                        entity.readBusinessEntity(data.getInt("serialNumberEntity")),
                        type.readOneWorkflowType(data.getString("type")));
                    allWorkflows.add(workflow);
                }
            }
        }
        return allWorkflows;
    }

    public Workflow readOneWorkflow(int id) throws SQLException{
        String sqlInstruction = "select * from workflow where id = ?";
        Workflow workflow = null;
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntityDataAccess entity = new BusinessEntityDBAccess();
        WorkflowTypeDataAccess type = new WorkflowTypeDBAccess();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setInt(1, id);
            try (ResultSet data = preparedStatement.executeQuery()){
                if(data.next()){
                    workflow = new Workflow(data.getInt("id"), data.getBoolean("isOver"),
                        entity.readBusinessEntity(data.getInt("serialNumberEntity")),
                        type.readOneWorkflowType(data.getString("type")));
                }
            }
        }
        return workflow;
    }

    public ArrayList<Workflow> readAllWorkflowsOfOneEntity(int serialNumber) throws SQLException{
        String sqlInstruction = "select * from workflow where serialNumberEntity = ?";
        ArrayList<Workflow> allWorkflows = new ArrayList<>();
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntityDataAccess entity = new BusinessEntityDBAccess();
        WorkflowTypeDataAccess type = new WorkflowTypeDBAccess();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setInt(1, serialNumber);
            try (ResultSet data = preparedStatement.executeQuery()){
                Workflow workflow;
                while(data.next()){
                    workflow = new Workflow(data.getInt("id"), data.getBoolean("isOver"),
                        entity.readBusinessEntity(data.getInt("serialNumberEntity")),
                        type.readOneWorkflowType(data.getString("type")));
                    allWorkflows.add(workflow);
                }
            }
        }
        return allWorkflows;
    }

    public ArrayList<Workflow> readAllWorkflowsOfAType(String code) throws SQLException{
        String sqlInstruction = "select * from workflow where type = ?";
        ArrayList<Workflow> allWorkflows = new ArrayList<>();
        Connection connection = SingletonConnexion.getInstance();
        BusinessEntityDataAccess entity = new BusinessEntityDBAccess();
        WorkflowTypeDataAccess type = new WorkflowTypeDBAccess();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setString(1, code);
            try(ResultSet data = preparedStatement.executeQuery()){
                Workflow workflow;
                while(data.next()){
                    workflow = new Workflow(data.getInt("id"), data.getBoolean("isOver"),
                        entity.readBusinessEntity(data.getInt("serialNumberEntity")),
                        type.readOneWorkflowType(data.getString("type")));
                    allWorkflows.add(workflow);
                }
            }
        }
        return allWorkflows;
    }

    public void deleteAllWorkflowsOfPerson(int serialNumberEntity) throws SQLException{
        String sqlInstruction = "delete from workflow where serialNumberEntity = ?";
        Connection connection = SingletonConnexion.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)){
            preparedStatement.setInt(1, serialNumberEntity);
            preparedStatement.executeUpdate();
        }
    }
}
