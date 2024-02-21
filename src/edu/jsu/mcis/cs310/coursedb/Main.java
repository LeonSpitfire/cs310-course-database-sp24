package edu.jsu.mcis.cs310.coursedb;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.Jsoner;
import edu.jsu.mcis.cs310.coursedb.dao.*;

public class Main {
    
    private static final String USERNAME = "nobody@jsu.edu";
    
    public static void main(String[] args) {
        
        // Sample JSON strings (replace with your actual JSON strings)
        String[] jsonSamples = {
            "{\"key1\": \"value1\", \"key2\": \"value2\"}",
            "{\"key1\": \"value1\", \"invalidKey\": \"invalidValue\"}",
            "{\"key1\": \"value1\", \"anotherInvalidKey\": \"invalidValue\", \"key3\": \"value3\"}"
        };

        // Attempt to parse each JSON string
        for (int i = 0; i < jsonSamples.length; i++) {
            String json = jsonSamples[i];
            try {
                Object jsonObject = Jsoner.deserialize(json);
                
                // If successful, print the JSON data to the console
                System.out.println("JSON data for sample " + (i+1) + ":");
                System.out.println(jsonObject.toString());
                
            } catch (JsonException ex) {
                // If an exception occurs during parsing, print the error message
                System.out.println("Error occurred while parsing JSON for sample " + (i+1) + ":");
                System.out.println(ex.getMessage());
            }
        }
        
        // Create DAO Objects
        
        DAOFactory daoFactory = new DAOFactory("coursedb");
        
        RegistrationDAO registrationDao = daoFactory.getRegistrationDAO();
        SectionDAO sectionDao = daoFactory.getSectionDAO();
        
        int studentid = daoFactory.getStudentDAO().find(USERNAME);
        
        // Test Connection
        
        if ( !daoFactory.isClosed() ) {
            
            System.out.println("Connected Successfully!");
            
        }
        
    }
    
}
