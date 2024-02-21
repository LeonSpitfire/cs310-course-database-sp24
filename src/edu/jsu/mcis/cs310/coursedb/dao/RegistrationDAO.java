package edu.jsu.mcis.cs310.coursedb.dao;

//Import Json-Simple-4
import com.github.cliftonlabs.json_simple.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class RegistrationDAO {
    
    private final DAOFactory daoFactory;
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    JsonArray JsonArray = new JsonArray();
    JsonArray Array= new JsonArray(); 
    JsonObject jsonobject = new JsonObject();
    
    public boolean create(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                String QUERY_CREATE = "INSERT INTO registration (studentid, termid, crn) VALUES (?, ?, ?)";
                ps = conn.prepareStatement(QUERY_CREATE);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                int rowsAffected = ps.executeUpdate();
                if(rowsAffected > 0){
                    result = true;
                    //Insert was good and succesful
                }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public boolean delete(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                 String QUERY_DELETE = "DELETE FROM registration WHERE studentid = ? AND termid=? AND crn=?";
                 ps = conn.prepareStatement(QUERY_DELETE);
                 ps.setInt(1, studentid);
                 ps.setInt(2, termid);
                 ps.setInt(3, crn);
            
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    result = true; // Fully Deleted it successful
            }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                String QUERY_DELETE = "DELETE FROM registration WHERE studentid = ? AND termid = ?";
                ps = conn.prepareStatement(QUERY_DELETE);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                int rowAffected = ps.executeUpdate();
                if (rowAffected > 0)
                {
                    result = true;
                }
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public String list(int studentid, int termid) {
        
        String result = null;
        
        JsonArray jsonArray = new JsonArray();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                //The ORDER BY crn = ?  isnt able to compare anything
                String QUERY_LIST = "SELECT studentid, termid, crn FROM registration WHERE studentid = ? AND termid = ?";
                ps = conn.prepareStatement(QUERY_LIST);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                rs = ps.executeQuery();
                
                
                while (rs.next()){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.put("studentid", rs.getInt("studentid"));
                    jsonObject.put("termid", rs.getInt("termid"));
                    jsonArray.add(jsonObject);
                }
                
                
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return jsonArray.toString();
        //return result;
        
    }
    
}