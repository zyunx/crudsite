package net.zyunx.crudsite.tool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class RunSQL {
	private final static String WEB_INF_DIR = "src/main/webapp/WEB-INF";

	private final static String DB_CONFIG_PROPERTIES_FILE = WEB_INF_DIR + "/dbconfig.properties";
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {

		Properties dbconfig = new Properties();
		dbconfig.load(new FileInputStream(DB_CONFIG_PROPERTIES_FILE));
		
		String driver = dbconfig.getProperty("db.driver");
		String url = dbconfig.getProperty("db.url");
		String username = dbconfig.getProperty("db.username");
		String password = dbconfig.getProperty("db.password");
		System.err.println(RunSQL.class.getName() + ": db.driver=" + driver);
		System.err.println(RunSQL.class.getName() + ": db.url=" + url);
		System.err.println(RunSQL.class.getName() + ": db.username=" + username);
		System.err.println(RunSQL.class.getName() + ": db.password=" + password);
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, username, password);
		
		Scanner s = new Scanner(System.in);
	    s.useDelimiter("/\\*[\\s\\S]*?\\*/|--[^\\r\\n]*|;");

	    Statement st = null;

	    try
	    {
	      st = conn.createStatement();

	      while (s.hasNext())
	      {
	        String line = s.next().trim();

	        if (!line.isEmpty())
	          st.execute(line);
	      }
	    }
	    finally
	    {
	      if (st != null)
	        st.close();
	    }
	    
		conn.close();
		System.exit(0);
	}
}
