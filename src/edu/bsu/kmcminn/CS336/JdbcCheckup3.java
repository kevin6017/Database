package edu.bsu.kmcminn.CS336;

/*
 * Try this progam and make it working first.
 *
 * This sample can be used to check the JDBC installation.
*/

// You need to import the java.sql package to use JDBC
import java.sql.*;

// We import java.io to be able to read from the command line
import java.io.*;

//import oracle.jdbc.OracleDriver;

class JdbcCheckup3
{
  public static String searchDB(String searchText)
       throws SQLException, IOException
  {
    // Load the Oracle JDBC driver
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());


    String serverName = "csor12c.dhcp.bsu.edu";
    String portNumber = "1521";
    String sid = "or12cdb";
    String url ="jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
    String queryText = searchText;
    String results;

    Connection conn =
    	      DriverManager.getConnection (url,
    	           "kmcminn", "3746");



    //System.out.println ("connected.");

    // Create a statement
    Statement stmt = conn.createStatement ();

    // Do the SQL
    ResultSet rset = stmt.executeQuery (queryText);
    results = "Plane Table \n";
    results += "--------------------------------------------------------------------------- \n";
    
    System.out.println("Plane table:");
    System.out.println("---------------------------------------------------------------------------");
    while (rset.next ()){
    	results += rset.getString (1) + " " + rset.getString(2)
      		  + " " + rset.getString(3)
      		  + " " + rset.getString(4)
      		  + " " + rset.getString(5)
      		  + " " + rset.getString(6) +"\n";
      System.out.println (rset.getString (1) + " " + rset.getString(2)
    		  + " " + rset.getString(3)
    		  + " " + rset.getString(4)
    		  + " " + rset.getString(5)
    		  + " " + rset.getString(6));
    }

    //System.out.println ("Your JDBC installation is correct.");

    // close the resultSet
    rset.close();

    // Close the statement
    stmt.close();

    // Close the connection
    conn.close();
	return results;
  }

}