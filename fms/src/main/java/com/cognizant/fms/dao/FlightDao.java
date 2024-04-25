package com.cognizant.fms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cognizant.fms.database.DBcon;

public class FlightDao {
	public ResultSet searchFlight(String source,String destination) {
		try {
			Connection c = DBcon.con();
			String query = "SELECT * FROM flights where origin = ? AND destination = ?";
			PreparedStatement ps = c.prepareStatement(query);
			ps.setString(1, source);
			ps.setString(2, destination);
			
			ResultSet rs = ps.executeQuery();
			return rs;
		}catch(SQLException e) {
			e.getMessage();
			return null;
		}
	}

	public ResultSet checkFlight(int fid) {
		try {
			Connection c = DBcon.con();
			String query = "Select * from flights where flight_id = "+fid;
			PreparedStatement ps = c.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			return rs;
		}catch(Exception e) {
			e.getMessage();
			return null;
		}
	}

	public ResultSet showFlight() {
		try {
			Connection c = DBcon.con();
			String query = "SELECT * FROM flights";
			PreparedStatement ps = c.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			return rs;
		}catch(SQLException e) {
			e.getMessage();
			return null;
		}
		
	}
	
	public void updateSeat(int fid, int nos, String cls, String opr) {
		try {
			Connection c = DBcon.con();
			String query = "";
			if(cls.equals("economic")) {
				query = "update flights set available_eco_seats = available_eco_seats "+opr+" "+nos+" where flight_id = "+fid;
			}else if(cls.equals("business")) {
				query = "update flights set available_bus_seats = available_bus_seats "+opr+" "+nos+" where flight_id = "+fid;
			}
			PreparedStatement ps = c.prepareStatement(query);
			
			ps.executeUpdate();
					
	}catch(SQLException e) {
		e.getMessage();
	}
	}
}
