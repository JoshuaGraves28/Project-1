import java.io.IOException;
import java.sql.*;
import javax.naming.*;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.json.simple.*;
public class Database {
    Context envContext = null, initContext = null;
    DataSource ds = null;
    private Connection getConnection() {
       
        Connection conn = null;
        
        try {
            
            Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/db_pool");
            conn = ds.getConnection();
            
        }        
        catch (Exception e) { e.printStackTrace(); }
        
        return conn;

    }
    public String getResultsAsTable(int sessionNumber)throws ServletException, IOException, SQLException {
        Connection connection = getConnection();
       
        String table = "";
        String query;
        String tableheading;
        String tablerow; 
        String key;
        String value;
        
        boolean results;
        
        ResultSetMetaData metadata = null;
        ResultSet resultset = null;
        
        PreparedStatement pstatement = null;
        
        try{
            System.out.println("*** Getting Query Results ... ");
            
            query = "SELECT * FROM registrations WHERE sessionid = ?";
            pstatement = connection.prepareStatement(query);
            results = pstatement.execute();
            if(sessionNumber == 1){
               pstatement.setInt(1,sessionNumber);
            }
            if(sessionNumber == 2){
               pstatement.setInt(1,sessionNumber);
            }
            if(sessionNumber == 3){
               pstatement.setInt(1,sessionNumber);
            }
            if(sessionNumber == 4){
               pstatement.setInt(1,sessionNumber);
            }
            
            if (results) {
                metadata = resultset.getMetaData();
                resultset = pstatement.getResultSet();
                
                int numberOfColumns = metadata.getColumnCount();
            
            table += "<table border=\"1\">";
            tableheading = "<tr>";
            
            System.out.println("*** Number of Columns: " + numberOfColumns);
            
            for (int i = 1; i <= numberOfColumns; i++) {
            
                key = metadata.getColumnLabel(i);
                
                tableheading += "<th>" + key + "</th>";
            
            }
            tableheading += "</tr>";
            
            table += tableheading;
                        
            while(resultset.next()) {
                
                tablerow = "<tr>";
                
                for (int i = 1; i <= numberOfColumns; i++) {

                    value = resultset.getString(i);

                    if (resultset.wasNull()) {
                        tablerow += "<td></td>";
                    }

                    else {
                        tablerow += "<td>" + value + "</td>";
                    }
                    
                }
                
                tablerow += "</tr>";
                
                table += tablerow;
                
            }
            
            table += "</table><br />";

        }
        }
        
         catch (Exception e) {
         System.out.println(e.toString());}
         finally {
            
            
            
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (pstatement != null) { try { pstatement.close(); pstatement = null; } catch (Exception e) {} }
            
            if (connection != null) { connection.close(); }
            
        }
         return table;
         
            }
    public String uploadUserDataToDatabase(String firstname, String lastname, String displayname, int sessionNumber) throws SQLException {
        Connection connection = getConnection();
        
        String query;
        String table = "";
        
        int id = 0;
        int checker = 0;
        
        ResultSet resultset= null;
        
        PreparedStatement pstatement = null;
        
        JSONObject json = new JSONObject();
        
        try{
            
            query = "INSERT INTO registrations(firstname, lastname, displayname, sessionid)"
                    + "VALUES(?,?,?,?)";
            pstatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            
            pstatement.setString(1,firstname);
            pstatement.setString(2, lastname);
            pstatement.setString(3, displayname);
            pstatement.setInt(4,sessionNumber);
            
            checker = pstatement.executeUpdate();
            if(checker ==1){
                resultset = pstatement.getGeneratedKeys();
                if(resultset.next()){
                    id = resultset.getInt(1);
                }
            }
            String code = "R";
            code+= id;
            
            json.put("code",code);
            json.put("displayname",displayname);
            
            table = JSONValue.toJSONString(json);
            
    }
    catch (Exception e) {
            System.out.println(e.toString());
            
     }
    finally {
   
        if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
        if (pstatement != null) { try { pstatement.close(); pstatement = null; } catch (Exception e) {} }
            
        if (connection != null) { connection.close(); }
            
        }
    return table;
    }
}
