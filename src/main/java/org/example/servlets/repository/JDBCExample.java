package org.example.servlets.repository;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.servlets.dto.AdministrativeUnits;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBCExample {
    private static final Logger logger= LogManager.getRootLogger();
    public static String getRegion() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = getMyConnection();
        Statement statement = connection.createStatement();
        logger.debug("Connection to table");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM administrative_units");
        logger.debug("Select all from table");
        String json="";
        Gson gson=new Gson();
        while(resultSet.next()){
            AdministrativeUnits administrativeUnits=new AdministrativeUnits(resultSet.getInt(1),
                    resultSet.getString(2),resultSet.getString(3),
                    resultSet.getInt(4),resultSet.getDouble(4));
            String line=gson.toJson(administrativeUnits);
            logger.debug("Each line from table " +line);
            json+=line;



        }
        logger.debug("Process with jdbc off");
        return json;
    }
    public static void createRegion(String name, String administrativeUnits, Integer population, Double area) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = getMyConnection();
            logger.debug("Connection to table");
            int parentId=findParentId(administrativeUnits,connection);
             Statement statement = connection.createStatement();
            String sql1 = "insert into administrative_units(name, parent_id, population, area) values ('" +name+ "', '" +parentId+ "', '" +population+ "', '" +area+ "')";
            logger.debug("Add data of new adminUnit in the table");
            int x = statement.executeUpdate(sql1);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteRegion(String name,String administrativeUnits) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = getMyConnection();
        logger.debug("Connection to table");
        int parentId=findParentId(administrativeUnits,connection);
        PreparedStatement st = connection.prepareStatement("DELETE FROM administrative_units WHERE name = '" + name + "' and parent_id = '" +parentId+"';");
        st.executeUpdate();
        logger.debug("Delete from the table finished");
    }
    public static void updateRegion(String name,String administrativeUnits, Integer population, Double area) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = getMyConnection();
        logger.debug("Connection to table");
        int parentId=findParentId(administrativeUnits,connection);
        Statement statement=connection.createStatement();
        logger.debug("Data update will population = " +population+"and area ="+ area);
        String sql="update administrative_units set population = '"+population+"' , area = '"+area+"' where name= '"+name+"' and parent_id = '" +parentId+"' ";
        statement.executeUpdate(sql);
        logger.debug("Update data from table finished");
    }
    public static Connection getMyConnection() throws SQLException {
        String hostName = "localhost";
        String dbName = "test";
        String userName = "postgres";
        String password = "postgres";
        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) throws SQLException {
        String connectionURL = "jdbc:postgresql://" + hostName + ":5432/" + dbName;
        return DriverManager.getConnection(connectionURL, userName, password);
    }
    public static int findParentId(String administrativeUnits,Connection connection) throws ClassNotFoundException, SQLException {
        logger.debug("Select parentId for new adminUnit");
        String getParentId = "SELECT id FROM administrative_units WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(getParentId);
        preparedStatement.setString(1, administrativeUnits);
        ResultSet resultSet = preparedStatement.executeQuery();
        int parentId = 0;
        if (resultSet.next()) {
            parentId = resultSet.getInt("id");
        } else {
            System.out.println("Нет данных для имени '" + administrativeUnits + "'");
        }
        logger.debug("ParentId is "+parentId);
        return parentId;
    }
}