package edu.bsu.kmcminn.CS336;

// You need to import the java.sql package to use JDBC
import java.sql.*;

// We import java.io to be able to read from the command line
import java.io.*;

//import oracle.jdbc.OracleDriver;

class DatabaseInterface {
	public static String searchDB(String searchText) throws SQLException, IOException {
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

		String serverName = "csor12c.dhcp.bsu.edu";
		String portNumber = "1521";
		String sid = "or12cdb";
		String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
		String queryText = searchText;
		String results;

		Connection conn = DriverManager.getConnection(url, "kmcminn", "3746");

		// System.out.println ("connected.");

		// Create a statement
		Statement stmt = conn.createStatement();

		// Do the SQL
		ResultSet rset = stmt.executeQuery(queryText);
		ResultSetMetaData rsetmd = rset.getMetaData();
		int columNumber = rsetmd.getColumnCount();
		results = "Table \n";
		results += "--------------------------------------------------------------------------- \n";

		System.out.println("Table:");
		System.out.println("---------------------------------------------------------------------------");
		while (rset.next()) {
			int i = 1; 
			while(i <= columNumber){
			results += rset.getString(i) + " ";
					//+ " " + rset.getString(2) + " " + rset.getString(3) + " " + rset.getString(4)
					//+ " " + rset.getString(5) + " " + rset.getString(6) + "\n";
			System.out.println(rset.getString(i));
			i++;
			}
			results += "\n";
		}
		rset.close();
		stmt.close();
		conn.close();
		return results;
	}

}