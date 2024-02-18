package edu.jsu.mcis.cs310.coursedb.dao;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class SectionDAO {
    
    private final DAOFactory daoFactory;
    
    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    JsonArray jsonArray = new JsonArray();
    JsonArray Array= new JsonArray(); 
    JsonObject jsonobject = new JsonObject();
    
    public String find(int termid, String subjectid, String num) {
        
        String result = "[]";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                String QUERY_FIND = "SELECT * FROM section WHERE subjectid = ? AND num = ? AND termid = ? ORDER BY crn";
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setString(1, subjectid);
                ps.setString(2, num);
                ps.setInt(3, termid);
                rs = ps.executeQuery();
                
                while(rs.next())
                {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.put("crn", rs.getInt("crn"));
                    jsonObject.put("subjectid", rs.getString("subjectid"));
                    jsonObject.put("num", rs.getString("num"));
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
        
        //return result;
        return jsonArray.toString();
        
    }
    
}