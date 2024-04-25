package com.cognizant.fms.database;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBcon{
		public static Connection c = null;
		public static Connection con() {
			try {
				String url="jdbc:mysql://localhost:3306/fms";
				String user ="root";
				String pass = "root";
				

				c = DriverManager.getConnection(url,user,pass);
					
			}catch(Exception e) {
				e.printStackTrace();
			}
			return c;
			
		}
	}