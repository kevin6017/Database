package edu.bsu.kmcminn.CS336;

// You need to import the java.sql package to use JDBC
import java.sql.*;

// We import java.io to be able to read from the command line
import java.io.*;

//import oracle.jdbc.OracleDriver;

class DatabaseInterface {
	public static String searchDB(String searchText) throws SQLException,
			IOException {
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

		String serverName = "csor12c.dhcp.bsu.edu";
		String portNumber = "1521";
		String sid = "or12cdb";
		String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":"
				+ sid;
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
		results = "Results:";
		results += "--------------------------------------------------------------------------- \n";

		System.out.println("Table:");
		System.out
				.println("---------------------------------------------------------------------------");
		int i = 1;
		results += "\n";
		while (rset.next()) {
			i = 1;
			while (i <= columNumber) {
				results += rsetmd.getColumnLabel(i) + ":      ";
				if (rsetmd.getColumnType(i) == Types.TIMESTAMP) {
					results += rset.getDate(i) + "\n";
					i++;
				} else {
					results += rset.getString(i) + "\n";
					i++;
				}
			}
			results += "\n\n";
		}
		rset.close();
		stmt.close();
		conn.close();
		return results;
	}

}